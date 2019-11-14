package logic.finish;

import model.OutPut;
import model.ProcessModel;
import model.GrandChardData;
import uitil.Json;

import java.util.*;

import static uitil.ArrayHelperKt.*;

public abstract class ProcessSchedualer {
    private LinkedList<GrandChardData> grantChart=new LinkedList<>();
    private LinkedList<ProcessModel> processModelsReady=new LinkedList<>();
    private final int numberOfProcess;

    public ProcessSchedualer(ArrayList<ProcessModel> processModelsReady) {
        this.processModelsReady.addAll(processModelsReady);
        numberOfProcess =processModelsReady.size();
        sortBaseInput();
    }

    private void sortBaseInput() {
        Collections.sort(processModelsReady, Comparator.comparingInt(ProcessModel::getEnterTime));
    }


    protected LinkedList<ProcessModel> getReadyQueue() {
        return processModelsReady;
    }

    protected LinkedList<GrandChardData> getGrantChart() {
        return grantChart;
    }

    protected abstract void schedualProcess();

    public final String getResultJson() {
        return Json.getInstance().convertArrayToJson(new OutPut(getAvrageExceTime(),getAvrageReturnTime(),getAvrageWaitingTime(),getGrantChart()));
    }
    protected int getNumberOfProcess(){
        return numberOfProcess;
    }

    public String run(){
        schedualProcess();
        return getResultJson();
    }
    protected final float getAvrageWaitingTime() {
        return ( ((float) sumWaitingTimeResutl(grantChart) /getNumberOfProcess()));
    }

    protected float getAvrageReturnTime() {
        return ( ((float) sumReturnTimeResult(grantChart) /getNumberOfProcess()));
    }

    protected float getAvrageExceTime() {

        return ( ((float) sumExceTime(grantChart) /getNumberOfProcess()));
    }


    }
