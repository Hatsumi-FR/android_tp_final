package com.mathieu.cauchy.tp_final_android.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.mathieu.cauchy.tp_final_android.R
import com.mathieu.cauchy.tp_final_android.viewmodel.FirebaseAuthViewModel
import kotlinx.android.synthetic.main.activity_firebase_login.*

class FirebaseLoginActivity : AppCompatActivity() {

    private lateinit var mViewModel: FirebaseAuthViewModel

    private var mObserverUser = Observer<FirebaseUser> {
        updateUser(it)
    }

    private var mObserverError = Observer<Int> {
        updateError(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)

        mViewModel = ViewModelProvider(this)[FirebaseAuthViewModel::class.java]
        firebaseButtonRegister.setOnClickListener { register() }
        firebaseButtonLogin.setOnClickListener { login() }
        firebaseButtonDisconnect.setOnClickListener { disconnect() }
    }

    override fun onStart() {
        super.onStart()
        mViewModel.mCurrentUser.observe(this, mObserverUser)
        mViewModel.mErrorProcess.observe(this, mObserverError)
    }

    override fun onStop() {
        mViewModel.mCurrentUser.removeObserver(mObserverUser)
        mViewModel.mErrorProcess.removeObserver(mObserverError)
        super.onStop()
    }

    private fun checkConformityFields(): Boolean {
        var isValid = true

        if (TextUtils.isEmpty(firebaseUserEmail.text.toString()) || TextUtils.isEmpty(firebaseUserPassword.text.toString())) {
            isValid = false
        }
        firebaseError.text = "empty field"
        return isValid
    }

    private fun login() {
        if (checkConformityFields()) {
            mViewModel.loginUser(firebaseUserEmail.text.toString(), firebaseUserPassword.text.toString())
        }
    }

    private fun register() {
        if (checkConformityFields()) {
            mViewModel.registerNewUser(firebaseUserEmail.text.toString(), firebaseUserPassword.text.toString())
        }
    }

    private fun disconnect() {
        mViewModel.disconnectUser()
    }

    private fun updateUser(user : FirebaseUser) {
        user.let {
            firebaseLog.text = "${user.uid}-${user.email}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateError(code : Int) {
        when(code) {
            5 -> {
                firebaseError.text = "disconnected"
                firebaseLog.text = "none"
            }
            9 -> firebaseError.text = "current user null"
            10 -> firebaseError.text = "Error when creating"
            11 -> firebaseError.text = "Error when login"
            else -> firebaseError.text = "All is good"
        }
    }
}