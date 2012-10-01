package uk.co.vurt.hakken.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class URLUtils {

	public static final String ENCODING = "UTF-8";
	
	private URLUtils() {
		
	}
	
	public static final String encode(String urlComponent) {
		try {
			return URLEncoder.encode(urlComponent, ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
	}
}
