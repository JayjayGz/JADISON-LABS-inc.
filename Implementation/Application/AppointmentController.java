package Implementation.Application;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    private final ClinicCatalog clinicCatalog;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog, ClinicCatalog clinicCatalog){
        this.professionalCatalog = professionalCatalog;
        this.clinicCatalog = clinicCatalog;
    }

    public List<TimeSlot> requestAvailability(UUID professionalID, UUID clinicID, LocalDate startDate){
        HealthcareProfessional professional = professionalCatalog.findByID(professionalID);

        return professional.getSchedule().getAvailableSlots(clinicID, startDate);
    }
    


    //-------------------------------------------- IMPORT FROM CSV --------------------------------------------

    // public AppointmentController(List<HealthcareProfessional> healthcareProfessionals, List<Clinic> clinics){
    //     this.healthcareProfessionals = healthcareProfessionals;
    //     this.clinics = clinics;
    // }

    public void importAvailabilityFromCSV(String filePath, String clinicID){

        //first i need to find the clinic 
        UUID clinicUUID = UUID.fromString(clinicID);
        Clinic clinic = null;

        clinic = clinicCatalog.findByID(clinicUUID);
        if (clinic == null){
            System.out.println("Clinic not found");
            return;
        }

        try (BufferedReader b = new BufferedReader(new FileReader(filePath))){
            String line;
            b.readLine(); 

            while((line = b.readLine()) != null){
                String[] data = line.split(",");
                //String firstName = data[0].trim();
                //String lastName = data[1].trim();
                UUID professionalID = UUID.fromString(data[0].trim());
                Specialties specialty = Specialties.valueOf(data[1].trim());
                LocalDate date = LocalDate.parse(data[2].trim());
                LocalTime startTime = LocalTime.parse(data[3].trim());
                LocalTime endTime = LocalTime.parse(data[4].trim());

                HealthcareProfessional professional;
                
                try{
                    professional = professionalCatalog.findByID(professionalID);
                } catch (IllegalArgumentException e){
                    System.out.println("healthcare Professional not found: " + professionalID);
                    continue; //Skipping the line
                }

                professional.addSpecialty(specialty);

                TimeSlot timeSlot = new TimeSlot(LocalDateTime.of(date, startTime), LocalDateTime.of(date, endTime), clinic);
                professional.getSchedule().addTimeSlot(timeSlot);
            }
           
            } 
            catch (IOException e){
                System.out.println("Error reading the file: " + e.getMessage());
        }
    }
    //-------------------------------------------- END OF IMPORT FROM CSV --------------------------------------------

    public void exportToCSV(String filePath, String professionalID){

        try{
            UUID id = UUID.fromString(professionalID);
            HealthcareProfessional hp = professionalCatalog.findByID(id);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("firstName, lastName, professionalID, specialty, schedule");
                writer.newLine();
                String schedule = "";

                for (TimeSlot slot : hp.getSchedule().getTimeSlots()){
                    schedule += slot.getStartDateTime() + " to " + slot.getEndDateTime() + ";";
                }

                writer.write(hp.getFirstName() + "," + hp.getLastName() + "," + hp.getProfessionalID() + "," + hp.getSpecialties() + "," + schedule);
                writer.newLine();
            }

        }catch (IOException e){
            System.out.println("Error exporting CSV: " + e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Healthcare Professional not found: " + professionalID);
        }
    
    }
}
