package logic;

import logic.finish.ProcessSchedualer;
import model.GrandChardData;
import model.ProcessModel;

import java.util.ArrayList;
import java.util.LinkedList;

import static uitil.ArrayHelperKt.filterBaseIdObject;

public abstract class PreemptiveProcessScheduler extends ProcessSchedualer {
    protected final int unitTimeForEachProcesss;

    public PreemptiveProcessScheduler(ArrayList<ProcessModel> processModels, int unitTimeForEachProcesss) {
        super(processModels);
        this.unitTimeForEachProcesss = unitTimeForEachProcesss;
    }


    @Override
    protected void schedualProcess() {
        int timer = 0;
        Buffer buffer = new Buffer(0, null);
        while (getReadyQueue().size() != 0) {

            ProcessModel processModel = getNextProcessFromReadyQueue(getReadyQueue(), getGrantChart(), timer);
            if (processModel != null) {
                //modify timer for example unitTimeForEachProcesss=2 timeEcxe=1
                buffer = calculate(processModel, timer);
                buffer.grandChardData.setLastTimeSeen(timer);
                getGrantChart().add(buffer.grandChardData);
            }
            timer = timer + unitTimeForEachProcesss + buffer.buffer;
        }
    }

    private Buffer calculate(ProcessModel processModel, int timer) {
        GrandChardData grandChardData = new GrandChardData(processModel.getId(), 0, 0, 0);
        grandChardData.setWaitTime(calculateWaitTime(processModel, timer));
        int exceTime = calculateExceAndSetRemainTime(processModel);
        Buffer buffer = new Buffer(Math.min(0,exceTime-unitTimeForEachProcesss), grandChardData);
        grandChardData.setExceTime(exceTime);
        int returnTime = calculateReturnTimeAndDeleteProcessIfFinish(processModel, timer, buffer);
        grandChardData.setReturnTime(returnTime);
        return buffer;
    }

    private int calculateReturnTimeAndDeleteProcessIfFinish(ProcessModel processModel, int timer, Buffer buffer) {
        if (processModel.getRemianTime() == 0) {
            int result = (timer + buffer.buffer + unitTimeForEachProcesss) - processModel.getEnterTime();

            getReadyQueue().remove(processModel);
            return result;

        }
        return 0;
    }

    private int calculateExceAndSetRemainTime(ProcessModel processModel) {
        int buffer = processModel.getRemianTime() - unitTimeForEachProcesss;
        if (buffer < 0) {
            int temp=processModel.getRemianTime();
            processModel.setRemianTime(0);
            return temp;
        }
        processModel.setRemianTime(buffer);
        return unitTimeForEachProcesss;
    }

    private int calculateWaitTime(ProcessModel processModel, int timer) {
        ArrayList<GrandChardData> arrayList = (ArrayList<GrandChardData>) filterBaseIdObject(getGrantChart(), processModel.getId());
        if (arrayList.size() == 0) {
            return timer - processModel.getEnterTime();
        }
        GrandChardData grandChardData = arrayList.get(arrayList.size() - 1);

        return timer - (grandChardData.getLastTimeSeen()+grandChardData.getExceTime());
    }

    protected abstract ProcessModel getNextProcessFromReadyQueue(LinkedList<ProcessModel> readyQueue, LinkedList<GrandChardData> grantChart, int timer);


    static class Buffer {
        int buffer;
        GrandChardData grandChardData;

        public Buffer(int buffer, GrandChardData grandChardData) {
            this.buffer = buffer;
            this.grandChardData = grandChardData;
        }
    }
}
