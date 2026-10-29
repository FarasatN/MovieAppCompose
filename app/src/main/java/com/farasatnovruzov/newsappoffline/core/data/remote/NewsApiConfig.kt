package com.farasatnovruzov.newsappoffline.core.data.remote

object NewsApiConfig {
    const val BASE_URL = "https://newsdata.io/api/1/latest"

    // Pulsuz API Key-lərinizin siyahısı. İstədiyiniz zaman yenilərini bura əlavə edə bilərsiniz.
    val API_KEYS = mutableListOf(
        "pub_afa06c3d3e9f4d75a0fde98a6fe00010",
         "pub_582732dde4c64687bef71a02a1cf37a3",
         "pub_c3e5dea93911498dbdfc29de9f07bd44"
    )
}
