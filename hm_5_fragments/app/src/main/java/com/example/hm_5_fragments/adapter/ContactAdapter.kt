package com.example.hm_5_fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hm_5_fragments.R
import com.example.hm_5_fragments.databinding.ItemContactBinding
import com.example.hm_5_fragments.fragments.ContactListFragment
import com.example.hm_5_fragments.fragments.ContactListFragmentDirections
import com.example.hm_5_fragments.model.ContactModel
import com.example.hm_5_fragments.utils.DiffUtil


class ContactAdapter(
    private var contactList: MutableList<ContactModel>,
    private val context: ContactListFragment,
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var filteredContactList: MutableList<ContactModel> = contactList

    fun setFilteredList(filteredList: MutableList<ContactModel>) {
        contactList = filteredList
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(contactModel: ContactModel, index: Int) {
            binding.lastName.text = contactModel.lastName
            binding.firstName.text = contactModel.firstName
            binding.contactNumber.text = contactModel.contactNumber

            fun setUrl(): String {
                val id = contactModel.imageView
                val url = "https://picsum.photos/200"
                return url.replace("200", id)
            }
            binding.avatar.load(setUrl()) {
                crossfade(400)
                transformations(CircleCropTransformation())
            }
            fun editContact() {
                val editAction =
                    ContactListFragmentDirections.actionContactListFragmentToBlankFragment(
                        binding.lastName.text.toString(),
                        binding.firstName.text.toString(),
                        binding.contactNumber.text.toString()
                    )
                context.findNavController().navigate(editAction)
            }


            itemView.setOnClickListener {
//                передаем сюда все данные, чтобы при переходе данные перешли во 2 фрагмент
                val saveAction =
                    ContactListFragmentDirections.actionContactListFragmentToContactFragment(
                        binding.lastName.text.toString(),
                        binding.firstName.text.toString(),
                        binding.contactNumber.text.toString(),
                        imageView = setUrl()
                    )
                context.findNavController().navigate(saveAction)
            }

            itemView.setOnLongClickListener {
                AlertDialog.Builder(itemView.context).apply {
                    setTitle("The confirmation")
                    setMessage("Do you want to delete or edit this contact number?")

                    setPositiveButton("Delete") { _, _ ->
                        deleteItem(index)
                    }


                    setNegativeButton("Edit") { _, _ ->
                        editContact()
                    }

                }.create().show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactList = contactList[position]
        holder.bindItem(contactList, position)
        //   you can set an animation here
        //   holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }



    fun setData(newContactList: MutableList<ContactModel>) {
        val diffUtil = DiffUtil(contactList, newContactList)
        val diffResults = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        contactList = newContactList
        diffResults.dispatchUpdatesTo(this)
    }


    fun setListOfContacts(list: MutableList<ContactModel>) {
        contactList = list
        notifyDataSetChanged()
    }


    fun deleteItem(index: Int) {
        // contacts = ArrayList(contactList)
        contactList.removeAt(index)
        notifyDataSetChanged()

    }


}



