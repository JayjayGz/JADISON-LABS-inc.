package Implementation.Domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class TimeSlot {
    
    //attributes
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private Set<Appointment> appointments;
    private final Clinic clinic;

    // Constructor for exporting csv data
    public TimeSlot(LocalDateTime startDateTime, LocalDateTime endDateTime, Clinic clinic) {
        this.startDateTime = Objects.requireNonNull(startDateTime);
        this.endDateTime = Objects.requireNonNull(endDateTime);
        if (!endDateTime.isAfter(startDateTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.clinic = Objects.requireNonNull(clinic);
        this.appointments = new HashSet<>();
    }

    public void addAppointment(Appointment appointment) {
        Objects.requireNonNull(appointment, "Appointment cannot be null");

        if (appointment.getTimeSlot() != this) {
            throw new IllegalArgumentException("Appointment belongs to another time slot");
        }

        if (appointments.contains(appointment)) {
            throw new IllegalArgumentException("Appointment already added");
        }

        if (appointment.getStatus() != AppointmentStatus.CANCELLED && !isAvailable()) {
            throw new IllegalStateException("Time slot is already booked");
        }

        appointments.add(appointment);
    }

    // Used when Rescheduling, not when cancelling
    protected void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
    }

    // Allowed multiple CANCELLED appointments, but only one active (REQUESTED or CONFIRMED)
    public boolean isAvailable() {
        return this.appointments
                .stream()
                .noneMatch(a -> a.getStatus() != AppointmentStatus.CANCELLED);
    }

    public UUID getClinicID() {
        return clinic.getClinicID();
    }

    //getters
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Set<Appointment> getAppointments() {
        return Set.copyOf(appointments);
    }
}
