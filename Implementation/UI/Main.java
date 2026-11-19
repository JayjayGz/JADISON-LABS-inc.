package Implementation.UI;

import Implementation.Application.AppointmentController;
import Implementation.Catalog.AppointmentCatalog;
import Implementation.Catalog.ClinicCatalog;
import Implementation.Catalog.HealthcareProfessionalCatalog;
import Implementation.Domain.*;
import Implementation.Domain.Utils.Coordinates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void runMenu(AppointmentController controller, UUID professionalID, UUID clinicID) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("Menu");
            System.out.println("1. View available Appointments");
            System.out.println("2. Add availability");
            System.out.println("0. Exit");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch(choice){
                    case 1 -> displayAvailability(sc, controller, professionalID, clinicID);
                    case 2 -> addAvailability(sc, controller, professionalID, clinicID);
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
            System.out.println();
        }
    }

    private static void displayAvailability(Scanner sc, AppointmentController controller, UUID professionalID, UUID clinicID) {
        System.out.println("View available Appointments");

        System.out.print("Starting date (yyyy-mm-dd, 'today', or Enter for today): ");
        LocalDate startDate = readDate(sc);

        List<TimeSlot> slots = controller.requestAvailability(professionalID, clinicID, startDate);

        if (slots.isEmpty()) {
            System.out.println("No availability found.");
        } else {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, MMMM d, uuuu", Locale.ENGLISH);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

            for (TimeSlot slot : slots) {
                System.out.println(
                        slot.getStartDateTime().format(dateFormat)
                                + " from "
                                + slot.getStartDateTime().format(timeFormat)
                                + " to "
                                + slot.getEndDateTime().format(timeFormat)
                );
            }
        }
    }

    private static void addAvailability(Scanner sc, AppointmentController controller, UUID professionalID, UUID clinicID) {
        System.out.print("Starting date (yyyy-MM-dd, 'today', or Enter for today): ");
        LocalDate startDate = readDate(sc);

        System.out.print("End date (yyyy-MM-dd, 'today', or Enter for today): ");
        LocalDate endDate = readDate(sc);

        System.out.print("Start time (HH:mm): ");
        LocalTime startTime = readTime(sc);

        System.out.print("End time (HH:mm): ");
        LocalTime endTime = readTime(sc);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        System.out.print("Pattern (DAILY, WEEKLY, BI_WEEKLY, MONTHLY): ");
        Pattern pattern = Pattern.valueOf(
                sc.nextLine().trim().toUpperCase(Locale.ROOT)
        );

        Set<DayOfWeek> weekDays = new HashSet<>();
        Set<Integer> monthDays = new HashSet<>();

        switch (pattern) {
            case WEEKLY, BI_WEEKLY -> {
                System.out.print("Weekdays separated by commas " + "(e.g. MONDAY,WEDNESDAY): ");

                for (String value : sc.nextLine().split(",", -1)) {
                    weekDays.add(DayOfWeek.valueOf(value.trim().toUpperCase(Locale.ROOT)));
                }
            }

            case MONTHLY -> {
                System.out.print("Days of month separated by commas (e.g. 1,15,28): ");

                for (String value : sc.nextLine().split(",", -1)) {
                    int day = Integer.parseInt(value.trim());

                    if (day < 1 || day > 31) {
                        throw new IllegalArgumentException("Days of month must be between 1 and 31");
                    }

                    monthDays.add(day);
                }
            }

            case DAILY -> {}
        }

        Recurrence recurrence = new Recurrence(weekDays, monthDays, pattern);

        controller.createAvailability(
                professionalID,
                clinicID,
                startDate,
                endDate,
                startTime,
                endTime,
                recurrence
        );

        System.out.println("Finished processing availability.");
    }

    private static LocalDate readDate(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();

            if (input.isEmpty() || input.equalsIgnoreCase("today")) {
                return LocalDate.now();
            }

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please try again, for example: 2026-11-18.");
            }
        }
    }

    private static LocalTime readTime(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                return LocalTime.now();
            }

            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid Time. Please try again, for example: 09:30).");
            }
        }
    }

    public static void main(String[] args) {
        // populate with mock data. Replace this later when we add the csv option
        Clinic clinic = new Clinic(
                "Test Clinic",
                new Coordinates(45.5019, -73.5674),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        HealthcareProfessional professional = new HealthcareProfessional("Amélie", "Tremblay");

        LocalDate date = LocalDate.now().plusDays(1);

        professional.getSchedule().addTimeSlot(new TimeSlot(date.atTime(9, 0), date.atTime(9, 30), clinic));

        Set<DayOfWeek> weekDays = new HashSet<>();
        weekDays.add(DayOfWeek.MONDAY);
        weekDays.add(DayOfWeek.TUESDAY);
        weekDays.add(DayOfWeek.WEDNESDAY);

        Set<Integer> monthDays = new HashSet<>();
        monthDays.add(3);
        monthDays.add(11);
        monthDays.add(15);
        monthDays.add(31);

        Recurrence weeklyRecurrence = new Recurrence(weekDays, monthDays, Pattern.WEEKLY);
        Recurrence monthlyRecurrence = new Recurrence(weekDays, monthDays, Pattern.MONTHLY);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(3);
        LocalTime startTime = LocalTime.of(9,30);
        LocalTime endTime = LocalTime.of(10, 0);

        HealthcareProfessionalCatalog professionalCatalog = new HealthcareProfessionalCatalog();
        ClinicCatalog clinicCatalog = new ClinicCatalog();
        AppointmentCatalog appointmentCatalog = new AppointmentCatalog();
        professionalCatalog.add(professional);
        clinicCatalog.add(clinic);

        AppointmentController controller = new AppointmentController(professionalCatalog, clinicCatalog, appointmentCatalog);

        controller.createAvailability(professional.getProfessionalID(), clinic.getClinicID(), startDate, endDate, startTime, endTime, weeklyRecurrence);
        controller.createAvailability(professional.getProfessionalID(), clinic.getClinicID(), startDate, endDate, startTime, endTime, monthlyRecurrence);


        // basic CLI for a specific professional at a specific clinic
        runMenu(controller, professional.getProfessionalID(), clinic.getClinicID());
    }
}
