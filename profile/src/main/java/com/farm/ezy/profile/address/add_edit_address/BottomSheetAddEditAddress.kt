package com.farm.ezy.profile.address.add_edit_address

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.farm.ezy.core.models.address.AddressSet
import com.farm.ezy.profile.databinding.BottomSheetAddEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetAddEditAddress : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAddEditBinding
    private val viewModel: AddEditViewModel by viewModels()

    @Inject
    lateinit
    var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAddEditBinding.inflate(layoutInflater)
        setViews()
        binding.apply {
            bottomSheetTitle.setOnClickListener {
                dismiss()
            }
            textViewSave.setOnClickListener {
                saveAddress()
            }
        }
        return binding.root
    }

    private fun saveAddress() = binding.apply {
        val error = resources.getString(com.farm.ezy.core.R.string.empty_error)

        val name = editTextName.text.toString()
        if (name.isEmpty()) {
            outlinedTextFieldName.error = error
            return@apply
        }
        val number = editTextNumber.text.toString()
        if (number.isEmpty()) {
            outlinedTextFieldNumber.error = error
            return@apply
        }
        if (number.length != 10) {
            outlinedTextFieldNumber.error =
                resources.getString(com.farm.ezy.core.R.string.number_error)
            return@apply
        }

        val houseNo = editTextHouseNo.text.toString()
        if (houseNo.isEmpty()) {
            outlinedTextFieldHouseNo.error = error
            return@apply
        }

        val area = editTextArea.text.toString()
        if (area.isEmpty()) {
            outlinedTextFieldArea.error = error
            return@apply
        }
        val landMark = editTextLandMark.text.toString()
        if (landMark.isEmpty()) {
            outlinedTextFieldLandMark.error = error
            return@apply
        }

        val town = editTextTown.text.toString()
        if (town.isEmpty()) {
            outlinedTextFieldTown.error = error
            return@apply
        }
        val postalCode = editPostCode.text.toString()
        if (postalCode.isEmpty()) {
            outlinedTextFieldPostalCode.error = error
            return@apply
        }
        if (postalCode.length != 6) {
            outlinedTextFieldNumber.error =
                resources.getString(com.farm.ezy.core.R.string.postal_code_error)
            return@apply
        }
        val state = editTextState.text.toString()
        if (state.isEmpty()) {
            outlinedTextFieldState.error = error
            return@apply
        }
        addAddressToDatabase(
            viewModel.address.copy(
                name, number, postalCode, houseNo, area, landMark, town, state
            )
        )
    }

    private fun addAddressToDatabase(address: AddressSet) {
        val ref = db.collection("Users").document(viewModel.uid!!)
            .collection("address")
        val path = address.path.ifEmpty {
            val p =ref.document().id
            address.apply {
                path = p
            }
            p
        }
        ref.document(path).set(address).addOnSuccessListener {
            Toast.makeText(
                requireContext(),
                resources.getString(com.farm.ezy.core.R.string.added),
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setViews() {
        val address = viewModel.address
        binding.apply {
            editTextName.setText(address.name)
            editTextNumber.setText(address.number)
            editTextHouseNo.setText(address.houseNo)
            editTextLandMark.setText(address.landMark)
            editTextArea.setText(address.area)
            editTextTown.setText(address.town)
            editPostCode.setText(address.posterCode)
            editTextState.setText(address.state)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun getTheme(): Int = com.farm.ezy.core.R.style.ThemeOverlay_App_BottomSheetDialog
}