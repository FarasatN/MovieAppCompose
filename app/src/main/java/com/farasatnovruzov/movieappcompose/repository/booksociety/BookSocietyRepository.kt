package com.farasatnovruzov.movieappcompose.repository.booksociety

import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.data.Resource
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import com.farasatnovruzov.movieappcompose.network.booksociety.BooksApi
import javax.inject.Inject

class BookSocietyRepository @Inject constructor(private val api: BooksApi) {
//    private val dataOrExceptionResourceBooks = DataOrException<List<Item>, Boolean, Exception>()
//    private val dataOrExceptionResourceBookInfo = DataOrException<Item, Boolean, Exception>()
//
//    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
//        dataOrExceptionResourceBooks.loading = true
//        try {
//            dataOrExceptionResourceBooks.data = api.getAllBooks(searchQuery).items
//            if (dataOrExceptionResourceBooks.data!!.isNotEmpty()) dataOrExceptionResourceBooks.loading =
//                false
//        } catch (e: Exception) {
//            dataOrExceptionResourceBooks.e = e
//        }
//        return dataOrExceptionResourceBooks
//    }
//
//    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
//        val response = try {
//            dataOrExceptionResourceBookInfo.loading = true
//            dataOrExceptionResourceBookInfo.data = api.getBookInfo(bookId)
//            if (dataOrExceptionResourceBookInfo.data.toString()
//                    .isNotEmpty()
//            ) dataOrExceptionResourceBookInfo.loading = false
//            else {
//            }
//        } catch (e: Exception) {
//            dataOrExceptionResourceBookInfo.e = e
//            return dataOrExceptionResourceBookInfo
//        }
//        return dataOrExceptionResourceBookInfo
//    }
//}

//------------------------------------------------------------------------------


    // 1. Bütün Kitabları Axtar (getBooks)
    // Funksiya, Resource<List<Item>> qaytarır
    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        // Asinxron əməliyyat başladığı üçün ilk növbədə Loading vəziyyəti göndərilə bilər.
        // Lakin Repository adətən məlumatı aldıqdan sonra vəziyyəti qaytarır.
        return try {
            // API çağırışını et
            Resource.Loading("Loading ...")
            val response = api.getAllBooks(searchQuery)
            // Cavabı yoxla
            if (response.items.isNullOrEmpty()) {
                // Əgər cavab uğurludursa, amma data yoxdursa
                Resource.Error(message = "Axtarışa uyğun kitab tapılmadı: ${response.items}")
            } else {
                // Uğurlu Vəziyyət
                Resource.Success(data = response.items)
            }
        } catch (e: Exception) {
            // Xəta Vəziyyəti
            Resource.Error(
                message = "Kitabları yükləyərkən xəta: ${e.localizedMessage}",
                data = null // Uğursuzluq halında data boş olur
            )
        }
    }

    // 2. Kitabın Məlumatını Al (getBookInfo)
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return try {
            Resource.Loading("Loading ...")
            val item = api.getBookInfo(bookId)
            if (item.id.isNullOrEmpty()) {
                Resource.Error(message = "Kitab haqqında məlumat tapılmadı!: ${item.accessInfo} ")
            } else {
                return Resource.Success(data = item)
            }
        } catch (e: Exception) {
            return Resource.Error(message = "Kitab məlumatlarını alarkən xəta: ${e.localizedMessage}")
        }
    }

}

