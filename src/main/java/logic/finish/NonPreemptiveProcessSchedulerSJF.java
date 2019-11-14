package logic.finish;

import model.ProcessModel;
import model.GrandChardData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.filterReadyQueueByEntertimeBaseExceGrantChart;

public class NonPreemptiveProcessSchedulerSJF extends NonPreemptiveProcessScheduler {

    public NonPreemptiveProcessSchedulerSJF(ArrayList<ProcessModel> processModels) {
        super(processModels);
    }

    @Override
    protected ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart) {
        ArrayList<ProcessModel> buffer = (ArrayList<ProcessModel>) filterReadyQueueByEntertimeBaseExceGrantChart(readyQueue, grantChart);
        buffer.sort(Comparator.comparingInt(ProcessModel::getExceTime));
        if (buffer.size() == 0) {
            LinkedList<ProcessModel> b= (LinkedList<ProcessModel>) readyQueue.clone();
            b.sort(Comparator.comparingInt(ProcessModel::getEnterTime).thenComparingDouble(ProcessModel::getExceTime));
            return b.get(0);
        }
        return buffer.get(0);
    }

}
