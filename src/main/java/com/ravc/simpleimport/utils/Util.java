/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rodrigo
 */
public class Util {

    public static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    public static String normalizer(String text) {
        text = text.replace('Š', 'S');
        text = text.replace('š', 's');
        text = text.replace('Ž', 'Z');
        text = text.replace('ž', 'z');
        text = text.replace('À', 'A');
        text = text.replace('Á', 'A');
        text = text.replace('Â', 'A');
        text = text.replace('Ã', 'A');
        text = text.replace('Ä', 'A');
        text = text.replace('Å', 'A');
        text = text.replace('Æ', 'A');
        text = text.replace('Ç', 'C');
        text = text.replace('È', 'E');
        text = text.replace('É', 'E');
        text = text.replace('Ê', 'E');
        text = text.replace('Ë', 'E');
        text = text.replace('Ì', 'I');
        text = text.replace('Í', 'I');
        text = text.replace('Î', 'I');
        text = text.replace('Ï', 'I');
        text = text.replace('Ñ', 'N');
        text = text.replace('Ò', 'O');
        text = text.replace('Ó', 'O');
        text = text.replace('Ô', 'O');
        text = text.replace('Õ', 'O');
        text = text.replace('Ö', 'O');
        text = text.replace('Ø', 'O');
        text = text.replace('Ù', 'U');
        text = text.replace('Ú', 'U');
        text = text.replace('Û', 'U');
        text = text.replace('Ü', 'U');
        text = text.replace('Ý', 'Y');
        text = text.replace('Þ', 'B');
        text = text.replace('à', 'a');
        text = text.replace('á', 'a');
        text = text.replace('â', 'a');
        text = text.replace('ã', 'a');
        text = text.replace('ä', 'a');
        text = text.replace('å', 'a');
        text = text.replace('æ', 'a');
        text = text.replace('ç', 'c');
        text = text.replace('è', 'e');
        text = text.replace('é', 'e');
        text = text.replace('ê', 'e');
        text = text.replace('ë', 'e');
        text = text.replace('ì', 'i');
        text = text.replace('í', 'i');
        text = text.replace('î', 'i');
        text = text.replace('ï', 'i');
        text = text.replace('ð', 'o');
        text = text.replace('ñ', 'n');
        text = text.replace('ò', 'o');
        text = text.replace('ó', 'o');
        text = text.replace('ô', 'o');
        text = text.replace('õ', 'o');
        text = text.replace('ö', 'o');
        text = text.replace('ø', 'o');
        text = text.replace('ù', 'u');
        text = text.replace('ú', 'u');
        text = text.replace('û', 'u');
        text = text.replace('ý', 'y');
        text = text.replace('ý', 'y');
        text = text.replace('þ', 'b');
        text = text.replace('ÿ', 'y');

        return text;
    }

    public static Date asDate(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date);
        return asDate(zonedDateTime.toLocalDateTime());
    }

    public static Date asDate(String date, String parser) {
        Date dateParsed = null;
        if (date == null) {
            return null;
        }

        try {
            if (parser == null) {
                parser = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(parser);
            dateParsed = sdf.parse(date);
        } catch (ParseException ex) {
            dateParsed = null;
        }
        return dateParsed;
    }

    public static String asDate(Date date) {
        String dateFormated = "";
        if (date != null) {
            dateFormated = new SimpleDateFormat("dd/MM/yyyy").format(date);
        }
        return dateFormated;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String asDate(LocalDateTime localDateTime, String format) {
        String date = "";
        DateTimeFormatter dtFormatter = null;

        if (format == null) {
            dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        } else {
            dtFormatter = DateTimeFormatter.ofPattern(format);
        }

        if (localDateTime != null) {
            date = localDateTime.format(dtFormatter);
        }

        return date;
    }

    public static String asDate(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String asDate(LocalDate localDate, String format) {
        String date = null;
        DateTimeFormatter dtFormatter = null;

        if (StringUtils.isNotBlank(format)) {
            dtFormatter = DateTimeFormatter.ofPattern(format);
        } else {
            dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        if (localDate != null) {
            date = localDate.format(dtFormatter);
        }

        return date;
    }

    public static String asDate(LocalDate localDate, DateTimeFormatter format) {
        String date = "";
        DateTimeFormatter dtFormatter;

        if (format == null) {
            dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } else {
            dtFormatter = format;
        }

        if (localDate != null) {
            date = localDate.format(dtFormatter);
        }

        return date;
    }
}
