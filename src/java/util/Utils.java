/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author pekmil
 */
public class Utils {
    
    public static final SimpleDateFormat simpleTimeFormat;
    public static final SimpleDateFormat simpleDateFormat;
    public static final SimpleDateFormat simpleDateTimeFormat;
    
    static{
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        simpleDateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }
    
    public static Duration dateToDuration(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        StringBuilder sb = new StringBuilder("PT");
        sb.append(c.get(Calendar.HOUR_OF_DAY)).append("H").append(c.get(Calendar.MINUTE))
                .append("M").append(c.get(Calendar.SECOND)).append("S");
        return Duration.parse(sb);
    }
    
    public static Date truncateDate(Date orig){
        if(orig == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(orig);
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }
    
    public static String formatDateTime(Date date){
        return simpleDateTimeFormat.format(date);
    }
    
    public static String formatTime(Date date){
        return simpleTimeFormat.format(date);
    }
    
}
