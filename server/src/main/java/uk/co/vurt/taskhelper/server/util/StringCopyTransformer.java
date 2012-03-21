package uk.co.vurt.taskhelper.server.util;

import flexjson.BasicType;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class StringCopyTransformer extends AbstractTransformer {

	String prefix="";
	
	public StringCopyTransformer(String prefix){
		this.prefix = prefix;
	}
	
	@Override
	public void transform(Object object) {
		boolean setContext = false; 
		TypeContext typeContext = getContext().peekTypeContext(); 
		String propertyName = typeContext != null ? typeContext.getPropertyName() : ""; 
		if(prefix.trim().equals("")){
			prefix = propertyName;
		}

		if (typeContext == null || typeContext.getBasicType() != BasicType.OBJECT) { 
			typeContext = getContext().writeOpenObject(); 
			setContext = true; 
		}
		
//		if (!typeContext.isFirst()) {
//			getContext().writeComma();
//		}
		
		String jsonAsString = (String) object;
		getContext().write(jsonAsString);
		
//		if (setContext) { 
//			getContext().writeCloseObject(); 
//		}
	}

}
