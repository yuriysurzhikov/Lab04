package com.yuriysurzhikov.lab4.ui.addcontract

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.yuriysurzhikov.lab4.BuildConfig
import com.yuriysurzhikov.lab4.R
import com.yuriysurzhikov.lab4.model.ContactsViewModel
import com.yuriysurzhikov.lab4.model.DataContact
import com.yuriysurzhikov.lab4.ui.text.LengthWatcher
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddContactFragment : Fragment() {

    private lateinit var nameInput: TextInputLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var phoneInput: TextInputLayout
    private lateinit var photoPicker: ImageView

    private lateinit var submitAction: Button
    private lateinit var cancelAction: Button
    private lateinit var pickPhotoAction: Button

    private lateinit var viewModel: ContactsViewModel

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(ContactsViewModel::class.java)
        nameInput = view.findViewById(R.id.name_til)
        nameInput.editText?.addTextChangedListener(LengthWatcher(nameInput))
        emailInput = view.findViewById(R.id.email_til)
        emailInput.editText?.addTextChangedListener(LengthWatcher(emailInput))
        phoneInput = view.findViewById(R.id.phone_til)
        phoneInput.editText?.addTextChangedListener(LengthWatcher(phoneInput))
        photoPicker = view.findViewById(R.id.circle_image)

        submitAction = view.findViewById(R.id.button_confirm)
        submitAction.setOnClickListener(submitClickListener)

        cancelAction = view.findViewById(R.id.button_cancel)
        cancelAction.setOnClickListener(cancelClickListener)

        pickPhotoAction = view.findViewById(R.id.button_take_photo)
        pickPhotoAction.setOnClickListener(pickPhotoClickListener)
        photoPicker.setOnClickListener(pickPhotoClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAPTURE_IMAGE_CODE -> processCaptureResult(resultCode, data)
        }
    }

    private fun processCaptureResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                photoPicker.setImageBitmap(imageBitmap)

                val outputFile = File.createTempFile("File_", ".png")
                var fileOutputStream: FileOutputStream? = null
                try {
                    fileOutputStream = FileOutputStream(outputFile)
                    imageBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    imageUri = FileProvider
                        .getUriForFile(
                            context!!,
                            "${BuildConfig.APPLICATION_ID}.fileprovider",
                            outputFile
                        )
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    fileOutputStream?.flush()
                    fileOutputStream?.close()
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(
                    context,
                    getString(R.string.warning_action_canceled),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun checkInputsIsValid(): Boolean {
        var valid = true
        if (nameInput.editText?.text.isNullOrEmpty()) {
            nameInput.error = getString(R.string.error_empty_name)
            valid = false
        }
        if (emailInput.editText?.text.isNullOrEmpty()) {
            emailInput.error = getString(R.string.error_empty_email)
            valid = false
        }
        if (phoneInput.editText?.text.isNullOrEmpty()) {
            phoneInput.error = getString(R.string.error_empty_phone)
            valid = false
        }
        return valid
    }

    private val submitClickListener = View.OnClickListener {
        if (checkInputsIsValid()) {
            val name = nameInput.editText?.text.toString()
            val email = emailInput.editText?.text.toString()
            val phone = phoneInput.editText?.text.toString()
            val newItem = DataContact(name, email, phone, imageUri)
            viewModel.insertContact(newItem)
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private val cancelClickListener = View.OnClickListener {
        activity?.supportFragmentManager?.popBackStack()
    }

    private val pickPhotoClickListener = View.OnClickListener {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(intent, CAPTURE_IMAGE_CODE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(
                context!!,
                resources.getString(R.string.error_activity_no_found),
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    companion object {
        const val CONTACT_ENTITY = "AddContactActivity.ContactEntity"
        private const val CAPTURE_IMAGE_CODE = 100

        @JvmStatic
        fun getInstance() = AddContactFragment()
    }
}