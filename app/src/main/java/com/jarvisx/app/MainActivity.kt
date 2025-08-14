package com.jarvisx.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var status: TextView

    private val requestAudioPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        status.append("\nMic permission: ${if (granted) "granted" else "denied"}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this).apply {
            text = "JarvisX ✨\nReady.\nTap fingerprint to unlock sensitive actions."
            textSize = 18f
            setPadding(32, 64, 32, 32)
        }
        status = tv
        setContentView(tv)

        // Request mic permission for voice features
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            requestAudioPermission.launch(Manifest.permission.RECORD_AUDIO)
        }

        // Quick biometric check
        val canAuth = BiometricManager.from(this)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuth == BiometricManager.BIOMETRIC_SUCCESS) {
            val executor = Executors.newSingleThreadExecutor()
            val prompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        runOnUiThread {
                            status.append("\nBiometric OK ✅")
                            // Kick MYMLLM with a starting intent
                            MYMLLM.runProtocol("startup", EngineContext(this@MainActivity, status))
                        }
                    }
                    override fun onAuthenticationError(code: Int, err: CharSequence) {
                        runOnUiThread { status.append("\nBiometric error: $err") }
                    }
                })
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle("JarvisX Secure Unlock")
                .setSubtitle("Confirm it's really you")
                .setNegativeButtonText("Cancel")
                .build()
            runOnUiThread { prompt.authenticate(info) }
        } else {
            status.append("\nBiometric not available")
            MYMLLM.runProtocol("startup", EngineContext(this, status))
        }
    }
}
