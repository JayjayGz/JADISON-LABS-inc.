package Implementation.Domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Schedule {
    private final UUID professionalID;
    private final List<TimeSlot> timeSlots;

    // Constructor for exporting csv data
    public Schedule(UUID professionalID, List<TimeSlot> timeSlots) {
        this(professionalID);

        for (TimeSlot slot : timeSlots) {
            addTimeSlot(slot);
        }
    }

    // Constructor for new Schedule
    public Schedule(UUID professionalID) {
        this.professionalID = professionalID;
        this.timeSlots = new ArrayList<>();
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        // Verify that an existing timeslot does not overlap with the new one
        boolean overlaps = timeSlots
                .stream()
                .anyMatch(existing ->
                        timeSlot.getStartDateTime().isBefore(existing.getEndDateTime())
                        && existing.getStartDateTime().isBefore(timeSlot.getEndDateTime())
        );

        if (!overlaps) {
            timeSlots.add(timeSlot);
        } else {
            throw new IllegalArgumentException("TimeSlot already exists");
        }
    }

    public void removeTimeSlot(TimeSlot timeSlot) {
        timeSlots.remove(timeSlot);
    }

    // Filter time slot chronologically, per clinic, and from the provided starting date
    public List<TimeSlot> getAvailableSlots(UUID clinicID, LocalDate startDate) {
        return getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getStartDateTime().isBefore(timeSlot.getEndDateTime()))
                .filter(timeSlot -> timeSlot.getClinicID().equals(clinicID))
                .filter(TimeSlot::isAvailable)
                .sorted(Comparator.comparing(TimeSlot::getStartDateTime))
                .toList();
    }

    public List<TimeSlot> getTimeSlots() {
        return List.copyOf(timeSlots);
    }

    public UUID getProfessionalID() {
        return professionalID;
    }
}
