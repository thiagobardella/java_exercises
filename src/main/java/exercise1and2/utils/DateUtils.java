package exercise1and2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public static Date getDateOf(String date) throws ParseException {
        return formatter.parse(date);
    }
}
