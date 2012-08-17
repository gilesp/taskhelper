package uk.co.vurt.hakken.client.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

public class JacksonStreamParser implements JsonStreamParser {

	@Override
	public List<JobDefinition> parseJobDefinitionStream(InputStream in) throws IOException {
		JsonFactory jsonF = new JsonFactory();
		JsonParser jp = jsonF.createJsonParser(in);
		try{
			return readJobsArray(jp);
		}finally{
			jp.close();
		}
	}

	private List<JobDefinition> readJobsArray(JsonParser jp) throws IOException{
		List<JobDefinition> jobs = new ArrayList<JobDefinition>();
		
		JsonToken current;
		
		try{
			current = jp.nextToken();
//			System.out.println("1 Current: " + current.toString());
			if(current != JsonToken.START_ARRAY){
				throw new IOException("Error: root should be array.");
			}
			
			current = jp.nextToken();
			while(current != null && current != JsonToken.END_ARRAY){
//				System.out.println("2 Current: " + current.toString());
				jobs.add(readJob(jp));
				current = jp.nextToken();
			}
		}catch(JsonParseException jpe){
			throw new IOException(jpe.getMessage(), jpe);
		}
		
		return jobs;
	}
	
	private JobDefinition readJob(JsonParser jp) throws IOException, JsonParseException{
		Long id = null;
		String name = null;
		TaskDefinition definition = null;
		Date created = null;
		Date due = null;
		String status = null;
		String group = null;
		String notes = null;
		Set<DataItem> dataItems = new HashSet<DataItem>();
		boolean modified = false;
		
		JsonToken current = jp.getCurrentToken();
//		System.out.println("3 Current: " + current.toString());
		if(current != JsonToken.START_OBJECT){
//			System.out.println("3 Current: " + current.toString());
			throw new IOException("Error: expected start of object, instead found " + current.asString());
		}
		while(jp.nextToken() != JsonToken.END_OBJECT){
//			System.out.println("4 Current: " + jp.getCurrentToken().toString());
			String itemName = jp.getCurrentName();
//			System.out.println("Item Name: '" + itemName + "'");
			//move to value token
			jp.nextToken();
			
			if(itemName.equals("id")){
				id = jp.getValueAsLong();
//				System.out.println("ID:" + id);
			} else if(itemName.equals("name")){
				name = jp.getText();
//				System.out.println("Name:" + name);
			} else if(itemName.equals("definition")){
				definition = readTaskDefinition(jp);
//				System.out.println("Definition:" + definition);
			} else if(itemName.equals("created")){
				created = new Date(Long.parseLong(jp.getText()));
//				System.out.println("Created:" + created);
			} else if(itemName.equals("due")){
				due = new Date(Long.parseLong(jp.getText()));
//				System.out.println("Due:" + due);
			} else if(itemName.equals("status")){
				status = jp.getText();
//				System.out.println("Status:" + status);
			} else if(itemName.equals("group")){
				group = jp.getText();
//				System.out.println("Group:" + group);
			} else if(itemName.equals("notes")){
				notes = jp.getText();
//				System.out.println("Notes:" + notes);
			} else if(itemName.equals("dataItems")){
				dataItems.addAll(readDataItemsArray(jp));
//				System.out.println("DataItems:" + dataItems);
			} else if(itemName.equals("modified")){
				modified = jp.getBooleanValue();
//				System.out.println("Modified:" + modified);
			} else {
				//something we weren't expecting so just skip it.
				jp.skipChildren();
//				System.out.println("Skipping...");
			}
		}
		return new JobDefinition(id, name, definition, created, due, status, group, notes, dataItems, modified);
	}
	
	private List<DataItem> readDataItemsArray(JsonParser jp) throws IOException, JsonParseException {
		List<DataItem> dataItems = new ArrayList<DataItem>();
		JsonToken current = jp.getCurrentToken();
		if(current != JsonToken.START_ARRAY){
			throw new IOException("Error: expected start of array.");
		}
		while(jp.nextToken() != JsonToken.END_ARRAY){
			dataItems.add(readDataItem(jp));
		}
		return dataItems;
	}
	
	private DataItem readDataItem(JsonParser jp) throws IOException, JsonParseException {
		Long id = null;
		String name = null;
		String type = null;
		String value = null;
		String pageName = null;
		
		if(jp.getCurrentToken() != JsonToken.START_OBJECT){
			throw new IOException("Error: expected start of object");
		}
		while(jp.nextToken() != JsonToken.END_OBJECT){
			String itemName = jp.getCurrentName();
			jp.nextToken();
			if(itemName.equals("id")){
				id = jp.getValueAsLong();
			} else if(itemName.equals("name")){
				name = jp.getText();
			} else if(itemName.equals("type")){
				type = jp.getText();
			} else if(itemName.equals("value")){
				value = jp.getText();
			} else if(itemName.equals("pageName")){
				pageName = jp.getText();
			} else {
				jp.skipChildren(); //something we weren't expecting, so ignore it
			}
		}
		
		return new DataItem(id, pageName, name, type, value);
	}
	
	private TaskDefinition readTaskDefinition(JsonParser jp) throws IOException, JsonParseException{
		while(jp.nextToken() != JsonToken.END_OBJECT){
			jp.skipChildren();
		}
		return null;
	}
}
