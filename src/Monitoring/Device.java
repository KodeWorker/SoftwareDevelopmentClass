package Monitoring;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Device {
    private String deviceCategory;
    private String deviceName;
    private String factorDatasetFile;
    private int safeRangeLowerBound;
    private int safeRangeUpperBound;
    private PrintWriter fileWriter;
    private PrintWriter consoleWriter;
    private String record;

    public Device(String deviceCategory, String deviceName, String factorDatasetFile,
                  int safeRangeLowerBound, int safeRangeUpperBound, PrintWriter fileWriter, PrintWriter consoleWriter){
        this.deviceCategory = deviceCategory;
        this.deviceName = deviceName;
        this.factorDatasetFile = factorDatasetFile;
        this.safeRangeLowerBound = safeRangeLowerBound;
        this.safeRangeUpperBound = safeRangeUpperBound;
        this.fileWriter = fileWriter;
        this.consoleWriter = consoleWriter;
        record = deviceCategory + " " + deviceName + "\n";
    }

    public void show(){
        System.out.println("device category: " + deviceCategory);
        System.out.println("device name: " + deviceName);
        System.out.println("factor dataset file: " + factorDatasetFile);
        System.out.println("safe range lower bound: " + safeRangeLowerBound);
        System.out.println("safe range upper bound: " + safeRangeUpperBound);
    }

    public void read(int periodCount, int patientPeiod, String patientName){
        // read sensor data from dataset file
        int lineNumber = periodCount;
        int value = 0;
        Boolean endOfFile = false;
        DecimalFormat df = new DecimalFormat("0.0");

        String line;
        try {
            line = Files.readAllLines(Paths.get(factorDatasetFile)).get(lineNumber);
            value = Integer.valueOf(line.trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IndexOutOfBoundsException e) {
            //e.printStackTrace();
            endOfFile = true;
            value = -1;
        }

        if (endOfFile || (value == -1)) {
            consoleWriter.println("[" + periodCount*patientPeiod + "] " + deviceName + " falls");
            fileWriter.println("[" + periodCount*patientPeiod + "] " + deviceName + " falls");
        }
        else{
            if (value < safeRangeLowerBound || value > safeRangeUpperBound) {
                consoleWriter.println("[" + periodCount * patientPeiod + "] " + patientName + " is in danger! Cause: " + deviceName + " " + df.format(value));
                fileWriter.println("[" + periodCount * patientPeiod + "] " + patientName + " is in danger! Cause: " + deviceName + " " + df.format(value));
            }
        }

        record += "[" + periodCount*patientPeiod + "] " + df.format(value) + "\n";

    }

    public String getRecord(){
        return record;
    }
}
