package uk.co.vurt.taskhelper.server.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import uk.co.vurt.taskhelper.server.domain.definition.ManualTaskDefinition;
import uk.co.vurt.taskhelper.server.domain.job.DataItem;
import uk.co.vurt.taskhelper.server.domain.job.Job;
import uk.co.vurt.taskhelper.server.domain.user.Person;

/*
 * HIDEOUS HACK ALERT!
 * 
 * DO NOT USE THIS CODE. EVER.
 * 
 */
public class HSCJobJDBCProvider implements HSCJobProvider {

	Person person;
	ManualTaskDefinition hscTaskDefinition;
	
	public HSCJobJDBCProvider(Person person, ManualTaskDefinition hscTaskDefinition){
		this.person = person;
		this.hscTaskDefinition = hscTaskDefinition;
	}
	
	private final static String RETRIEVE_JOBS_SQL = 
			"select a.hsb_appointment_date as due_date, " +
					"a.hsb_allocated_to_quick_label as station, " +
					"a.hsb_created_timestamp as created, " +
					"a.hsb_password as password, " +
					"c.hc_quick_label as name, " +
					"r.hsc_address_label as address, " +
					"r.hsc_notes as notes, " +
					"r.hsc_contact_centre_notes as contect_centre_notes " +
			"from hsc_referrals r, hsc_appointments a, hsc_contacts c " +
			"where r.hsc_closed = 'N' " +
			"and r.HSC_APPOINTMENT >= ? " +
			"and r.HSC_APPOINTMENT <= SYSDATE +1 " +
			"and a.hsb_report_id = r.hsc_report_id " +
			"and r.hsc_primary_contact_id = c.hc_id " +
			"and a.hsb_cancel_reason is null " +
			"and a.hsb_allocated_to_quick_label = ? " +
			"order by due_date asc";
	
	@Override
	public List<Job> getJobsForTaskGroup(String taskGroup, String lastUpdated) {
		List<Job> jobs = new ArrayList<Job>();
		
		Connection conn = null;
		PreparedStatement retrieveJobs = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", "irmis");
	    connectionProps.put("password", "irmis");
	    try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@fshq-mis:1521:ILIX", connectionProps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    if(conn != null){
	    	try {
	    		retrieveJobs = conn.prepareStatement(RETRIEVE_JOBS_SQL);
	    		retrieveJobs.setTimestamp(1, Timestamp.valueOf(lastUpdated.replace("T", " "))); //hack upon hack... Munging the ISO 8601 format into what Timestamp expects.
	    		retrieveJobs.setString(2, taskGroup);
	    		ResultSet results = retrieveJobs.executeQuery();
	    		while(results.next()){
	    			//create job instance
	    			Job job = new Job();
	    			job.setPerson(person);
	    			job.setManualTask(hscTaskDefinition);
	    			job.setDue(timestampToDate(results.getTimestamp("due_date")));
	    			job.setGroupname(results.getString("station"));
	    			job.setCreated(timestampToDate(results.getTimestamp("created")));
	    			job.setNotes(results.getString("address"));
	    			job.setName(hscTaskDefinition.getName());

	    			job.getDataItems().add(createTextDataItem("contact", results.getString("name"), job));
	    			job.getDataItems().add(createTextDataItem("address", results.getString("address"), job));
	    			job.getDataItems().add(createTextDataItem("password", results.getString("password"), job));
	    			job.getDataItems().add(createTextDataItem("notes", results.getString("notes"), job));
	    			
	    			jobs.add(job);
	    		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
		return jobs;
	}
	
	private Date timestampToDate(Timestamp timestamp){
		return new Date(timestamp.getTime() + (timestamp.getNanos() / 1000000));
	}
	
	private DataItem createTextDataItem(String name, String value, Job job){
		System.out.println("CREATING DATAITEM: " + name + " with value " + value);
		DataItem item = new DataItem();
		item.setJob(job);
		item.setPageName("intro");
		item.setType("TEXT");
		item.setName(name);
		item.setValue(value);
		return item;
	}
}
