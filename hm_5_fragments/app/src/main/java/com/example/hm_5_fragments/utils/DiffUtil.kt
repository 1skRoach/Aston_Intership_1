package com.example.hm_5_fragments.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.hm_5_fragments.model.ContactModel

class DiffUtil(
    private val oldList: MutableList<ContactModel>,
    private val newList: MutableList<ContactModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int =  oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}