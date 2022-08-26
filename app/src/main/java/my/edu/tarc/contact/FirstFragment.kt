package my.edu.tarc.contact

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.edu.tarc.contact.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

        val contactAdapter = ContactAdapter(MainActivity.contactList)

            //Connect adapter to the RecycleView
        binding.recycleViewContact.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recycleViewContact.adapter = contactAdapter

    }

    override fun onDestroyView() {
        Log.d("First Fragment", "onDestroyView")
        super.onDestroyView()
        _binding = null
    }
}