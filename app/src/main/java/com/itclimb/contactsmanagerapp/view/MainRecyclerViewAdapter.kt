package com.itclimb.contactsmanagerapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.itclimb.contactsmanagerapp.R
import com.itclimb.contactsmanagerapp.databinding.ActivityMainBinding
import com.itclimb.contactsmanagerapp.databinding.CardItemBinding
import com.itclimb.contactsmanagerapp.room.Contact

class MainRecyclerViewAdapter(private  val contactList:List<Contact>, private val clickListener:(Contact) -> Unit ):
    RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    class MainViewHolder(private val binding: CardItemBinding ):RecyclerView.ViewHolder(binding.root){
        fun bind(contact:Contact, clickListener: (Contact) -> Unit) {
            binding.nameTv.text = contact.name
            binding.emailTv.text = contact.email
            binding.listItemLayout.setOnClickListener {
                clickListener(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:CardItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.card_item,
            parent, false)

        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  contactList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(contactList[position], clickListener)
    }
}