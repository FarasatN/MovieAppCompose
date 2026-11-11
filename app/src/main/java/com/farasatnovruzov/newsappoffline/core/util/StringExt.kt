package com.farasatnovruzov.newsappoffline.core.util

/**
 * SQL `LIKE` üçün xüsusi simvolları (\, %, _) escape edir ki, istifadəçi daxil etdiyi
 * mətn adi simvol kimi axtarılsın. Sıra vacibdir: əvvəlcə `\` ikiləşdirilir.
 */
fun String.escapeSqlLike(): String =
    replace("\\", "\\\\")
        .replace("%", "\\%")
        .replace("_", "\\_")
