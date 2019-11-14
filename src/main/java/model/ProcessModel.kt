package model

import com.squareup.moshi.JsonClass
import java.util.concurrent.atomic.AtomicInteger

@JsonClass(generateAdapter = true)
data class ProcessModel(
    var enterTime: Int,
    var exceTime: Int,
    @Transient
    val id: Int = atomicInteger.incrementAndGet()) {
    //it just use for preemptive
    @Transient
    var remianTime=exceTime
    @Transient
    var priorrity=0
    companion object {
        val atomicInteger = AtomicInteger()

    }

}
