package com.example.hm_5_fragments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hm_5_fragments.OnContactChanged
import com.example.hm_5_fragments.R
import com.example.hm_5_fragments.databinding.FragmentContactListBinding
import com.example.hm_5_fragments.databinding.FragmentEditBinding
import com.example.hm_5_fragments.model.ContactModel


class BlankFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        val args: ContactFragmentArgs by navArgs()
        val editedLastname =  binding.lastNameEditText.text.toString()
        val editedFirstname = binding.firstNameEditText.text.toString()
        val editedNumber = binding.numberEditText.text.toString()


        binding.lastNameEditText.setText(args.lastName)
        binding.firstNameEditText.setText(args.firstName)
        binding.numberEditText.setText(args.contactNumber)

        binding.saveButton.setOnClickListener {
          val action = BlankFragmentDirections.actionBlankFragmentToContactListFragment(

          )
            findNavController().navigate(action)
            Toast.makeText(context, "Contact has Changed", Toast.LENGTH_SHORT).show()
        }



        return binding.root
    }

}