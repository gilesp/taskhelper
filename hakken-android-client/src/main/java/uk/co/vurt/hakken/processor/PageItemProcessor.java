package uk.co.vurt.hakken.processor;

import java.util.Map;

import uk.co.vurt.hakken.domain.task.pageitem.PageItem;

public class PageItemProcessor {

	public static boolean getBooleanAttribute(PageItem item, String attributeName){
		boolean attribute = false;
		String stringValue = getStringAttribute(item, attributeName);
		if(stringValue != null){
			attribute = Boolean.parseBoolean(stringValue);
		}
		return attribute;
	}
	
	public static String getStringAttribute(PageItem item, String attributeName){
		Map<String, String> attributes = item.getAttributes();
		
		String value = null;
		if(attributes != null){
			if(attributes.containsKey(attributeName)){
				value = attributes.get(attributeName);
			}
		}
		return value;
	}
}
