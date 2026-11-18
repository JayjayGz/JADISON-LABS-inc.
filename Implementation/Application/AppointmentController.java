package Implementation.Application;

import Implementation.Domain.Appointment;
import Implementation.Domain.HealthcareProfessional;
import Implementation.Domain.HealthcareProfessionalCatalog;
import Implementation.Domain.Patient;
import Implementation.Domain.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AppointmentController {
    private final HealthcareProfessionalCatalog professionalCatalog;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog){
        this.professionalCatalog = professionalCatalog;
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
}
