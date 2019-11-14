package logic.finish;

import model.ProcessModel;
import model.GrandChardData;

import java.util.ArrayList;
import java.util.LinkedList;

public class NonPreemptiveProcessSchedulerFCFS extends NonPreemptiveProcessScheduler {

    public NonPreemptiveProcessSchedulerFCFS(ArrayList<ProcessModel> processModels) {
        super(processModels);
    }

    @Override
    protected ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart) {
        //not neceesary because in constructor set
        /*readyQueue.sort((Comparator.comparingInt(ProcessModel::getEnterTime)));*/
        return readyQueue.get(0);
    }

}
