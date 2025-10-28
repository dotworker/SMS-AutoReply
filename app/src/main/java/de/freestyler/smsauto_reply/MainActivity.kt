package de.freestyler.smsauto_reply

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 123
    private lateinit var autoReplySwitch: Switch
    private lateinit var delaySwitch: Switch // NEU
    private lateinit var keywordEditText: EditText
    private lateinit var replyMessageEditText: EditText
    private lateinit var allowedNumbersEditText: EditText // NEU
    private lateinit var saveButton: Button
    private lateinit var donateLink: TextView // NEU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        loadSavedSettings() // NEU
        requestPermissions()
        setupClickListeners()
    }

    private fun initializeViews() {
        autoReplySwitch = findViewById(R.id.autoReplySwitch)
        delaySwitch = findViewById(R.id.delaySwitch) // NEU
        keywordEditText = findViewById(R.id.keywordEditText)
        replyMessageEditText = findViewById(R.id.replyMessageEditText)
        allowedNumbersEditText = findViewById(R.id.allowedNumbersEditText) // NEU
        saveButton = findViewById(R.id.saveButton)
        donateLink = findViewById(R.id.donateLink) // NEU
    }

    // NEU: Gespeicherte Einstellungen laden
    private fun loadSavedSettings() {
        autoReplySwitch.isChecked = AutoReplySettings.isAutoReplyEnabled(this)
        delaySwitch.isChecked = AutoReplySettings.isDelayEnabled(this) // NEU
        keywordEditText.setText(AutoReplySettings.getKeyword(this))
        replyMessageEditText.setText(AutoReplySettings.getReplyMessage(this))
        allowedNumbersEditText.setText(AutoReplySettings.getAllowedNumbers(this))
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS
        )

        val needsPermission = permissions.any {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (needsPermission) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
        }
    }

    private fun setupClickListeners() {
        saveButton.setOnClickListener {
            val keyword = keywordEditText.text.toString().trim()
            val replyMessage = replyMessageEditText.text.toString().trim()
            val allowedNumbers = allowedNumbersEditText.text.toString().trim() // NEU
            val isEnabled = autoReplySwitch.isChecked
            val isDelayEnabled = delaySwitch.isChecked // NEU

            if (keyword.isEmpty() || replyMessage.isEmpty()) {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //AutoReplySettings.saveSettings(this, isEnabled, keyword, replyMessage)
            // Erweitert: Auch erlaubte Nummern speichern
            AutoReplySettings.saveSettings(this, isEnabled, keyword, replyMessage, allowedNumbers,isDelayEnabled)
            Toast.makeText(this, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show()
        }
        // NEU: PayPal-Link Click-Listener
        donateLink.setOnClickListener {
            val paypalUrl = getString(R.string.paypal_url) // HIER DEINEN PAYPAL-LINK EINFÜGEN
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paypalUrl))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.paypal_link_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            val allGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (!allGranted) {
                Toast.makeText(this, getString(R.string.sms_permission_required), Toast.LENGTH_LONG).show()
            }
        }
    }
}
