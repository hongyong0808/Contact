package my.edu.tarc.contact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.tarc.contact.databinding.FragmentSecondBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Implicit Intent
    private val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.imageViewPicture.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        readProfilePicture()
        readProfileInfo()

        binding.imageViewPicture.setOnClickListener {
            getPhoto.launch("image/*")
        }
    }

    private fun readProfileInfo() {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        if (preferences != null) {
            if(preferences.contains(getString(R.string.name))){
                binding.editTextTextName.setText(
                    preferences.getString(getString(R.string.name), ""))
            }
            if(preferences.contains(getString(R.string.phone))){
                binding.editTextPhone.setText(
                    preferences.getString(getString(R.string.phone), ""))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_save).isVisible = true
        menu.findItem(R.id.action_profile).isVisible = false
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                saveProfilePicture()
                saveProfileInfo()
                Toast.makeText(context, getString(R.string.profile_saved),
                    Toast.LENGTH_SHORT).show()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveProfileInfo() {
        val preferences = activity?.getPreferences(Context.MODE_PRIVATE)
        if (preferences != null) {
            with(preferences.edit()){
                putString(getString(R.string.name), binding.editTextTextName.text.toString())
                putString(getString(R.string.phone), binding.editTextPhone.text.toString())
            }.apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveProfilePicture() {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)

        val bd = binding.imageViewPicture.getDrawable() as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun readProfilePicture() {
        val filename = "profile.png"
        val file = File(this.context?.filesDir, filename)
        if(file.exists()){
            try {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.imageViewPicture.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }else{
            binding.imageViewPicture.setImageResource(R.drawable.ic_baseline_account_box_24)
        }

    }
}