package com.farasatnovruzov.newsappoffline.core.di

import org.koin.core.qualifier.named

/** `List<String>` tipi çox ümumidir, ona görə API açarları siyahısı ad ilə işarələnir. */
val ApiKeysQualifier = named("ApiKeys")
