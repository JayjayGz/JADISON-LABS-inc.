package Implementation.Domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TimeSlot {
    
    //attributes
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Set<Appointment> appointments;

    // Constructor for exporting csv data
    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Appointment> appointments) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.appointments = appointments;
    }

    // Constructor for adding a new available time slot
    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.appointments = new HashSet<>();
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    //getters
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    //setters
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
