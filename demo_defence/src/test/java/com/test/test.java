package com.test;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author tsn77
 * @Date 2021/5/25 17:52
 * @Version 1.0
 */
public class test {
    public static void main(String[] args) throws ParseException {
        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date data=sdf.parse("2008-07-10 19:20:00");
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, 30);
        System.out.println(sdf.format(calendar.getTime()));
    }
}
