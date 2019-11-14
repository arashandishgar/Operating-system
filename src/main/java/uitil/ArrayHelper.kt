package uitil

import model.ProcessModel
import model.GrandChardData
import java.util.*

fun Collection<GrandChardData>.sumExceTime() =
    this.sumBy {
        it.exceTime
    }

fun Collection<GrandChardData>.sumWaitingTimeResutl() =
    this.sumBy {
        it.waitTime
    }

fun Collection<GrandChardData>.sumReturnTimeResult() =
    this.sumBy {
        it.returnTime
    }

fun Collection<ProcessModel>.filterReadyQueueByEntertimeBaseExceGrantChart(grantChart:LinkedList<GrandChardData>)=
    this.filter {
        it.enterTime <=grantChart.sumExceTime()
    }

fun Collection<ProcessModel>.filterReadyQueueByEntertime(timer:Int)=
    this.filter {
        it.enterTime <=timer
    }
fun Collection<GrandChardData>.filterBaseIdObject(id:Int)=
    this.filter {
        it.id ==id
    }