package uk.co.vurt.hakken.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class HashUtils {

//	private static final Logger logger = LoggerFactory
//			.getLogger(HashUtils.class);

	// generated using https://www.grc.com/passwords.htm
	//In the real world, we should be generating this on a per app/user basis and storing that in a database of some sort
	//then if it ever gets compromised, we could issue a new one.
	public final static String SHARED_SECRET = "38A4E84BFDC2E785C289E43A35F4A4EED64380C70D3C3A688183D50C88A760F7";
	private final static String HASH_TYPE = "HmacSHA1";

	/**
	 * Hash a string using Hmac-SHA1
	 * @param value
	 * @return
	 */
	public static String hmacSHA1(String value) {
		String encodedValue = null;
		try {
			Mac mac = Mac.getInstance(HASH_TYPE);
			SecretKeySpec secret = new SecretKeySpec(SHARED_SECRET.getBytes(),
					HASH_TYPE);
			mac.init(secret);
			byte[] digest = mac.doFinal(value.getBytes());
			encodedValue = new String(Base64.encodeBase64(digest));
		} catch (NoSuchAlgorithmException nsae) {
//			logger.error("Unable to use algorithm " + HASH_TYPE, nsae);
		} catch (InvalidKeyException ike) {
//			logger.error("Invalid Key", ike);
		}
		return encodedValue;
	}

	/**
	 * Create a string containing all the entries in the map in key=value[&key=value] form,
	 * sorted alphabetically by the key
	 * 
	 * @param parameterMap
	 * @return
	 */
	public static String createValueString(Map<String, String> parameterMap) {
		StringBuffer paramString = new StringBuffer();
		SortedSet<String> keySet = new TreeSet<String>();
		keySet.addAll(parameterMap.keySet());
		for (String key : keySet) {
			if (paramString.length() > 0) {
				paramString.append("&");
			}
			paramString.append(key);
			paramString.append("=");
			paramString.append(parameterMap.get(key));
		}

		return paramString.toString();
	}

	public static String hash(Map<String, String> parameterMap) {

		return hmacSHA1(createValueString(parameterMap));
	}

	/**
	 * Utility method to be used by server to confirm that values received match the hashedValue sent
	 * 
	 * @param parameterMap
	 * @param hashedValue
	 * @return
	 */
	public static boolean validate(Map<String, String> parameterMap, String hashedValue){
		return hash(parameterMap).equals(hashedValue);
	}
	
	
	/*
	 * Send to the server:
	 * 
	 * username
	 * hmac hash value
	 * data
	 */
}
