package Implementation.Catalog;

import Implementation.Domain.Appointment;

import java.util.*;

public class AppointmentCatalog {
    private final Map<UUID, Appointment> appointments = new HashMap<>();

    public void addAppointment(Appointment appointment) {
        Objects.requireNonNull(appointment, "Appointment cannot be null");
        UUID id = Objects.requireNonNull(appointment.getAppointmentID());

        if (appointments.putIfAbsent(id, appointment) != null) {
            throw new IllegalArgumentException("Appointment already exists");
        }
    }

    public Appointment findByID(UUID appointmentID) {
        Appointment appointment = appointments.get(appointmentID);

        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }

        return appointment;
    }

    public List<Appointment> getAll() {
        return List.copyOf(appointments.values());
    }
}