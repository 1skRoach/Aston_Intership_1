package com.example.hm_5_fragments.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.example.hm_5_fragments.adapter.ContactAdapter
import com.example.hm_5_fragments.databinding.FragmentContactListBinding
import com.example.hm_5_fragments.data.ContactList
import com.example.hm_5_fragments.model.ContactModel
import com.example.hm_5_fragments.utils.DiffUtil
import java.util.*


class ContactListFragment : Fragment() {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactAdapter: ContactAdapter
    private var contactList = mutableListOf<ContactModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        initParams()
        initToolbar()

        return binding.root
    }

    private fun initParams() {

        contactAdapter =
            ContactAdapter(ContactList.randomContactList as MutableList<ContactModel>, this@ContactListFragment, )
        binding.recyclerView.adapter = contactAdapter

    }


    private fun initToolbar() {
        val searchView: SearchView = binding.SearchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return false
            }
        })
    }

    private fun filterList(text: String?) {
        val filteredContactList = mutableListOf<ContactModel>()
        for (item in contactAdapter.filteredContactList) {
            if (text?.lowercase(Locale.ROOT)?.let {
                    item.firstName.lowercase(Locale.ROOT)
                        .contains(it)
                } == true) {
                filteredContactList.add(item)
            }
            if (filteredContactList.isNotEmpty()) {
                contactAdapter.setFilteredList(filteredContactList)
            }
        }
    }
}