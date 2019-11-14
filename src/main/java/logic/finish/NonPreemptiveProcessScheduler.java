package logic.finish;


import model.ProcessModel;
import model.GrandChardData;

import java.util.ArrayList;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.sumExceTime;

public abstract class NonPreemptiveProcessScheduler extends ProcessSchedualer {


    public NonPreemptiveProcessScheduler(ArrayList<ProcessModel> processModels) {
        super(processModels);
    }

    @Override
    protected final void schedualProcess() {

       /*  getQeueInput().forEach(processModel -> {
             ResultModel resultModel=new ResultModel(processModel.getId(),0,0,0);
             Buffer buffer=calculateWaitingTime(getGrantChart(),processModel);
             resultModel.setWaitTime(buffer.waitintTime);
             resultModel.setExceTime(calculateExceTime(getGrantChart(),processModel,buffer.extraTime));
             //it must be at end donot change
             resultModel.setReturnTime(calculateReturnTimeResult(getGrantChart(),processModel));
             getGrantChart().addLast(resultModel);
         });*/

        for (int i = 0; i < getNumberOfProcess(); i++) {
            ProcessModel processModel = getNextProcessFromReadyQueue(getReadyQueue(),getGrantChart());
            getReadyQueue().remove(processModel);
            addGrantChart(processModel);
        }
    }

    private void addGrantChart(ProcessModel processModel) {
        GrandChardData grandChardData = new GrandChardData(processModel.getId(), 0, 0, 0);
        Buffer buffer = calculateWaitingTime(getGrantChart(), processModel);
        grandChardData.setWaitTime(buffer.waitintTime);
        grandChardData.setExceTime(calculateExceTime( processModel, buffer.extraTime));
        //it must be at end donot change
        grandChardData.setReturnTime(calculateReturnTimeResult(getGrantChart(), processModel));
        getGrantChart().addLast(grandChardData);
    }

    protected int calculateReturnTimeResult(LinkedList<GrandChardData> grantChart, ProcessModel processModel) {
        return (sumExceTime(grantChart) + processModel.getExceTime())-processModel.getEnterTime();
    }


    private int calculateExceTime( ProcessModel processModel, int extraTime) {

        return processModel.getExceTime() + extraTime;
    }

    private Buffer calculateWaitingTime(LinkedList<GrandChardData> grantChart, ProcessModel processModel) {
        int sumExeTimeBeforeProcess = sumExceTime(getGrantChart());
        if (sumExeTimeBeforeProcess >= processModel.getEnterTime()) {
            return new Buffer(sumExeTimeBeforeProcess - processModel.getEnterTime(), 0);
        }
        return new Buffer(processModel.getEnterTime(), processModel.getEnterTime() - sumExeTimeBeforeProcess);
    }

    //implement structure
    protected abstract ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart);



    private static class Buffer {
        private int extraTime;
        private int waitintTime;

        public Buffer(int waitintTime, int extraTime) {
            this.extraTime = extraTime;
            this.waitintTime = waitintTime;
        }
    }

}
