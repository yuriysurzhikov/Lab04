package com.yuriysurzhikov.lab4.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yuriysurzhikov.lab4.INavigationActivity
import com.yuriysurzhikov.lab4.MainActivity
import com.yuriysurzhikov.lab4.R
import com.yuriysurzhikov.lab4.databinding.FragmentMainBinding
import com.yuriysurzhikov.lab4.model.DataContact
import com.yuriysurzhikov.lab4.model.ContactsViewModel
import com.yuriysurzhikov.lab4.ui.addcontract.AddContactFragment
import com.yuriysurzhikov.lab4.ui.list.OnItemClickListener

class MainFragment : Fragment() {

    private val adapter = ContactsRecyclerAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ContactsViewModel
    private lateinit var addButton: FloatingActionButton
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(ContactsViewModel::class.java)
        binding.viewModel = viewModel

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter
        adapter.removeListener = removeListener

        addButton = view.findViewById(R.id.add_fab)
        addButton.setOnClickListener(addClickListener)

        viewModel.observeContacts(this, contactObserver)
    }

    private val removeListener = object : OnItemClickListener<DataContact> {
        override fun onItemClick(item: DataContact, position: Int) {
            viewModel.removeContact(item)
        }
    }

    private val addClickListener = View.OnClickListener {
        val fragment = AddContactFragment.getInstance()
        if (activity is INavigationActivity) {
            (activity as INavigationActivity).showFragment(fragment)
        }
    }

    private val contactObserver = Observer<List<DataContact>> {
        adapter.setItems(it)
    }

    companion object {
        const val CREATE_CONTACT_CODE = 101

        @JvmStatic
        fun getInstance() = MainFragment()
    }
}