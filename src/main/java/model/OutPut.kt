package model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
 data class OutPut(
    val exceTimeAvrage: Float,
    val returnTimeAvrage: Float,
    val waitTimeAvrage: Float,
    val grantChart: List<GrandChardData>
)