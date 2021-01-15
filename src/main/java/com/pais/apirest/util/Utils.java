package com.pais.apirest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

	/**
	 * Metodo que retorna um hash hexadecimal, criado a partir da hora atual.
	 * 
	 * @return string
	 */
	public static String gerarHash() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return DigestUtils.sha256Hex(sdf.format(new Date()));
	}

	/**
	 * Metodo que incrementa 5 minutos na data passada como parametro.
	 * 
	 * @param data
	 * @return date
	 */
	public static Date getDataIncrementada5Min(Date data) {
		// define o tempo de expiracao do token em 5 minutos a partir da data atual
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);

		GregorianCalendar gc = new GregorianCalendar(tz);
		gc.setTime(data);
		gc.add(Calendar.MINUTE, 5);
		return gc.getTime();
	}

}
