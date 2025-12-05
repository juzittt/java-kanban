package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.gym.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimetableTest {

    private static Group childGroup, adultGroup;
    private static Coach coach1, couch2;
    private Timetable timetable;

    @BeforeAll
    static void beforeAll() {
        childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
        coach1 = new Coach("Данилов", "Глеб", "Александрович");
        couch2 = new Coach("Завьялова", "Екатерина", "Александровна");
    }

    @BeforeEach
    void setUp() {
        timetable = new Timetable();
    }

    @Test
    void shouldReturnSingleDaySession() {
        TrainingSession single = new TrainingSession(
                childGroup, coach1, DayOfWeek.MONDAY, LocalTime.of(11, 30));

        timetable.addNewTrainingSession(single);
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void shouldReturnMultipleDaySessions() {
        TrainingSession thursdayAdult = new TrainingSession(adultGroup, coach1,
                DayOfWeek.THURSDAY, LocalTime.of(22, 30));
        TrainingSession thursdayChild = new TrainingSession(childGroup, coach1,
                DayOfWeek.THURSDAY, LocalTime.of(18, 20));

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(thursdayChild);

        TrainingSession mondayChild = new TrainingSession(childGroup, coach1,
                DayOfWeek.MONDAY, LocalTime.of(13, 10));
        TrainingSession saturdayChild = new TrainingSession(childGroup, coach1,
                DayOfWeek.SATURDAY, LocalTime.of(10, 0));

        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(saturdayChild);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());

        assertEquals(LocalTime.of(18, 20),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet().toArray()[0]);
        assertEquals(LocalTime.of(22, 30),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).keySet().toArray()[1]);
    }

    @Test
    void shouldReturnSingleTimeSession() {
        TrainingSession mondayChild = new TrainingSession(childGroup, coach1,
                DayOfWeek.MONDAY, LocalTime.of(18, 20));

        timetable.addNewTrainingSession(mondayChild);

        assertEquals(1, timetable
                .getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, LocalTime.of(18, 20)).size());
        assertEquals(0, timetable
                .getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, LocalTime.of(14, 0)).size());
    }

    @Test
    void shouldReturnTwoTimeSessions() {
        TrainingSession thursdayAdult = new TrainingSession(adultGroup, coach1,
                DayOfWeek.THURSDAY, LocalTime.of(16, 25));
        TrainingSession thursdayChild = new TrainingSession(childGroup, coach1,
                DayOfWeek.THURSDAY, LocalTime.of(16, 25));

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(thursdayChild);

        assertEquals(2, timetable
                .getTrainingSessionsForDayAndTime(DayOfWeek.THURSDAY, LocalTime.of(16, 25)).size());
    }

    @Test
    void shouldReturnRightCouchCounterByOneSession() {
        TrainingSession thursdayAdult = new TrainingSession(adultGroup, coach1,
                DayOfWeek.THURSDAY, LocalTime.of(15, 30));
        timetable.addNewTrainingSession(thursdayAdult);

        assertEquals(1, timetable.getCountByCoaches().getFirst().getValue());
    }

    @Test
    void shouldReturnRightCouchCounterByTwoSessions() {
        TrainingSession thursdayAdult = new TrainingSession(adultGroup, couch2,
                DayOfWeek.THURSDAY, LocalTime.of(15, 30));
        TrainingSession thursdayChild = new TrainingSession(childGroup, couch2,
                DayOfWeek.THURSDAY, LocalTime.of(8, 30));

        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(thursdayAdult);

        assertEquals(2, timetable.getCountByCoaches().getFirst().getValue());
    }

    @Test
    void shouldReturnMultipleCouchSessionsInExpectedOrder() {
        TrainingSession mondayAdult = new TrainingSession(adultGroup, coach1,
                DayOfWeek.MONDAY, LocalTime.of(15, 30));

        timetable.addNewTrainingSession(mondayAdult);

        TrainingSession thursdayAdult = new TrainingSession(adultGroup, couch2,
                DayOfWeek.THURSDAY, LocalTime.of(15, 30));
        TrainingSession thursdayChild = new TrainingSession(childGroup, couch2,
                DayOfWeek.THURSDAY, LocalTime.of(8, 30));

        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(thursdayAdult);

        assertEquals(couch2, timetable.getCountByCoaches().getFirst().getKey());
        assertEquals(coach1, timetable.getCountByCoaches().getLast().getKey());
    }

    @Test
    void shouldThrowNullPointerIfNullPassed() {
        assertThrows(NullPointerException.class, () -> timetable.addNewTrainingSession(null));
    }
}
