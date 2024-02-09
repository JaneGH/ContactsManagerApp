package com.itclimb.contactsmanagerapp.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itclimb.contactsmanagerapp.repository.ContactRepository
import com.itclimb.contactsmanagerapp.room.Contact
import kotlinx.coroutines.launch

class ContactViewModel(private  val repository:ContactRepository) : ViewModel(), Observable {
    val users = repository.contacts;
    private var isUpdateOrDelete = false;
    private lateinit var contactToUpdateOrDelete: Contact

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun delete (contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
        //resetting
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            //make an update
            contactToUpdateOrDelete.name   = inputName.value!!
            contactToUpdateOrDelete.email  = inputEmail.value!!
            update(contactToUpdateOrDelete)
        } else {
            val name  = inputName.value!!
            val email = inputEmail.value!!
            insert(Contact(0,name,email))
            inputName.value  = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(contactToUpdateOrDelete)
        }else{
            deleteAll()
        }

    }

    fun initUpdateOrDelete (contact: Contact) {
        inputName.value = contact.name
        inputEmail.value = contact.email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}
