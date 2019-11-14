package logic;

import model.GrandChardData;
import model.ProcessModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.filterReadyQueueByEntertime;
import static uitil.IOHelper.println;

public class PreemptiveProcessSchedulerRR extends PreemptiveProcessScheduler {


    public PreemptiveProcessSchedulerRR(ArrayList<ProcessModel> processModels, int unitTimeForEachProcesss) {
        super(processModels, unitTimeForEachProcesss);
    }

    @Override
    protected ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart, int timer) {
        ArrayList<ProcessModel> processModels = (ArrayList<ProcessModel>) filterReadyQueueByEntertime(readyQueue, timer);
        Collections.sort(processModels,Comparator.comparingInt(ProcessModel::getPriorrity));
        for(ProcessModel processModel:processModels){
            println("id : "+processModel.getId());
            println("priority : "+processModel.getPriorrity());
        }
        if (existAnyProcess(processModels)) {
            ProcessModel processModel= processModels.get(0);

            println("id were give : "+processModel.getId());
            processModel.setPriorrity(processModel.getPriorrity()+1);
            return processModel;
        }
        return null;
    }
    private boolean existAnyProcess(ArrayList<ProcessModel> processModels) {
        return processModels.size() != 0;
    }
}
