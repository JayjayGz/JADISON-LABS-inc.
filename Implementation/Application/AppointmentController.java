package Implementation.Application;

import Implementation.Catalog.AppointmentCatalog;
import Implementation.Catalog.ClinicCatalog;
import Implementation.Catalog.HealthcareProfessionalCatalog;

import Implementation.Domain.Appointment;
import Implementation.Domain.Clinic;
import Implementation.Domain.HealthcareProfessional;
import Implementation.Domain.Patient;
import Implementation.Domain.Recurrence;
import Implementation.Domain.Schedule;
import Implementation.Domain.Specialties;
import Implementation.Domain.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.io.FileWriter;

public class AppointmentController {
    private final HealthcareProfessionalCatalog professionalCatalog;
    private final AppointmentCatalog appointmentCatalog;
    private final ClinicCatalog clinicCatalog;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog, ClinicCatalog clinicCatalog, AppointmentCatalog appointmentCatalog) {
        this.professionalCatalog = professionalCatalog;
        this.appointmentCatalog = appointmentCatalog;
        this.clinicCatalog = clinicCatalog;
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
        appointmentCatalog.addAppointment(appointment);

        return appointment;
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

    public void createAvailability(UUID professionalID, UUID clinicID, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Recurrence recurrence) {
        Clinic clinic = clinicCatalog.findByID(clinicID);
        Schedule schedule = professionalCatalog.findByID(professionalID).getSchedule();

        schedule.createAvailability(clinic, startDate, endDate, startTime, endTime, recurrence);
    }
}
