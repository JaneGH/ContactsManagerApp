package com.itclimb.contactsmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itclimb.contactsmanagerapp.databinding.ActivityMainBinding
import com.itclimb.contactsmanagerapp.repository.ContactRepository
import com.itclimb.contactsmanagerapp.room.Contact
import com.itclimb.contactsmanagerapp.room.ContactDatabase
import com.itclimb.contactsmanagerapp.view.MainRecyclerViewAdapter
import com.itclimb.contactsmanagerapp.viewmodel.ContactViewModel
import com.itclimb.contactsmanagerapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  contactViewModel: ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ContactDatabase.getInstance(applicationContext).contactDAO
        val repository = ContactRepository(dao)
        val factory =  ViewModelFactory(repository)

        contactViewModel= ViewModelProvider(this, factory)[ContactViewModel::class.java]
        binding.contactViewModel= contactViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this )
        displayUserList()
    }

    private fun displayUserList() {
        contactViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter =  MainRecyclerViewAdapter(it,
                {selectedItem:Contact -> listItemClicked(selectedItem)}

            )
        })
    }

    private fun listItemClicked(selectedItem: Contact) {
        Toast.makeText(this, "selected item is ${selectedItem.name}",
        Toast.LENGTH_LONG).show()
        contactViewModel.initUpdateOrDelete(selectedItem)
    }
}