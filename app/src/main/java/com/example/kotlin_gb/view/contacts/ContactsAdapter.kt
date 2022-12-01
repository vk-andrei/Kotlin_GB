package com.example.kotlin_gb.view.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_gb.R
import com.example.kotlin_gb.databinding.ContactItemBinding

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.RecyclerItemContactsViewHolder>() {

    var contactsList: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerItemContactsViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return RecyclerItemContactsViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerItemContactsViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }


    class RecyclerItemContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ContactItemBinding.bind(view)
        fun bind(name: String) {
            binding.tvContactName.text = name
        }
    }
}