package uitil;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import model.Input;
import model.InputJsonAdapter;
import model.OutPut;
import model.OutPutJsonAdapter;

import java.io.IOException;


public class Json {
    private Moshi moshi;
    private static Json json;

    private Json() {
        moshi = new Moshi.Builder()
                .add(new KotlinJsonAdapterFactory())
                .build();
    }

    public static synchronized Json getInstance() {
        if (json == null) {
            json = new Json();
        }
        return json;
    }

    public Input convertStringToJsonObject(String rawText) {
        InputJsonAdapter processModelJsonAdapter=new InputJsonAdapter(moshi);
        Input input=null;
        try {
             input = processModelJsonAdapter.fromJson(rawText);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  input;
    }

    public String convertArrayToJson(OutPut outPut) {
        OutPutJsonAdapter outPutJsonAdapter=new OutPutJsonAdapter(moshi);
        return outPutJsonAdapter.toJson(outPut);
    }
}
