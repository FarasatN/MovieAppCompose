package com.farasatnovruzov.movieappcompose.screens.booksociety.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.model.booksociety.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class BookSocietyLoginScreenViewModel : ViewModel() {

    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

//    private val _loading = MutableLiveData(false)
//    val loading: MutableLiveData<Boolean> = _loading

    // Replace MutableLiveData with MutableStateFlow
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow() // Expose as an immutable StateFlow



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
                                val displayName = task.result.user?.email?.split('@')?.get(0)
                                createUser(displayName)
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

    fun createUser(displayName: String?){
        val userId = auth.currentUser?.uid
//        val user = mutableMapOf<String, Any>()
        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer"
        ).toMap()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
        FirebaseFirestore.getInstance().collection("users").add(user)

    }
}

