package my.edu.tarc.contact.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.contact.dao.ContactDao
import my.edu.tarc.contact.model.Contact
import my.edu.tarc.contact.repository.ContactRepository
import my.edu.tarc.mycontact.database.ContactDatabase

class ContactViewModel (application: Application): AndroidViewModel(application) {

    //Create a UI dataset
    var contactList: LiveData<List<Contact>>
    private val contactRepository: ContactRepository

    init {
        val contactDao = ContactDatabase.getDatabase(application).contactDao()

        contactRepository = ContactRepository(contactDao)
        contactList = contactRepository.allContact
    }

    //Launching a coroutine
    fun insert(contact: Contact) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        contactRepository.delete(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch {
        contactRepository.update(contact)
    }

}