package Monitoring;

public class Device {
    private String deviceCategory;
    private String deviceName;
    private String factorDatasetFile;
    private int safeRangeLowerBound;
    private int safeRangeUpperBound;

    public Device(String deviceCategory, String deviceName, String factorDatasetFile,
                  int safeRangeLowerBound, int safeRangeUpperBound){
        this.deviceCategory = deviceCategory;
        this.deviceName = deviceName;
        this.factorDatasetFile = factorDatasetFile;
        this.safeRangeLowerBound = safeRangeLowerBound;
        this.safeRangeUpperBound = safeRangeUpperBound;
    }

    public void show(){
        System.out.println("device category: " + deviceCategory);
        System.out.println("device name: " + deviceName);
        System.out.println("factor dataset file: " + factorDatasetFile);
        System.out.println("safe range lower bound: " + safeRangeLowerBound);
        System.out.println("safe range upper bound: " + safeRangeUpperBound);
    }
}
