package com.farasatnovruzov.movieappcompose.screens.booksociety.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class BookSocietyLoginScreenViewModel : ViewModel() {

    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //    private val _loading = MutableStateFlow(false)
    private val _loading = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> = _loading


    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
//        if (_loading.value == false) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "FB",
                                "signInWithEmailAndPassword: Yayayay! ${task.result.user?.email.toString() + " : " + task.result.user?.uid.toString()}"
                            )
                            home()
                        } else {
//                        _loading.value = false
                        }
                    }
//            }
            } catch (ex: Exception) {
                Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
            }


        }


    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                if (_loading.value == false) {
                    _loading.value = true
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(
                                    "FB",
                                    "createUserWithEmailAndPassword: Create! : ${task.result.toString()}"
                                )
                                home()

                            } else {
                                Log.d(
                                    "FB",
                                    "createUserWithEmailAndPassword: ${task.result.toString()}"
                                )
                            }
                            _loading.value = false

                        }
                }
            } catch (ex: Exception) {
                Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
            }

        }

}