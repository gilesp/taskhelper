package uk.co.vurt.hakken.jackson;

import java.text.SimpleDateFormat;

public class ObjectMapper extends org.codehaus.jackson.map.ObjectMapper {

	public ObjectMapper() {
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));
	}
}
