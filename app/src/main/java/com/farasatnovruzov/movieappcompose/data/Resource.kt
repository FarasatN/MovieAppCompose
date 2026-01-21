package com.farasatnovruzov.movieappcompose.data

// Resource, bütün mümkün vəziyyətlər üçün üst sinifdir (Sealed Class)
// data class-indan daha deqiqdir

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    // 1. Loading Vəziyyəti
    class Loading<T>(data: T? = null) : Resource<T>(data)
    // 2. Success Vəziyyəti
    class Success<T>(data: T) : Resource<T>(data)
    // 3. Error Vəziyyəti
    // Xətanı mesaj və ya Exception obyekti ilə ötürmək olar
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}