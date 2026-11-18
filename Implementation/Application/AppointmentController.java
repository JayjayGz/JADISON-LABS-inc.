package Implementation.Application;

import Implementation.Domain.Appointment;
import Implementation.Domain.Clinic;
import Implementation.Domain.Specialties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
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
    private final List<Clinic> clinics;

    public AppointmentController(HealthcareProfessionalCatalog professionalCatalog) {
        this.professionalCatalog = professionalCatalog;
        this.clinics = List.of();
    }

    public AppointmentController(List<HealthcareProfessional> healthcareProfessionals, List<Clinic> clinics) {
        this.professionalCatalog = new HealthcareProfessionalCatalog(healthcareProfessionals);
        this.clinics = clinics;
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
                for(HealthcareProfessional hp: professionalCatalog.getProfessionals()){
                    if(hp.getFirstName().equals(firstName) && hp.getLastName().equals(lastName)){
                        healthcareProfessional = hp;
                        break;
                    }
                }

                //if we dont find the healthcare professional, we create a new one
                if(healthcareProfessional == null){
                    healthcareProfessional = new HealthcareProfessional(firstName, lastName);
                    professionalCatalog.addProfessional(healthcareProfessional);
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
