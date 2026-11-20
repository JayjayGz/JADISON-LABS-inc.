package Implementation.Catalog;

import Implementation.Domain.Patient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatientCatalog {
    private final List<Patient> patients = new ArrayList<>();

    public void add(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }

        boolean exists = patients.stream()
                .anyMatch(p -> p.getPatientID()
                        .equals(patient.getPatientID()));

        if (exists) {
            throw new IllegalArgumentException("Patient already exists.");
        }

        patients.add(patient);
    }

    public Patient findByID(UUID patientID) {
        return patients.stream()
                .filter(p -> p.getPatientID().equals(patientID))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientID));
    }

    public void remove(UUID patientID) {
        Patient patient = findByID(patientID);
        patients.remove(patient);
    }

    public List<Patient> getAll() {
        return List.copyOf(patients);
    }
}
