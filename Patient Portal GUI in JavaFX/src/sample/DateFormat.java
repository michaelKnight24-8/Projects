package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

  // little utility class to make sure that the date is in the proper format
  // needed
public class DateFormat {

    // format the date so that it is in the proper format to be set in the datepicker
    public static LocalDate formatDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    //function to remove the zero from the database when finding appointments and surgeries
    public static String removeZeros(String date) {
        return Integer.toString(Integer.parseInt(date.split("/")[0])) + "/" +
                date.split("/")[1] + "/" + date.split("/")[2];
    }

    // fixes the date by adding zeros when needed if below 10
    public static String fixDate(String date) {
        String [] dates = date.split("/");

        // loop over the date and add a 0 when needed
        for (int i = 0; i < 2; i++) {
            if (dates[i].length() == 1) {
                String temp = dates[i];
                dates[i] = "0" + temp;
            }
        }
        //combine it all together with '/'s when needed
        return dates[0] + "/" + dates[1] + "/" + dates[2];
    }
}
