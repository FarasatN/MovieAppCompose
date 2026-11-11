package com.farasatnovruzov.newsappoffline.core.data.remote

/** API HTTP 200 qaytarsa da cavab gövdəsində `status = "error"` olduqda (məs. kvota bitib) atılır. */
class NewsApiException(message: String) : Exception(message)
