package my.edu.tarc.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.contact.model.Contact

class ContactAdapter (private val contactList: List<Contact>): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        //A view object hosting each record
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewPhone: TextView = view.findViewById(R.id.textViewPhone)

        init {
            view.setOnClickListener{
                //TODO: Handling click event
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewName.text = contactList[position].name
        holder.textViewPhone.text = contactList[position].phone
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}