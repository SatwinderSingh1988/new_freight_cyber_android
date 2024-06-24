package com.app.freightCyber.presentation.auth.authentication_method.voice

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieDrawable
import com.app.freightCyber.R
import com.app.freightCyber.databinding.ActivityVoiceBinding
import com.app.freightCyber.presentation.auth.verify_otp.VerifyOtpActivity
import com.app.freightCyber.core.base.activity.BaseActivity
import com.app.freightCyber.core.base.view_model.BaseViewModel
import com.app.freightCyber.utils.showInfoToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VoiceActivity : BaseActivity<ActivityVoiceBinding>() {

    private val viewModel: VoiceActivityVM by viewModels()
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private lateinit var speechRecognizer: SpeechRecognizer
    private var preEnrolledPassphrase = ""
    var isGranted = false

    override fun getLayoutResource(): Int {
        return R.layout.activity_voice
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreateView() {
        setStatusTextColor()
        initOnClick()
        checkAudioPermission()
        voiceRecodeClickEvent()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
    }

    override fun onResume() {
        super.onResume()
        checkAudioPermission()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun voiceRecodeClickEvent() {
        binding.animationView.setOnTouchListener { v, event ->
            if (!isGranted) {
                showInfoToast("permission required")
                goToSettingScreen()
                return@setOnTouchListener false
            } else {
                checkDeviceSupportRecognition()
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.animationView.repeatCount = LottieDrawable.INFINITE
                        binding.animationView.playAnimation()
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        binding.animationView.cancelAnimation()
                        binding.animationView.progress = 0f
                        showNewLoading()
                        goToNextScreen()
                    }
                }
                true
            }
        }
    }

    private fun goToNextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            hideNewLoading()
         //   showInfoToast("voice registered")
            val intent = Intent(this@VoiceActivity, VerifyOtpActivity::class.java)
            intent.putExtra("FROM", "AUTHENTICATION_SCREEN")
            startActivity(intent)
        }, 3000)
    }

    private fun goToSettingScreen() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun checkAudioPermission() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
        } else {
            isGranted = true
        }
    }

    private fun checkDeviceSupportRecognition() {
        if (SpeechRecognizer.isRecognitionAvailable(this@VoiceActivity)) {

            val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this@VoiceActivity)

            speechRecognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle) {

                }

                override fun onBeginningOfSpeech() {

                }

                override fun onRmsChanged(rmsdB: Float) {

                }

                override fun onBufferReceived(buffer: ByteArray?) {

                }

                override fun onEndOfSpeech() {

                }

                override fun onError(error: Int) {

                }

                override fun onResults(results: Bundle) {
                    val voiceResults =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!voiceResults.isNullOrEmpty()) {
                        val recognizedText = voiceResults[0]
                        preEnrolledPassphrase = recognizedText
                        // authenticateUser(recognizedText)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {

                }

                override fun onEvent(eventType: Int, params: Bundle?) {

                }
            })
            speechRecognizer.startListening(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH))
        }
    }

    private fun authenticateUser(recognizedText: String) {
        if (recognizedText.equals(preEnrolledPassphrase, ignoreCase = true)) {
            showToast("successful")
        } else {
            showToast("failed")
        }
    }

    private fun initOnClick() {
        viewModel.onClick.observe(this, Observer {
            when (it?.id) {
                R.id.imgBack -> {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setStatusTextColor() {
        window.decorView.systemUiVisibility = 0
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            isGranted =
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }
}