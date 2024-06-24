package com.app.freightCyber.presentation.auth.authentication_method.facial

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseArray
import android.widget.Toast
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityFacialBinding
import com.app.freightCyber.presentation.auth.authentication_method.facial.BitmapUtils.getBitmap
import com.app.freightCyber.presentation.auth.authentication_method.voice.VoiceActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService


@AndroidEntryPoint
class FacialActivity : BaseActivity<ActivityFacialBinding>() {

    private val viewModel: FacialActivityVM by viewModels()
    var preview: Preview? = null
    private val PERMISSION_REQUEST_CODE: Int = 101
    var cameraSelector: CameraSelector? = null
    private var outputDirectory: File? = null
    var cameraExecutor: ExecutorService? = null
    var imageAnalysis: ImageAnalysis? = null
    var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    var cameraProvider: ProcessCameraProvider? = null
    var faceTurnLeft = false
    private var byteArray: ByteArray? = null
    private var imageInputFromBitmap: InputImage? = null
    var imageCapture: ImageCapture? = null
    var timer: CountDownTimer? = null
    var livedataUpdate = MutableLiveData<Float>()
    var currentProgress = 0
    override fun getLayoutResource(): Int {
        return R.layout.activity_facial
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
      //  mCheckPermission()
        setStatusTextColor()
        initOnClick()
     //   inItCamera()
     //   startCam(1)
    }

    private fun mCheckPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCam(1)
            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable camera functionality)
                showToast("Required camera permission")
            }
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
                 R.id.circular_progress , R.id.progressSeekbar-> {
                      val intent = Intent(this@FacialActivity , VoiceActivity::class.java)
                      startActivity(intent)
                  }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    private fun inItCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture?.addListener({}, ContextCompat.getMainExecutor(this))

        try {
            cameraProvider = cameraProviderFuture?.get()!!
            preview = Preview.Builder().build()
            preview!!.targetRotation = windowManager.defaultDisplay.rotation
            preview!!.setSurfaceProvider(binding.viewFinder.surfaceProvider)

            imageCapture = ImageCapture.Builder().build()
            imageCapture!!.targetRotation = windowManager.defaultDisplay.rotation
            imageAnalysis =
                ImageAnalysis.Builder().setImageQueueDepth(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
                    .build()


        } catch (e: ExecutionException) {
            Toast.makeText(this, e.message.toString() + "", Toast.LENGTH_SHORT).show()
        } catch (e: InterruptedException) {
            Toast.makeText(this, e.message.toString() + "", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }


    }

    private fun startCam(type: Int) {
        if (type == 1) {
            try {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this as LifecycleOwner, cameraSelector!!, preview, imageCapture, imageAnalysis
                )
                imageAnalysis?.setAnalyzer(ContextCompat.getMainExecutor(this),
                    object : ImageAnalysis.Analyzer {

                        @SuppressLint("UnsafeOptInUsageError")
                        override fun analyze(image: ImageProxy) {
                            imageInputFromBitmap = InputImage.fromBitmap(getBitmap(image)!!, 0)
                            //   if (this@FaceDetectionActivity.i == 1) {
                            if (timer == null) {
                                timer()

                            }
                            image.close()
                        }
                        //  }
                    })
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this as LifecycleOwner, cameraSelector!!, preview, imageCapture, imageAnalysis
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun timer() {
        timer = object : CountDownTimer(2000, 100) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                detectFaces(imageInputFromBitmap!!)
                timer()

            }
        }
        timer?.start()
    }


    var i = 1;
    private fun detectFaces(image: InputImage) {
        Log.i("Times", "" + i)
        i++

        // [START set_detector_options]
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setMinFaceSize(0.15f).enableTracking().build()
        // [END set_detector_options]
        val realTimeOpts =
            FaceDetectorOptions.Builder().setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build()

        // [START get_detector]
        val detector = FaceDetection.getClient(options)
        val detectorOne = FaceDetector.Builder(this).setTrackingEnabled(false)
            .setLandmarkType(FaceDetector.ALL_LANDMARKS).setMode(FaceDetector.FAST_MODE).build();

        val frame: Frame = Frame.Builder().setBitmap(image.bitmapInternal).build()
        val sparseArray: SparseArray<Face>? = detectorOne.detect(frame)
        detectorOne.release()

        for (i in 0 until sparseArray?.size()!!) {          //can't use for-each loops for SparseArrays
            val face: Face = sparseArray.valueAt(i)
            //get it's coordinates
            image.bitmapInternal?.let {
                if (face.height + face.position.y <= it.height && face.width + face.position.x <= it.width && face.position.x >= 0) {
                    val faceBitmap: Bitmap = Bitmap.createBitmap(
                        it,
                        face.position.x.toInt(),
                        face.position.y.toInt(),
                        face.width.toInt(),
                        face.height.toInt()
                    )

                    val stream = ByteArrayOutputStream()
                    faceBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    byteArray = stream.toByteArray()
                    faceBitmap.recycle()
                }
            }
            //Do whatever you want with this cropped Bitmap

        }

        detector.process(image).addOnSuccessListener { faces ->
            if (faces.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val newProgress = binding.circularProgress.progress + 10.0
                    binding.circularProgress.setProgress(
                        newProgress.coerceIn(0.0, 100.0).toFloat().toDouble(), 100.0
                    )
                    Log.i("currentprogress", binding.circularProgress.progress.toString())

                }, 0)

                if (binding.circularProgress.progress.toString().equals("100.0")) {
                    if (currentProgress == 0) {
                        openNewPage()
                    }
                }

            } else {
                Log.i("NoFaceDetected", "No face detected")
            }
        }.addOnFailureListener { e ->
            Log.i("FaceDetectionError", "Face detection failed: ${e.message}", e)
        }

    }

    private fun openNewPage() {
        currentProgress = 1
        showNewLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            hideNewLoading()
            val intent = Intent(this@FacialActivity, VoiceActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}