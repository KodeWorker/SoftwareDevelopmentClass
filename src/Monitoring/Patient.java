package Monitoring;
import java.util.ArrayList;

public class Patient {
    private String patientName;
    private int patientPeroid;
    private ArrayList<Device> attachment = new ArrayList<>();

    public Patient(String patientName, int patientPeriod){
        this.patientName = patientName;
        this.patientPeroid = patientPeriod;
    }

    public void attach(String deviceCategory, String deviceName, String factorDatasetFile,
                       int safeRangeLowerBound, int safeRangeUpperBound){
        Device device = new Device(deviceCategory, deviceName, factorDatasetFile, safeRangeLowerBound, safeRangeUpperBound);
        attachment.add(device);
    }

    public void show(){
        System.out.println("patient name: " + patientName);
        System.out.println("patient period: " + patientPeroid);
        for(int i = 0; i < attachment.size(); i++){
            attachment.get(i).show();
        }
    }

}