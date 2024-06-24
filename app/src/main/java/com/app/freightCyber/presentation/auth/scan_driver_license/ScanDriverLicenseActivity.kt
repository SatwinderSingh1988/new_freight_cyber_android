package com.app.freightCyber.presentation.auth.scan_driver_license

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityScanDriverLicenseBinding
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class ScanDriverLicenseActivity : BaseActivity<ActivityScanDriverLicenseBinding>() {

    private val viewModel: ScanDriverLicenseActivityVM by viewModels()
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf (Manifest.permission.CAMERA).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var permissionGranted = true
        permissions.entries.forEach { if (it.key in REQUIRED_PERMISSIONS && it.value == false) permissionGranted = false }
        if (!permissionGranted) {
            showToast("Permission request denied")
        } else {
            startCamera()
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_scan_driver_license
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        initializeCamera()
    }

    private fun initializeCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch(_: Exception) {

            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this , it) == PackageManager.PERMISSION_GRANTED
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.imgClick -> {
                    takePhoto()
                    //startActivity(Intent(this@ScanDriverLicenseActivity , LicenseVerificationActivity::class.java))
                }
            }
        })
    }


    private fun takePhoto() {
        val myAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        myAudioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE

        val imageCapture = imageCapture ?: return
        imageCapture.takePicture(ContextCompat.getMainExecutor(this@ScanDriverLicenseActivity),
            object : ImageCapture.OnImageCapturedCallback() {
                @ExperimentalGetImage
                override fun onCaptureSuccess(image: ImageProxy) {
                    LicenseVerificationActivity.image = image.toBitmap()
                    startActivity(Intent(this@ScanDriverLicenseActivity , LicenseVerificationActivity::class.java))
                    super.onCaptureSuccess(image)
                }
                override fun onError(exception: ImageCaptureException) {
                    showToast("some problem occur please try again later")
                    super.onError(exception)
                }
            }
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
