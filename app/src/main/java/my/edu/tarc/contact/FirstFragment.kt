package my.edu.tarc.contact

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.contact.databinding.FragmentFirstBinding
import my.edu.tarc.contact.viewmodel.ContactViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val contactViewModel: ContactViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("First Fragment", "onViewCreate")
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val contactAdapter = ContactAdapter(MainActivity.contactList)
        val contactAdapter = ContactAdapter()
            //Connect adapter to the RecycleView
        binding.recycleViewContact.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recycleViewContact.adapter = contactAdapter

    }

    override fun onResume(){
        Log.d("onResume","First Fragment")
        super.onResume()

        val contactAdapter = ContactAdapter()

        contactViewModel.contactList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                Toast.makeText(context,getString(R.string.no_record),Toast.LENGTH_SHORT).show()
            }else{
                contactAdapter.setContact(it)
            }
        }

        binding.recycleViewContact.layoutManager = LinearLayoutManager(activity)
        binding.recycleViewContact.adapter = contactAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile ->{
                val navController = activity?.findNavController(R.id.nav_host_fragment_content_main)
                navController?.navigate(R.id.action_ContactFragment_to_ProfileFragment)
                return true
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_save).isVisible = false
    }


    override fun onDestroyView() {
        Log.d("First Fragment", "onDestroyView")
        super.onDestroyView()
        _binding = null
    }
}