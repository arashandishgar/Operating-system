package model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Input(val arrayList:List<ProcessModel>,val type:String,val unitTime:Int)