package com.yuriysurzhikov.lab4.ui.imagefragment

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yuriysurzhikov.lab4.R
import com.yuriysurzhikov.lab4.utils.ViewsUtil

class ImageFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var progressIndicator: ProgressBar
    private lateinit var errorMessage: TextView
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUri = it.getParcelable(ARG_IMAGE_URI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressIndicator = view.findViewById(R.id.progress_bar)
        errorMessage = view.findViewById(R.id.error_message)
        errorMessage.text = context?.getText(R.string.error_failed_load_image)
        imageView = view.findViewById(R.id.image_view)
        Glide.with(imageView)
            .load(imageUri)
            .addListener(loadListener)
            .into(imageView)
    }

    private val loadListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            ViewsUtil.setGone(progressIndicator)
            ViewsUtil.setGone(imageView)
            ViewsUtil.setVisible(errorMessage)
            return true
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            ViewsUtil.setGone(progressIndicator)
            ViewsUtil.setGone(errorMessage)
            ViewsUtil.setVisible(imageView)
            return false
        }
    }

    companion object {

        private const val ARG_IMAGE_URI = "image_uri"

        @JvmStatic
        fun getInstance(imageUri: Uri?) = ImageFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_IMAGE_URI, imageUri)
            }
        }
    }
}