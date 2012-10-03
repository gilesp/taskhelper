package uk.co.vurt.hakken.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * Utility method to replace named tokens in a string
	 *
	 * It looks for named tokens surrounded with square brackets, and replaces 
	 * them with the appropriate value from the provided map.
	 * 
	 * 
	 * @param text
	 * @param replacements
	 * @return
	 */
	public static String replaceTokens(String text,
			Map<String, String> replacements) {
		Pattern pattern = Pattern.compile("\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String replacement = replacements.get(matcher.group(1));
			if (replacement != null) {
				matcher.appendReplacement(buffer, "");
				buffer.append(replacement);
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
