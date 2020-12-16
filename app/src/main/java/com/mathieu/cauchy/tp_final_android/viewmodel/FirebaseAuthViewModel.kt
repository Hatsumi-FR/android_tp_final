package com.mathieu.cauchy.tp_final_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.mathieu.cauchy.tp_final_android.FirebaseAuthRepository

class FirebaseAuthViewModel : ViewModel() {

    private val mFirebaseAuthRepository: FirebaseAuthRepository by lazy { FirebaseAuthRepository() }
    var mCurrentUser = MutableLiveData<FirebaseUser>()
    var mErrorProcess = MutableLiveData<Int>()

    init {
        mCurrentUser = mFirebaseAuthRepository.mCurrentUser
        mErrorProcess = mFirebaseAuthRepository.mErrorProcess
    }

    fun loginUser(email: String, password: String) {
        mFirebaseAuthRepository.loginUser(email, password)
    }

    fun registerNewUser(email: String, password: String) {
        mFirebaseAuthRepository.registerNewUser(email, password)
    }

    fun disconnectUser() {
        mFirebaseAuthRepository.disconnectUser()
    }
}