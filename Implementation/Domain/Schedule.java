package Implementation.Domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

    public void createAvailability(Clinic clinic, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Recurrence recurrence) {
        // Stores this to compare which week we are in case of Bi Weekly
        LocalDate firstWeekStart = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // Iterate through days and check if the selected days of the week/month match with the current day
        startDate.datesUntil(endDate.plusDays(1)).forEach(date -> {
            boolean match = switch (recurrence.getRecurrencePattern()) {
                case DAILY -> true;
                case WEEKLY -> recurrence.getWeekDays().contains(date.getDayOfWeek());
                case MONTHLY -> recurrence.getMonthDays().contains(date.getDayOfMonth());
                case BI_WEEKLY -> recurrence.getWeekDays().contains(date.getDayOfWeek()) && ChronoUnit.WEEKS.between(firstWeekStart, date) % 2 == 0;
            };

            if (match) {
                addAvailability(clinic, date, startTime, endTime);
            }
        });
    }

    // Skip already available days (notify the user of days skipped); only used in createAvailability
    private void addAvailability(Clinic clinic, LocalDate day, LocalTime startTime, LocalTime endTime) {
        try {
            addTimeSlot(new TimeSlot(LocalDateTime.of(day, startTime), LocalDateTime.of(day, endTime), clinic));
        } catch (IllegalArgumentException e) {
            System.out.println("Skipped " + day + " because " + e.getMessage());
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
