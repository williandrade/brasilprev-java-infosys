package com.example.heroku.util;

import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class HelperUtil {

	public static String toMD5(String message) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] bytes = message.getBytes("UTF-8");
			byte[] digested = messageDigest.digest(bytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; ++i) {
				sb.append(Integer.toHexString((digested[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static LocalDateTime getNowDateTime() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static Date getNowDate() {
		LocalDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return HelperUtil.toDate(now);
	}

	public static Date toDate(LocalDateTime dateTime) {
		Date out = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
		return out;
	}

	public static LocalDateTime toLocalDateTime(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
