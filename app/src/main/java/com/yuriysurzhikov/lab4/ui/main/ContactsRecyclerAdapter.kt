package com.yuriysurzhikov.lab4.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yuriysurzhikov.lab4.R
import com.yuriysurzhikov.lab4.model.DataContact
import com.yuriysurzhikov.lab4.ui.list.AbstractRecyclerAdapter
import com.yuriysurzhikov.lab4.ui.list.OnItemClickListener
import de.hdodenhof.circleimageview.CircleImageView

class ContactsRecyclerAdapter :
    AbstractRecyclerAdapter<DataContact, ContactsRecyclerAdapter.ContactHolder>() {

    lateinit var removeListener: OnItemClickListener<DataContact>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_contact, parent, false),
            removeListener
        )
    }

    inner class ContactHolder(
        view: View,
        private val removeListener: OnItemClickListener<DataContact>
    ) :
        AbstractRecyclerAdapter.AbstractViewHolder<DataContact>(view) {

        private val name: TextView? by lazy { itemView.findViewById<TextView>(R.id.name) }
        private val email: TextView? by lazy { itemView.findViewById<TextView>(R.id.email) }
        private val phone: TextView? by lazy { itemView.findViewById<TextView>(R.id.phone) }
        private val image: ImageView by lazy { itemView.findViewById<CircleImageView>(R.id.contact_image) }
        private val removeButton: ImageView by lazy { itemView.findViewById<ImageView>(R.id.remove_action) }

        override fun bind(item: DataContact) {
            super.bind(item)

            name?.text = item.name
            phone?.text = item.phone
            email?.text = item.email
            removeButton.setOnClickListener {
                removeListener.onItemClick(item, adapterPosition)
            }

            Glide.with(image)
                .load(item.imageProfile)
                .into(image)
        }
    }
}