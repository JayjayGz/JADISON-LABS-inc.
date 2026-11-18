package Implementation.Domain;

import java.time.LocalTime;

public class Appointment {
    
    //attributes
    private String medicalService;
    private AppointmentStatus status;
    private LocalTime arrivalTime;

    // Constructor for exporting csv data
    public Appointment(String medicalService, AppointmentStatus appointmentStatus, LocalTime arrivalTime){
        this.medicalService = medicalService;
        this.status = appointmentStatus;
        this.arrivalTime = arrivalTime;
    }

    // Constructor for making an appointment; starts as requested with no arrival time yet
    public Appointment(String medicalService){
        this.medicalService = medicalService;
        this.status = AppointmentStatus.REQUESTED;
    }

    //getters
    public String getMedicalService(){
        return this.medicalService;
    }

    public AppointmentStatus getAppointmentStatus(){
        return this.status;
    }

    public LocalTime getArrivalTime(){
        return this.arrivalTime;
    }


    //setters
    public void setMedicalService(String medicalService){
        this.medicalService = medicalService;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus){
        this.status = appointmentStatus;
    }

    public void setArrivalTime(LocalTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }


}
