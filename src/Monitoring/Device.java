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
    private PrintWriter writer;
    private String record;

    public Device(String deviceCategory, String deviceName, String factorDatasetFile,
                  int safeRangeLowerBound, int safeRangeUpperBound, PrintWriter writer){
        this.deviceCategory = deviceCategory;
        this.deviceName = deviceName;
        this.factorDatasetFile = factorDatasetFile;
        this.safeRangeLowerBound = safeRangeLowerBound;
        this.safeRangeUpperBound = safeRangeUpperBound;
        this.writer = writer;
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
            System.out.println("[" + periodCount*patientPeiod + "] " + deviceName + " falls");
            writer.println("[" + periodCount*patientPeiod + "] " + deviceName + " falls");
        }
        else{
            if (value < safeRangeLowerBound || value > safeRangeUpperBound)
                System.out.println("[" + periodCount*patientPeiod + "] " + patientName +" is in danger! Cause: " + deviceName + " " + df.format(value));
                writer.println("[" + periodCount*patientPeiod + "] " + patientName +" is in danger! Cause: " + deviceName + " " + df.format(value));
        }

        record += "[" + periodCount*patientPeiod + "] " + df.format(value) + "\n";

    }

    public String getRecord(){
        return record;
    }
}
