package logic.finish;

import model.ProcessModel;
import model.GrandChardData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.filterReadyQueueByEntertimeBaseExceGrantChart;
import static uitil.ArrayHelperKt.sumExceTime;
import static uitil.IOHelper.println;

public class NonPreemptiveProcessSchedulerHRRN extends NonPreemptiveProcessScheduler {

    public NonPreemptiveProcessSchedulerHRRN(ArrayList<ProcessModel> processModels) {
        super(processModels);
    }

    @Override
    protected ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart) {
        ArrayList<ProcessModel> buffer = (ArrayList<ProcessModel>) filterReadyQueueByEntertimeBaseExceGrantChart(readyQueue, grantChart);
        buffer.sort(Comparator.comparingDouble(this::calculatePriority));
        if (buffer.size() == 0) {
            LinkedList<ProcessModel> b= (LinkedList) readyQueue.clone();
          b.sort(Comparator.comparingInt(ProcessModel::getEnterTime).thenComparingDouble(this::calculatePriority));
            return b.get(0);
        }
        return buffer.get(buffer.size()-1);
    }
    private float calculatePriority(ProcessModel value){
        float p=((float)((sumExceTime(getGrantChart())-value.getEnterTime())+value.getExceTime())/value.getExceTime());
        println(p);
        return  ((float)((sumExceTime(getGrantChart())-value.getEnterTime())+value.getExceTime())/value.getExceTime());
    }


}
