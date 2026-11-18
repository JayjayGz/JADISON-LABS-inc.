package Implementation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Schedule {

    private final List<TimeSlot> timeSlots;

    public Schedule(){
        this.timeSlots = new ArrayList<>();
    }

    public void addTimeSlot(TimeSlot timeSlot){
        this.timeSlots.add(timeSlot);
    }

    public List<TimeSlot> getAvailableSlots(UUID clinicID, LocalDate startDate){
        List<TimeSlot> availableSlots = new ArrayList<>();

        for(TimeSlot slot: timeSlots){

            if(slot.getClinic().getClinicID().equals(clinicID) 
                && !slot.getStartDateTime().toLocalDate().isBefore(startDate) 
                && slot.isAvailable()){
                availableSlots.add(slot);
            }

        }

        return availableSlots;
    }

    
}
