import java.io.*;
import java.util.ArrayList;
import Monitoring.Patient;
import Monitoring.Monitor;

public class Main {
    public static void main(String[] args){
        String outputPath = "sampleOutput";

        PrintWriter fileWriter = null;
        PrintWriter consoleWriter = new PrintWriter(System.out, true);
        try {
            fileWriter = new PrintWriter(outputPath, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String filePath = args[0]; // first parsed argument is input file path
        File file  = new File(filePath);
        if (file.exists() && !file.isDirectory()){
//            consoleWriter.println("[Read Profile] from \"" + filePath + "\"\n");

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
//            consoleWriter.println("[Input File]");
//            for (int i = 0; i < str.length; i++)
//                if (str[i] == null)
//                    break;
//                else {
//                    consoleWriter.println(str[i]);
//                }
//            consoleWriter.println("\n");

            // parse input file
            int monitorPeriod = 0;
            ArrayList<Patient> patientList = new ArrayList<>();
            int patientIdx = 0;
            for (int i = 0; i < str.length; i++){

                String patientName;
                int patientPeriod;
                String deviceCategory;
                String deviceName;
                String factorDatasetFile;
                int safeRangeLowerBound;
                int safeRangeUpperBound;
                Patient patient;

                if (str[i] == null)
                    break;
                else {
                    if (i == 0) {
                        monitorPeriod = Integer.valueOf(str[i].trim());
//                        consoleWriter.println("monitor period: " + monitorPeriod);
                    }
                    else {
                            String[] parameters = str[i].split(" ");
                            if (parameters[0].equals("patient")) {
                                // new patient
                                patientName = parameters[1];
                                patientPeriod = Integer.valueOf(parameters[2].trim());
                                patient = new Patient(patientName, patientPeriod);
                                patientList.add(patient);
                                patientIdx += 1;
                            }
                            else {
                                // attach sensor device
                                deviceCategory = parameters[0];
                                deviceName = parameters[1];
                                factorDatasetFile = parameters[2];
                                safeRangeLowerBound = Integer.valueOf(parameters[3].trim());
                                safeRangeUpperBound = Integer.valueOf(parameters[4].trim());

                                patientList.get(patientIdx-1).attach(
                                        deviceCategory,
                                        deviceName,
                                        factorDatasetFile,
                                        safeRangeLowerBound,
                                        safeRangeUpperBound,
                                        fileWriter,
                                        consoleWriter);
                            }
                        }
                    }
                }

            // show patient profile
//            consoleWriter.println("[SHOW PROFILE]");
//            for(int i = 0; i < patientList.size(); i++){
//                consoleWriter.println("[PATIENT #" + (i+1) + "]");
//                patientList.get(i).show();
//            }
//            consoleWriter.println("\n");

            // start monitoring
//            consoleWriter.println("[START MONITORING]");
            /* there should be one thread for each patient*/
            ArrayList<Monitor> patientThreadList = new ArrayList<>();
            for(int i = 0; i < patientList.size(); i++) {
                Monitor monitor = new Monitor();
                monitor.attach(patientList.get(i), monitorPeriod);
                patientThreadList.add(monitor);
            }

            for(int i = 0; i < patientThreadList.size(); i++){
                patientThreadList.get(i).start();
            }

            for(int i = 0; i < patientThreadList.size(); i++) {
                try {
                    patientThreadList.get(i).join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // print records
            String record;
            for(int j = 0; j < patientList.size(); j++) {
                // connect records from different patients
                record = patientThreadList.get(j).getRecord();
                if (record.endsWith("\n")) {
                    record = record.substring(0, record.length() - 1);
                }
                fileWriter.println(record);
                consoleWriter.println(record);
            }

            fileWriter.close();
            consoleWriter.close();
        }
        else{
            consoleWriter.println("[System Ends] no such file in \"" + filePath + "\"\n");
        }
    }
}
