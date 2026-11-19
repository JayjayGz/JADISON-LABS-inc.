package Implementation.Catalog;

import Implementation.Domain.Patient;

import java.util.*;

public class PatientCatalog {
    private final Map<UUID, Patient> patients = new HashMap<>();

    public void add(Patient patient) {
        Objects.requireNonNull(patient, "Patient cannot be null");
        UUID id = Objects.requireNonNull(patient.getPatientID());

        if (patients.putIfAbsent(id, patient) != null) {
            throw new IllegalArgumentException("Patient already exists");
        }
    }

    public Patient findByID(UUID patientID) {
        Patient patient = patients.get(patientID);

        if (patient == null) {
            throw new IllegalArgumentException("Patient not found");
        }

        return patient;
    }

    public List<Patient> getAll() {
        return List.copyOf(patients.values());
    }

    public void remove(UUID patientID) {
        if (patients.remove(patientID) == null) {
            throw new IllegalArgumentException("Patient not found");
        }
    }
}