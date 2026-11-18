package Implementation.Application;

import Implementation.Domain.HealthcareProfessional;
import Implementation.Domain.HealthcareProfessionalCatalog;
import Implementation.Domain.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import Implementation.Domain.*;
import java.util.List;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class AppointmentController {
    private final HealthcareProfessionalCatalog professionalCatalog;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog){
        this.professionalCatalog = professionalCatalog;
    }

    public List<TimeSlot> requestAvailability(UUID professionalID, UUID clinicID, LocalDate startDate){
        HealthcareProfessional professional = professionalCatalog.findByID(professionalID);

        return professional.getSchedule().getAvailableSlots(clinicID, startDate);
    }
    


    //-------------------------------------------- IMPORT FROM CSV --------------------------------------------
    private List<HealthcareProfessional> healthcareProfessionals;
    private List<Clinic> clinics;

    public AppointmentController(List<HealthcareProfessional> healthcareProfessionals, List<Clinic> clinics){
        this.healthcareProfessionals = healthcareProfessionals;
        this.clinics = clinics;
    }

    public void importAvailability(String filePath, String clinicID){

        //first i need to find the clinic associated with the import
        UUID clinicUUID = UUID.fromString(clinicID);
        Clinic clinic = null;

        for(Clinic c: clinics){
            if(c.getClinicID().equals(clinicUUID)){
                clinic = c;
                break;
            }
        }
        if (clinic == null){
            System.out.println("Clinic not found");
            return;
        }

        try (BufferedReader b = new BufferedReader(new FileReader(filePath))){
            String line;
            b.readLine(); 

            while((line = b.readLine()) != null){
                String[] data = line.split(",");
                String firstName = data[0].trim();
                String lastName = data[1].trim();
                Specialties specialty = Specialties.valueOf(data[2].trim());
                LocalDate date = LocalDate.parse(data[3].trim());
                LocalTime startTime = LocalTime.parse(data[4].trim());
                LocalTime endTime = LocalTime.parse(data[5].trim());

                HealthcareProfessional healthcareProfessional = null;
                for(HealthcareProfessional hp: healthcareProfessionals){
                    if(hp.getFirstName().equals(firstName) && hp.getLastName().equals(lastName)){
                        healthcareProfessional = hp;
                        break;
                    }
                }

                //if we dont find the healthcare professional, we create a new one
                if(healthcareProfessional == null){
                    healthcareProfessional = new HealthcareProfessional(firstName, lastName);
                    healthcareProfessionals.add(healthcareProfessional);
                }
                healthcareProfessional.addSpecialty(specialty);

                TimeSlot timeSlot = new TimeSlot(LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime), clinic);
                healthcareProfessional.getSchedule().addTimeSlot(timeSlot);
            }
           
            } 
            catch (IOException e){
                System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
