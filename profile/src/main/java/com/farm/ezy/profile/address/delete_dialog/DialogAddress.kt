/*
 * BIT Lalpur App
 *
 * Created by Ayaan on 9/1/21, 1:09 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 9/1/21, 11:14 AM
 */



package com.farm.ezy.profile.address.delete_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DialogAddress : DialogFragment() {

    private val args: DialogAddressArgs by navArgs()

    @Inject
    lateinit var db: FirebaseFirestore
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage(resources.getString(com.farm.ezy.core.R.string.delete_warning))
            .setNegativeButton(resources.getString(com.farm.ezy.core.R.string.no), null)
            .setPositiveButton(resources.getString(com.farm.ezy.core.R.string.yes)) { _, _ ->
                deleteItem()
            }.create()


    private fun deleteItem() {
        db.collection("Users").document(args.uid)
            .collection("address")
            .document(args.address.path)
            .delete().addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    resources.getString(com.farm.ezy.core.R.string.deleted),
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    it.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}