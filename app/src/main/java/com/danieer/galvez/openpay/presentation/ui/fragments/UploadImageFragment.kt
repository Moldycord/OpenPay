package com.danieer.galvez.openpay.presentation.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.danieer.galvez.openpay.R
import com.danieer.galvez.openpay.databinding.FragmentUploadImageBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.ui.viewmodel.UploadImageFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class UploadImageFragment : Fragment() {

    private var _binding: FragmentUploadImageBinding? = null
    private val binding get() = _binding!!
    private var hasImageSelected = false

    private var filePath: Uri? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UploadImageFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[UploadImageFragmentViewModel::class.java]
        setupViews()
    }


    private fun setupViews() {
        binding.apply {
            selectImgButton.setOnClickListener { selectImage() }
            uploadImgButton.setOnClickListener { uploadImage() }
        }
    }

    private fun selectImage() {
        val pickImageIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri = data.data!!

            filePath = data.data!!
            binding.imgViewSelected.setImageURI(selectedImageUri)
            hasImageSelected = true
        }
    }

    private fun uploadImage() {
        filePath?.let {
            showProgress()
            viewModel.uploadImage(it).addOnCompleteListener {
                hideProgress()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.uploaded_img_successfully),
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener {
                hideProgress()
                showToastError()
            }.addOnCanceledListener {
                hideProgress()
                showToastError()
            }
        }
    }

    private fun showToastError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.uploaded_img_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 22
    }
}