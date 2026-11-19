package Implementation.Application;

import Implementation.Catalog.ClinicCatalog;
import Implementation.Catalog.HealthcareProfessionalCatalog;
import Implementation.Domain.Clinic;
import Implementation.Domain.HealthcareProfessional;
import Implementation.Domain.Specialties;
import Implementation.Domain.TimeSlot;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class AvailabilityFileManager {
    private final HealthcareProfessionalCatalog professionalCatalog;
    private final ClinicCatalog clinicCatalog;

    public AvailabilityFileManager(HealthcareProfessionalCatalog professionalCatalog, ClinicCatalog clinicCatalog) {
        this.professionalCatalog = professionalCatalog;
        this.clinicCatalog = clinicCatalog;
    }

    public void importAvailability(String filePath){

        try (BufferedReader b = new BufferedReader(new FileReader(filePath))){
            String line;
            b.readLine();

            int lineNumber = 1;

            while((line = b.readLine()) != null){
                lineNumber++;

                if (line.isBlank()){
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    if (data.length != 7) {
                        throw new IllegalArgumentException("Expected 7 columns.");
                    }

                    String firstName = data[0].trim();
                    String lastName = data[1].trim();
                    Specialties specialty = Specialties.valueOf(data[2].trim());
                    LocalDate date = LocalDate.parse(data[3].trim());
                    LocalTime startTime = LocalTime.parse(data[4].trim());
                    LocalTime endTime = LocalTime.parse(data[5].trim());

                    if (firstName.isBlank() || lastName.isBlank()) {
                        throw new IllegalArgumentException("Name cannot be empty.");
                    }

                    UUID clinicID = UUID.fromString(data[6].trim());
                    Clinic clinic = clinicCatalog.findByID(clinicID);

                    if (clinic == null) {
                        throw new IllegalArgumentException(
                                "Clinic not found: " + clinicID
                        );
                    }

                    // Create the time slot
                    TimeSlot timeSlot = new TimeSlot(
                            date.atTime(startTime),
                            date.atTime(endTime),
                            clinic
                    );

                    // Look up if professional already exists
                    HealthcareProfessional professional = professionalCatalog.getAll()
                            .stream()
                            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                            .findFirst()
                            .orElse(null);

                    // If professional is not there, add it to the catalog
                    if (professional == null) {
                        professional = new HealthcareProfessional(firstName, lastName, specialty);
                        professional.getSchedule().addTimeSlot(timeSlot);
                        professionalCatalog.add(professional);
                    } else {
                        professional.getSchedule().addTimeSlot(timeSlot);
                    }
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    System.out.println("Skipped line " + lineNumber + ": " + e.getMessage());
                }
            }

        }
        catch (IOException e){
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }


    public void exportAvailability(String filePath, String clinicID) {
        try{
            UUID id = UUID.fromString(clinicID);
            Clinic clinic = clinicCatalog.findByID(id);

            if (clinic == null) {
                throw new IllegalArgumentException("Clinic not found: " + id);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("firstName,lastName,specialty,date,start,end,clinicID");
                writer.newLine();

                for (HealthcareProfessional hp : professionalCatalog.getAll()) {
                    for (TimeSlot slot : hp.getSchedule().getTimeSlots()) {

                        if (!slot.getClinicID().equals(id)) {
                            continue;
                        }

                        writer.write(
                                hp.getFirstName() + ","
                                        + hp.getLastName() + ","
                                        + hp.getSpecialty().name() + ","
                                        + slot.getStartDateTime().toLocalDate() + ","
                                        + slot.getStartDateTime().toLocalTime() + ","
                                        + slot.getEndDateTime().toLocalTime() + ","
                                        + slot.getClinicID()
                        );
                        writer.newLine();
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error exporting CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot export: " + e.getMessage());
        }
    }
}
