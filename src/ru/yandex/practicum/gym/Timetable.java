package ru.yandex.practicum.gym;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<LocalTime, List<TrainingSession>>> weekTrainingSessionMap = new HashMap<>();
    private final Map<Coach, Integer> coachSessions = new HashMap<>();

    public Timetable() {
        for (DayOfWeek day : DayOfWeek.values()) {
            weekTrainingSessionMap.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            throw new NullPointerException("Training session cannot be null");
        }

        DayOfWeek day = trainingSession.getDayOfWeek();
        LocalTime time = trainingSession.getTimeOfDay();
        Coach coach = trainingSession.getCoach();

        TreeMap<LocalTime, List<TrainingSession>> dailySessions = weekTrainingSessionMap.get(day);
        dailySessions.computeIfAbsent(time, k -> new ArrayList<>()).add(trainingSession);

        coachSessions.merge(coach, 1, Integer::sum);
    }

    public TreeMap<LocalTime, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return weekTrainingSessionMap.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, LocalTime timeOfDay) {
        List<TrainingSession> sessions = weekTrainingSessionMap.get(dayOfWeek).get(timeOfDay);
        return sessions != null ? new ArrayList<>(sessions) : Collections.emptyList();
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        return coachSessions.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}