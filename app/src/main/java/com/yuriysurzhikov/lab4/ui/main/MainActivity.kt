package com.yuriysurzhikov.lab4.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yuriysurzhikov.lab4.R
import com.yuriysurzhikov.lab4.databinding.ActivityMainBinding
import com.yuriysurzhikov.lab4.model.DataContact
import com.yuriysurzhikov.lab4.ui.addcontract.AddContactActivity
import com.yuriysurzhikov.lab4.ui.addcontract.AddContactActivity.Companion.CONTACT_ENTITY
import com.yuriysurzhikov.lab4.ui.list.OnItemClickListener

class MainActivity : AppCompatActivity() {

    private val adapter = ContactsRecyclerAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var addButton: FloatingActionButton
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.viewModel = viewModel

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.removeListener = removeListener

        addButton = findViewById(R.id.add_fab)
        addButton.setOnClickListener(addClickListener)

        viewModel.observeContacts(this, contactObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREATE_CONTACT_CODE -> processNewContactResult(resultCode, data)
        }
    }

    private val removeListener = object : OnItemClickListener<DataContact> {
        override fun onItemClick(item: DataContact, position: Int) {
            viewModel.removeContact(item)
        }
    }

    private fun processNewContactResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                val contact = data?.extras?.getParcelable<DataContact>(CONTACT_ENTITY)
                contact?.let { viewModel.insertContact(it) }
                Toast.makeText(this, getString(R.string.message_action_success), Toast.LENGTH_SHORT)
                    .show()
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, getString(R.string.warning_action_canceled), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private val addClickListener = View.OnClickListener {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivityForResult(intent, CREATE_CONTACT_CODE)
    }

    private val contactObserver = Observer<List<DataContact>> {
        adapter.setItems(it)
    }

    companion object {
        const val CREATE_CONTACT_CODE = 101
    }
}