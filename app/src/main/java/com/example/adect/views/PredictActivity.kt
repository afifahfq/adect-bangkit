package com.example.adect.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.adect.R
import com.example.adect.databinding.ActivityPredictBinding
import com.example.adect.helper.createCustomTempFile
import com.example.adect.helper.uriToFile
import com.example.adect.viewmodels.PredictViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictBinding
    private lateinit var currentPhotoPath: String
    private lateinit var mLiveDataStory: PredictViewModel
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }

        binding.editDescriptionText.text = "How to take photo : \n" +
                "Gently pull your lower eyelid downward while focusing your eyes upward. Make sure to take the photo close enough so your eyelid color can be captured correctly.\n" +
                "Photo example : "

        mLiveDataStory = ViewModelProvider(this)[PredictViewModel::class.java]
        subscribe()
    }

    private fun subscribe() {
        val loadingObserver = Observer<Boolean> { aLoading ->
            showLoading(aLoading)
        }
        mLiveDataStory.getStatus().observe(this, loadingObserver)

        val resultObserver = Observer<String?> { aResult ->
            if (aResult != "") {
                showResult(aResult)
            }
        }
        mLiveDataStory.getListPredict().observe(this, resultObserver)
    }

    private fun showResult(aResult: String) {
        if (aResult != "failure") {
            Toast.makeText(this, "Image sent!", Toast.LENGTH_SHORT).show()

            val myIntent = Intent(this@PredictActivity, PredictResultActivity::class.java)
            myIntent.putExtra(PredictResultActivity.EXTRA_RESULT, aResult)
            startActivity(myIntent)
            finish()
        } else {
            Toast.makeText(this, "Upload failed, please retry!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setMyButtonEnable() {
        val resultDesc = binding.editDescriptionText.text
        binding.uploadButton.isEnabled = resultDesc != null && resultDesc.toString().isNotEmpty()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@PredictActivity,
                "com.example.adect",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        if (imageFile != null) {
            onAlertDialog(binding.root)
        } else {
            Toast.makeText(this@PredictActivity, "Please choose your image.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onAlertDialog(view: View) {
        val file = imageFile as File
        val desc: String = binding.editDescriptionText.text.toString()
        val description = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val builder = androidx.appcompat.app.AlertDialog.Builder(view.context)
        builder.setTitle("Predict Anemia")
        builder.setMessage("Send your image to be predicted for anemia?")

        builder.setPositiveButton(
            "Send") { dialog, id ->
//            Toast.makeText(this, "As Myself",Toast.LENGTH_SHORT).show()
            mLiveDataStory.getPredict(imageMultipart)

        }
        builder.setNeutralButton(
            "Cancel") { dialog, id ->
        }
        builder.show()
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            val result =  BitmapFactory.decodeFile(myFile.path)
//            Silakan gunakan kode ini jika mengalami perubahan rotasi
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(myFile.path),
//                true
//            )

            imageFile = myFile
            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@PredictActivity)

            imageFile = myFile
            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}