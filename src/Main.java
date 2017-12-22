import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        String filePath = args[0]; // first parsed argument is input file path
        File file  = new File(filePath);
        if (file.exists() && !file.isDirectory()){
            System.out.println("[Read Profile] from \"" + filePath + "\"\n");

            // read input file
            int LINE_LIMIT = 1000;
            String[] str = new String[LINE_LIMIT];
            try {
                FileReader reader = new FileReader(filePath);
                int character;
                int idx = 0;
                while ((character = reader.read()) != -1) {
                    if ((char)character != '\n')
                        if (str[idx] == null)
                            str[idx] = "" + (char) character;
                        else
                            str[idx] += (char)character;
                    else
                        idx += 1;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // show input file
            System.out.println("[Input File]");
            for (int i = 0; i < str.length; i++)
                if (str[i] == null)
                    break;
                else {
                    System.out.println(str[i]);
                }
            System.out.println("\n");

            // parse input file
            int monitorPeriod = 0;

            for (int i = 0; i < str.length; i++){
                if (str[i] == null)
                    break;
                else {
                    if (i == 0)
                        monitorPeriod = Integer.valueOf(str[i].trim());
                    else
                        if (i%2 == 1) {
                            // new patient
                            String[] patient = str[i].split(" ");
                            String patientName = patient[1];
                            int patientPeriod = Integer.valueOf(patient[2].trim());
                            System.out.println(patientName);
                            System.out.println(patientPeriod);
                        }
                        else {
                            // sensor configuration
                            String[] parameters = str[i].split(" ");
                            String deviceCategory;
                            String deviceName;
                            String factorDatasetFile;
                            int safeRangeLowerBound;
                            int safeRangeUpperBound;
                        }
                }
            }
            System.out.println(monitorPeriod);

        }
        else{
            System.out.println("[System Ends] no such file in \"" + filePath + "\"\n");
        }
    }
}
