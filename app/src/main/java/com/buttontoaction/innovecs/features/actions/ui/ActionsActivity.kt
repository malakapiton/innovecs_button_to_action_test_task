package com.buttontoaction.innovecs.features.actions.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.buttontoaction.innovecs.R


class ActionsActivity : AppCompatActivity() {

    private lateinit var actionsButton: Button
    private lateinit var actionsViewModel: ActionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionsViewModel = ViewModelProvider(this)[ActionsViewModel::class.java]

        actionsButton = findViewById(R.id.actionsButton)

        actionsButton.setOnClickListener {
            actionsViewModel.actionTypeLiveData.observe(this) {
                applyActionType(it)
            }
        }

        actionsViewModel.loadActionType()
    }

    private fun applyActionType(actionType: String) {
        when (actionType) {
            ANIMATION -> {
                ObjectAnimator.ofFloat(actionsButton, View.ROTATION, 0f, 360f).setDuration(750).start()
            }
            TOAST -> {
                if (isInternetConnectionEnabled(this)) {
                    Toast.makeText(applicationContext, "Action is Toast!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "No internet connection, please enable it and try again", Toast.LENGTH_LONG).show()
                }
            }
            CALL -> {
                openChooseContactScreen()
            }
            NOTIFICATION -> {
                Toast.makeText(applicationContext, "Action is Notification!", Toast.LENGTH_LONG).show()
                openChooseContactScreen()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            val contactUri = data?.data
            val projection = arrayOf(CommonDataKinds.Phone.NUMBER)
            val cursor = contentResolver.query(
                contactUri!!, projection,
                null, null, null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val numberIndex = cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberIndex)

                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                startActivity(intent)
            }
        }
    }

    private fun isInternetConnectionEnabled(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    private fun openChooseContactScreen() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER)
    }

    companion object {
        const val ANIMATION = "animation"
        const val TOAST = "toast"
        const val CALL = "call"
        const val NOTIFICATION = "notification"
        const val REQUEST_SELECT_PHONE_NUMBER = 1
    }
}