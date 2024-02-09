package com.itclimb.contactsmanagerapp.repository

import com.itclimb.contactsmanagerapp.room.Contact
import com.itclimb.contactsmanagerapp.room.ContactDAO

//bridge between the ViewModel and Data Source
class ContactRepository (private val contactDAO: ContactDAO) {
    val contacts = contactDAO.getAllContacts()

    suspend fun insert(contact: Contact):Long {
        return contactDAO.insertContact(contact )
    }

    suspend fun delete(contact: Contact) {
        return contactDAO.deleteContact(contact )
    }

    suspend fun update (contact: Contact) {
        return contactDAO.updateContact( contact )
    }

    suspend fun deleteAll () {
        return contactDAO.deleteAll()
    }

}