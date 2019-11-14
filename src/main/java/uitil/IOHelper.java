package uitil;

import java.io.*;

public class IOHelper {
    public static void println(Object o) {
        System.out.println(o);
    }

    public static String convertFileToText(File f) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            bufferedReader.lines().forEach(stringBuilder::append);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void writeStringToFile(File file, String string) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            bufferedWriter.write(string+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
