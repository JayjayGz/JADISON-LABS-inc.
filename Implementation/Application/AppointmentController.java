package Implementation.Application;

import Implementation.HealthcareProfessionalCatalog;
import Implementation.Domain.HealthcareProfessional;
import Implementation.Domain.TimeSlot;

import java.time.LocalDate;
import java.util.List;
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
    
}
