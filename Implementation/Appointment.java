package Implementation;

import java.util.Date;
import java.time.LocalTime;
public class Appointment {
    
    //attributes
    private String medicalService;
    private String location;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String clinic;
    private AppointmentStatus appointmentStatus;
    private LocalTime arrivalTime;
    private HealthcareProfessional healthcareProfessional;

    public Appointment(String medicalService, String location, Date date, LocalTime startTime, LocalTime endTime, String clinic, AppointmentStatus appointmentStatus, LocalTime arrivalTime, HealthcareProfessional healthcareProfessional){
        this.medicalService = medicalService;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clinic = clinic;
        this.appointmentStatus = appointmentStatus;
        this.healthcareProfessional = healthcareProfessional;
    }

    //getters
    public String getMedicalService(){
        return this.medicalService;
    }

    public String getLocation(){
        return this.location;
    }

    public Date getDate(){
        return this.date;
    }

    public LocalTime getStartTime(){
        return this.startTime;
    }

    public LocalTime getEndTime(){
        return this.endTime;
    }

    public String getClinic(){
        return this.clinic;
    }

    public AppointmentStatus getAppointmentStatus(){
        return this.appointmentStatus;
    }

    public LocalTime getArrivalTime(){
        return this.arrivalTime;
    }

    public HealthcareProfessional getHealthcareProfessional(){
        return this.healthcareProfessional;
    }

    //setters
    public void setMedicalService(String medicalService){
        this.medicalService = medicalService;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }

    public void setClinic(String clinic){
        this.clinic = clinic;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus){
        this.appointmentStatus = appointmentStatus;
    }

    public void setArrivalTime(LocalTime arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setHealthcareProfessional(HealthcareProfessional healthcareProfessional){
        this.healthcareProfessional = healthcareProfessional;
    }


}
