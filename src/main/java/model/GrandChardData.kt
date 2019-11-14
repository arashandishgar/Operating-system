package model

data class GrandChardData(val id:Int, var waitTime:Int=0, var exceTime:Int=0, var returnTime:Int=0){
    @Transient
    var lastTimeSeen=0
}