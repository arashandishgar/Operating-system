package logic;

import model.ProcessModel;
import model.GrandChardData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.filterReadyQueueByEntertime;

public class PreemptiveProcessSchedulerSRT extends PreemptiveProcessScheduler {


    public PreemptiveProcessSchedulerSRT(ArrayList<ProcessModel> processModels) {
        super(processModels, 1);
    }


    @Override
    protected ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart, int timer) {
        ArrayList<ProcessModel> processModels = (ArrayList<ProcessModel>) filterReadyQueueByEntertime(readyQueue, timer);
        if (existAnyProcess(processModels)) {
            Collections.sort(processModels,Comparator.comparingInt(ProcessModel::getExceTime));
            return processModels.get(0);
        }
        return null;
    }


    private boolean existAnyProcess(ArrayList<ProcessModel> processModels) {
        return processModels.size() != 0;
    }
}
