package ru.yandex.practicum.gym;

import java.time.LocalTime;

public class TrainingSession {
    private final Group group;
    private final Coach coach;
    private final DayOfWeek dayOfWeek;
    private final LocalTime timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, LocalTime timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public Group getGroup() { return group; }
    public Coach getCoach() { return coach; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getTimeOfDay() { return timeOfDay; }
}