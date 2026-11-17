package Implementation;

import java.time.LocalTime;
import java.util.Date;

public class Availability {
    
    //attributes
    private LocalTime startTime;
    private LocalTime endTime;
    private Date date;
    private HealthcareProfessional healthcareProfessional;

    //constructor
    public Availability(LocalTime startTime, LocalTime endTime, Date date, HealthcareProfessional healthcareProfessional){
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.healthcareProfessional = healthcareProfessional;
    }

    //getters
    public LocalTime getStartTime(){
        return this.startTime;
    }

    public LocalTime getEndTime(){
        return this.endTime;
    }

    public Date getDate(){
        return this.date;
    }

    public HealthcareProfessional getHealthcareProfessional(){
        return this.healthcareProfessional;
    }

    //setters
    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setHealthcareProfessional(HealthcareProfessional healthcareProfessional){
        this.healthcareProfessional = healthcareProfessional;
    }
}
