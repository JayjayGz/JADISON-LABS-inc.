package Implementation.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Schedule {
    private UUID professionalID;
    private List<TimeSlot> timeSlots;

    // Constructor for exporting csv data
    public Schedule(UUID professionalID, List<TimeSlot> timeSlots) {
        this.professionalID = professionalID;
        this.timeSlots = timeSlots;
    }

    // Constructor for new Schedule
    public Schedule(UUID professionalID) {
        this.professionalID = professionalID;
        this.timeSlots = new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public UUID getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(UUID professionalID) {
        this.professionalID = professionalID;
    }
}
