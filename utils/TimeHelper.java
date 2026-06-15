package utils;

public class TimeHelper {
    private static final String[] DAYS = {"Senin","Selasa","Rabu","Kamis",
        "Jumat","Sabtu","Minggu"
    };

    public static String format(int hour) {
        int dayIndex = hour / 24;
        int clockHour = hour % 24;
        return DAYS[dayIndex] + " " + String.format("%02d:00", clockHour);
    }
}