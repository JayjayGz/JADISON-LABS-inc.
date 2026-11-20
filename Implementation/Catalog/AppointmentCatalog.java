package Implementation.Catalog;

import Implementation.Domain.Appointment;

import java.util.*;

public class AppointmentCatalog {
    private final List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment) {
        Objects.requireNonNull(appointment, "Appointment cannot be null");

        boolean exists = appointments.stream()
                .anyMatch(a -> a.getAppointmentID().equals(appointment.getAppointmentID()));

        if (exists) {
            throw new IllegalArgumentException("Appointment already exists.");
        }

        appointments.add(appointment);
    }

    public Appointment findByID(UUID appointmentID) {
        return appointments.stream()
                .filter(p -> p.getAppointmentID().equals(appointmentID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentID));
    }

    public List<Appointment> getAll() {
        return List.copyOf(appointments);
    }
}