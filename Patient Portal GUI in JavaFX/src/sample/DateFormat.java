package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat {
    public static LocalDate formatDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    public static String fixDate(String date) {
        String [] dates = date.split("/");

        for (int i = 0; i < 2; i++) {
            if (dates[i].length() == 1) {
                String temp = dates[i];
                dates[i] = "0" + temp;
            }
        }

        return dates[0] + "/" + dates[1] + "/" + dates[2];
    }
}
