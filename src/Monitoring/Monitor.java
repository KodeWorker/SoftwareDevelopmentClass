package Monitoring;

import java.util.ArrayList;

public class Monitor extends Thread {
    private Patient patient;
    private int monitorPeriod;
    private String record;

    // override the run method in thread
    public void attach(Patient patient, int monitorPeriod){
        this.patient = patient;
        this.monitorPeriod = monitorPeriod;
    }

    public void run(){
        record = "patient " + patient.getPatientName() + "\n";
        String patientName = patient.getPatientName();
        int patientPeriod = patient.getPatientPeroid();
        ArrayList<Device> attachment = patient.getAttachment();
        for (int i = 0 ; i <= monitorPeriod; i+=patientPeriod){

            // wait for a patient period
            try {
                this.sleep(patientPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // read from each attach device on the patient
            for (int j = 0; j < attachment.size(); j++){
                attachment.get(j).read((i/patientPeriod), patientPeriod, patientName);
            }
        }

        for (int j = 0; j < attachment.size(); j++)
            record += attachment.get(j).getRecord();
    }

    public String getRecord(){
        return record;
    }
}
