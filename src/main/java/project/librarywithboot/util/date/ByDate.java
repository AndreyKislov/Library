package project.librarywithboot.util.date;

import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;

@Component
public class ByDate {
    public int getAge(Date birthDay) {
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthDay);
        if (today.before(cal)) {
            throw new IllegalArgumentException("The birthDay is before Now. It's unbelievable!");
        }
        int yearNow = today.get(Calendar.YEAR);
        int monthNow = today.get(Calendar.MONTH);
        int dayOfMonthNow = today.get(Calendar.DAY_OF_MONTH);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public int getDay(Date appointment){
        Date today = new Date();
        if (today.before(appointment)) {
            throw new IllegalArgumentException("The appointment date is before Now. It's unbelievable!");
        }
        long millis = today.getTime() - appointment.getTime();
        return (int) (millis/86400000);
    }
}


