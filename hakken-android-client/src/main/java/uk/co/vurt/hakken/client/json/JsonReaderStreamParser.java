package uk.co.vurt.hakken.client.json;

import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

//import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
//import android.util.JsonReader;
//import android.util.JsonToken;
import uk.co.vurt.hakken.domain.task.TaskDefinition;

/**
 * This class requires functionality only present in API Level >= 11 (Android 3+)
 * Until I can figure out a way to choose it when running on those platforms but 
 * still support previous ones, I'm going to comment it out.
 * 
 *
 */
public class JsonReaderStreamParser implements JsonStreamParser{

	public List<JobDefinition> parseJobDefinitionStream(InputStream in) throws IOException {
//		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
//		try {
//			return readJobsArray(reader);
//		}finally {
//			reader.close();
//		}
		return null;
	}
/*	
	public List readJobsArray(JsonReader reader) throws IOException {
		List jobs = new ArrayList();
		
		reader.beginArray();
		while(reader.hasNext()){
			jobs.add(readJob(reader));
		}
		reader.endArray();
		return jobs;
	}
	
	public JobDefinition readJob(JsonReader reader) throws IOException {
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
		
		reader.beginObject();
		while(reader.hasNext()){
			String itemName = reader.nextName();
			if(itemName.equals("id")){
				id = reader.nextLong();
			} else if(itemName.equals("name")){
				name = reader.nextString();
			} else if(itemName.equals("definition") && reader.peek() != JsonToken.NULL){
				definition = readTaskDefinition(reader);
			} else if(itemName.equals("created")){
				created = new Date(reader.nextLong());
			} else if(itemName.equals("due")){
				due = new Date(reader.nextLong());
			} else if(itemName.equals("status")){
				status = reader.nextString();
			} else if(itemName.equals("group")){
				group = reader.nextString();
			} else if(itemName.equals("notes")){
				notes = reader.nextString();
			} else if(itemName.equals("dataItems") && reader.peek() != JsonToken.NULL){
				dataItems.addAll(readDataItemsArray(reader));
			} else if(itemName.equals("modified")){
				modified = reader.nextBoolean();
			} else {
				reader.skipValue(); //something we weren't expecting, so ignore it
			}
		}
		reader.endObject();
		return new JobDefinition(id, name, definition, created, due, status, group, notes, dataItems, modified);
	}
	
	public List readDataItemsArray(JsonReader reader) throws IOException {
		List dataItems = new ArrayList();
		
		reader.beginArray();
		while(reader.hasNext()){
			dataItems.add(readDataItem(reader));
		}
		reader.endArray();
		return dataItems;
	}
	
	public TaskDefinition readTaskDefinition(JsonReader reader){
		return null;
	}
	
	public DataItem readDataItem(JsonReader reader) throws IOException{
		Long id = null;
		String name = null;
		String type = null;
		String value = null;
		String pageName = null;
		
		reader.beginObject();
		while(reader.hasNext()){
			String itemName = reader.nextName();
			if(itemName.equals("id")){
				id = reader.nextLong();
			} else if(itemName.equals("name")){
				name = reader.nextString();
			} else if(itemName.equals("type")){
				type = reader.nextString();
			} else if(itemName.equals("value")){
				value = reader.nextString();
			} else if(itemName.equals("pageName")){
				pageName = reader.nextString();
			} else {
				reader.skipValue(); //something we weren't expecting, so ignore it
			}
		}
		reader.endObject();
		
		return new DataItem(id, pageName, name, type, value);
	}
	*/

	@Override
	public void parseJobDefinitionStream(InputStream in,
			JobDefinitionHandler callback) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TaskDefinition> parseTaskDefinitionStream(InputStream in)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseTaskDefinitionStream(InputStream in,
			TaskDefinitionHandler callback) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
}
