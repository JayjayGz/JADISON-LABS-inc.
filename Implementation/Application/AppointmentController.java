package Implementation.Application;

import Implementation.Catalog.AppointmentCatalog;
import Implementation.Catalog.ClinicCatalog;
import Implementation.Domain.*;
import Implementation.Catalog.HealthcareProfessionalCatalog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

import Implementation.Domain.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.FileWriter;

public class AppointmentController {
    private final HealthcareProfessionalCatalog professionalCatalog;
    private final AppointmentCatalog appointmentCatalog;
    private final ClinicCatalog clinicCatalog;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog, ClinicCatalog clinicCatalog, AppointmentCatalog appointmentCatalog) {
        this.professionalCatalog = professionalCatalog;
        this.clinicCatalog = clinicCatalog;
        this.appointmentCatalog = appointmentCatalog;
    }

    public List<TimeSlot> requestAvailability(UUID professionalID, UUID clinicID, LocalDate startDate){
        HealthcareProfessional professional = professionalCatalog.findByID(professionalID);

        return professional.getSchedule().getAvailableSlots(clinicID, startDate);
    }

    public Appointment bookAppointment(Patient patient, UUID professionalID, UUID clinicID, LocalDateTime startDateTime, String medicalService){
        Objects.requireNonNull(patient, "Patient cannot be null");
        Objects.requireNonNull(professionalID, "Professional ID cannot be null");
        Objects.requireNonNull(clinicID, "Clinic ID cannot be null");
        Objects.requireNonNull(startDateTime, "Start date and time cannot be null");

        if(medicalService == null || medicalService.isBlank()){
            throw new IllegalArgumentException("Medical service cannot be blank");
        }

        if(!startDateTime.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("Appointment must start in the future");
        }

        HealthcareProfessional professional = professionalCatalog.findByID(professionalID);
        TimeSlot slot = professional.getSchedule().findTimeSlot(clinicID, startDateTime);

        if(!slot.isAvailable()){
            throw new IllegalStateException("Time slot is already booked");
        }

        Appointment appointment = new Appointment(medicalService, slot, patient);
        slot.addAppointment(appointment);
        patient.addAppointment(appointment);

        return appointment;
    }



    public void createAvailability(UUID professionalID, UUID clinicID, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Recurrence recurrence) {
        Clinic clinic = clinicCatalog.findByID(clinicID);
        Schedule schedule = professionalCatalog.findByID(professionalID).getSchedule();

        schedule.createAvailability(clinic, startDate, endDate, startTime, endTime, recurrence);
    }
}
