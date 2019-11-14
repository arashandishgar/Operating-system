import logic.PreemptiveProcessSchedulerRR;
import logic.PreemptiveProcessSchedulerSRT;
import logic.finish.NonPreemptiveProcessSchedulerFCFS;
import logic.finish.NonPreemptiveProcessSchedulerHRRN;
import logic.finish.NonPreemptiveProcessSchedulerSJF;
import logic.finish.ProcessSchedualer;
import model.Input;
import model.ProcessModel;
import uitil.Json;

import java.io.File;
import java.util.ArrayList;

import static uitil.IOHelper.convertFileToText;
import static uitil.IOHelper.writeStringToFile;


class Main {
    public static void main(String[] args) {
        File jsonFile = new File("C:\\dev\\workspace\\Operating-system\\src\\main\\resources\\Json.json".replace("\\", "/"));
        Json jsonParser = Json.getInstance();
        Input input = jsonParser.convertStringToJsonObject(convertFileToText(jsonFile));
        ArrayList<ProcessModel> processModels = new ArrayList<>(input.getArrayList());
        String type = input.getType();
        ProcessSchedualer processSchedualer = null;
        switch (type) {
            case "RR":
                processSchedualer = new PreemptiveProcessSchedulerRR(processModels, input.getUnitTime());
                break;
            case "SRT":
                processSchedualer = new PreemptiveProcessSchedulerSRT(processModels);
                break;
            case "FCFS":
                processSchedualer = new NonPreemptiveProcessSchedulerFCFS(processModels);
                break;
            case "SJF":
                processSchedualer = new NonPreemptiveProcessSchedulerSJF(processModels);
                break;
            case "HRRN":
                processSchedualer = new NonPreemptiveProcessSchedulerHRRN(processModels);
        }

       String result=processSchedualer.run();
        File outPut = new File("C:\\dev\\workspace\\Operating-system\\src\\main\\resources\\OutPut.text".replace("\\", "/"));
        writeStringToFile(outPut,result);
    }
}
