package Implementation.Domain;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Appointment {
    
    //attributes
    private String medicalService;
    private final UUID appointmentID;
    private AppointmentStatus status;
    private LocalTime arrivalTime;
    private TimeSlot timeSlot;
    private final Patient patient;

    // Constructor for importing csv data
    public Appointment(String medicalService, UUID appointmentID, AppointmentStatus appointmentStatus, LocalTime arrivalTime, TimeSlot timeSlot, Patient patient) {
        this.medicalService = medicalService;
        this.appointmentID = appointmentID;
        this.status = appointmentStatus;
        this.arrivalTime = arrivalTime;
        this.timeSlot = timeSlot;
        this.patient = patient;
    }

    // Constructor for making an appointment; starts as requested with no arrival time yet
    public Appointment(String medicalService, TimeSlot timeSlot, Patient patient) {
        this.medicalService = medicalService;
        this.patient = patient;
        this.appointmentID = UUID.randomUUID();
        this.status = AppointmentStatus.REQUESTED;
        this.timeSlot = Objects.requireNonNull(timeSlot);
    }

    //getters
    public String getMedicalService(){
        return this.medicalService;
    }

    public AppointmentStatus getStatus(){
        return this.status;
    }

    public LocalTime getArrivalTime(){
        return this.arrivalTime;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public UUID getAppointmentID() {
        return appointmentID;
    }

    //setters
    public void setMedicalService(String medicalService) {
        this.medicalService = medicalService;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void confirmAppointment() {
        if (status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot confirm a cancelled appointment");
        } else if (status == AppointmentStatus.CONFIRMED) {
            throw new IllegalStateException("Appointment is already confirmed");
        } else  if (status == AppointmentStatus.REQUESTED) {
            this.status = AppointmentStatus.CONFIRMED;
        }
    }

    public void cancelAppointment() {
        if (status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already cancelled");
        } else {
            this.status = AppointmentStatus.CANCELLED;
        }
    }

    public void rescheduleAppointment(TimeSlot newTimeSlot) {
        if (newTimeSlot == null) {
            throw new IllegalArgumentException("Must provide a valid time slot to reschedule an appointment");
        }
        if (status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reschedule a cancelled appointment");
        }
        if (newTimeSlot == this.timeSlot) {
            throw new IllegalArgumentException("Cannot reschedule an already rescheduled time");
        }
        if (!newTimeSlot.isAvailable()) {
            throw new IllegalStateException("Cannot reschedule an appointment to an unavailable time slot");
        }

        TimeSlot oldTimeSlot = this.timeSlot;
        AppointmentStatus oldStatus = this.status;

        this.timeSlot = newTimeSlot;
        this.status = AppointmentStatus.REQUESTED;

        // Try adding the appointment to the new time slot, revert the changes if it fails
        try {
            newTimeSlot.addAppointment(this);
        } catch (RuntimeException e) {
            this.status = oldStatus;
            this.timeSlot = oldTimeSlot;
            throw e;
        }

        // Remove the appointment from this time slot
        oldTimeSlot.removeAppointment(this);
    }
}
