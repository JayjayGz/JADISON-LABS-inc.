package Implementation.Domain;

import java.time.DayOfWeek;
import java.util.Set;

public class Recurrence {
    private Set<DayOfWeek> weekDays;
    private Set<Integer> monthDays;
    private Pattern recurrencePattern;

    public Recurrence(Set<DayOfWeek> weekDays, Set<Integer> monthDays, Pattern recurrencePattern) {
        this.weekDays = weekDays;
        this.monthDays = monthDays;
        this.recurrencePattern = recurrencePattern;
    }

    public Set<DayOfWeek> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Set<DayOfWeek> weekDays) {
        this.weekDays = weekDays;
    }

    public Set<Integer> getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(Set<Integer> monthDays) {
        this.monthDays = monthDays;
    }

    public Pattern getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(Pattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }
}