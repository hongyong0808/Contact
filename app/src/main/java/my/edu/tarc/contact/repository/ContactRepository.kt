package my.edu.tarc.contact.repository

import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase
import my.edu.tarc.contact.dao.ContactDao
import my.edu.tarc.contact.model.Contact

class ContactRepository (private val contactDao: ContactDao){
    //Cache copy of dataset
    val allContact: LiveData<List<Contact>> = contactDao.getAll()

    suspend fun insert(contact: Contact){
        contactDao.insert(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }

    fun findByName(name: String):Contact{
        return contactDao.findByName(name)
    }

    fun findByPhone(phone: String):Contact{
        return contactDao.findByPhone(phone)
    }

    fun syncContact(id: String){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("contact")

        for(contact in allContact.value!!.listIterator()){
            myRef.child(id).child(contact.phone).child("phone").setValue(contact.phone)
            myRef.child(id).child(contact.phone).child("name").setValue(contact.name)
        }

        if(!allContact.value.isNullOrEmpty()){
            for(contact in  allContact.value!!.listIterator()){

            }
        }
    }

}