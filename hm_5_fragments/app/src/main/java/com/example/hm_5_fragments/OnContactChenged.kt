package com.example.hm_5_fragments

import com.example.hm_5_fragments.model.ContactModel

interface OnContactChanged {
    fun updateContact(contactModel: ContactModel)
}