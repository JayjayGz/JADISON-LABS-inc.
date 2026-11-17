package Implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlot {
    private Clinic clinic;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<Appointment> appointments;

    //parameterized constructor
    public TimeSlot (Clinic clinic, LocalDateTime startDateTime, LocalDateTime endDateTime) {

        if (!endDateTime.isAfter(startDateTime)) {
             throw new IllegalArgumentException("End must be after start.");
        }
        
        this.clinic = clinic;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.appointments = new ArrayList<>();
    }

    //getters
    public Clinic getClinic(){
        return this.clinic;
    }

    public LocalDateTime getStartDateTime(){
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime(){
        return this.endDateTime;
    }

    public List<Appointment> getAppointments(){
        return this.appointments;
    }

    public boolean isAvailable() {
    for (Appointment appointment : appointments) {
        if (appointment.getAppointmentStatus() != AppointmentStatus.CANCELLED) {
            return false;
        }
    }
    return true;
}

}