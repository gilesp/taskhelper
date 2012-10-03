package uk.co.vurt.hakken.server.connector;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.db.DatabaseTableTaskDefinition;
import uk.co.vurt.hakken.util.StringUtils;

/**
 * Hideously inefficient jdbc data source.
 * 
 * At the very least, use a DataSource based connector instead.
 * 
 * @author giles.paterson
 *
 */
public class JDBCConnector extends AbstractDataConnector<DatabaseTableTaskDefinition> {

	private static final Logger logger = LoggerFactory.getLogger(JDBCConnector.class);
			
	private final static String CONNECTION_STRING_KEY = "jdbc_url";
	private final static String DRIVER_NAME_KEY = "driver";
	private final static String TABLE_NAME_KEY = "table";
	private final static String TABLE_USER_FIELD_KEY = "username";
	private final static String DB_USERNAME_KEY = "db_user";
	private final static String PASSWORD_KEY = "password";
	
	private final static String INFO_STRING = "Connect to a database using JDBC.\nNeed to specify the url and other parameters.\nTODO: Complete this message.";

	private Properties properties;
	private String schema = null;
	private List<DatabaseTableTaskDefinition> definitions;
	
	public JDBCConnector(){
		properties = new Properties();
	}
	
	private final static String GET_INSTANCES_SQL = "Select * from [" + TABLE_NAME_KEY + "] where upper([" + TABLE_USER_FIELD_KEY + "]) = upper(?)";
	
	@Override
	public List<Instance> getInstances(DatabaseTableTaskDefinition taskDefinition, Map<String, String> properties, String username, Date lastUpdated) {
		logger.debug("Retrieving instances from " + taskDefinition.getName() + " for username " + username + " since " + lastUpdated);

		if(logger.isDebugEnabled()){
			logger.debug("Properties: ");
			for (Map.Entry<String, String> entry : properties.entrySet()) {
			    logger.debug(entry.getKey() + " = " + entry.getValue());
			}

		}
		properties.put(TABLE_NAME_KEY, taskDefinition.getName());
		
		List<Instance> instances = new ArrayList<Instance>();
		Connection conn = getConnection();
		if(conn != null){
			try{
				if(logger.isDebugEnabled()){
					logger.debug("SQL: " + StringUtils.replaceTokens(GET_INSTANCES_SQL, properties));
				}
				PreparedStatement ps = conn.prepareStatement(StringUtils.replaceTokens(GET_INSTANCES_SQL, properties));
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int columns = metaData.getColumnCount();
				logger.debug("Retrieved " + columns + " columns");
				int id = 0;
				while(rs.next()){
					logger.debug("Retrieving instance " + id);
					Map<String, String> dataItems = new HashMap<String, String>();
					for(int i = 1; i <= columns; i++){
						if(logger.isDebugEnabled()){
							logger.debug("Storing Data Item: " + metaData.getColumnName(i) + ":" + rs.getString(i));
						}
						dataItems.put(metaData.getColumnName(i), rs.getString(i));
					}
					//TODO: fix this id;
					instances.add(new Instance(String.valueOf(id), username, new Date(), new Date(), "", dataItems));
					id++;
				}
			}catch(SQLException sqle){
				logger.error("Unable to query database", sqle);
			}
			closeConnection(conn);
		}
		return instances;
	}
	@Override
	public String getInfo() {
		return INFO_STRING;
	}

	private Connection getConnection(){
		if(properties == null){
			throw new IllegalStateException("Init must be called before use");
		}
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", properties.get(DB_USERNAME_KEY));
		connectionProps.put(PASSWORD_KEY, properties.get(PASSWORD_KEY));
		try {
			Class.forName(properties.getProperty(DRIVER_NAME_KEY));
			conn = DriverManager.getConnection(properties.getProperty(CONNECTION_STRING_KEY), connectionProps);
		} catch (ClassNotFoundException cnfe) {
			logger.error("Unable to find database driver class: " + properties.get(DB_USERNAME_KEY), cnfe);
		} catch (SQLException sqle) {
			logger.error("Unable to open connection to database.", sqle);
		}
		
		return conn;
	}
	
	private void closeConnection(Connection conn){
		if(conn != null){
			try{
				conn.close();
				conn = null;
			}catch(SQLException sqle){
				logger.error("Unable to close database connection.", sqle);
			}
		}
	}
	
	/**
	 * Utility method to replace named tokens in a string
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
	
	public void setConnectionString(String connectionString){
		properties.put(CONNECTION_STRING_KEY, connectionString);
	}
	
	public void setDriverName(String driverName){
		properties.put(DRIVER_NAME_KEY, driverName);
	}
	
	public void setDbUser(String dbUser){
		properties.put(DB_USERNAME_KEY, dbUser);
	}
	public void setDbPassword(String dbPassword){
		properties.put(PASSWORD_KEY, dbPassword);
	}
	
	public void setSchema(String schema){
		this.schema = schema;
	}
	
//	public void setTableName(String tableName){
//		replacements.put(TABLE_NAME_KEY, tableName);
//	}
//	public void setUserField(String userField){
//		replacements.put(TABLE_USER_FIELD_KEY, userField);
//	}

	@Override
	public List<DatabaseTableTaskDefinition> getDefinitions() {
		if(definitions == null || definitions.isEmpty()){
			definitions = new ArrayList<DatabaseTableTaskDefinition>();
			
			Connection con = getConnection();
			if(con != null){
				try {
					DatabaseMetaData metadata = con.getMetaData();
					ResultSet rs = metadata.getTables(null, schema, "%", null); //TODO: make this configurable
					while(rs.next()){

						String tableName = rs.getString("TABLE_NAME");

						ResultSet tableRs = metadata.getColumns(null, null, tableName, null);
						List<String> dataItemNames = new ArrayList<String>();
						while(tableRs.next()){
							dataItemNames.add(tableRs.getString("COLUMN_NAME"));
						}
						tableRs.close();
						tableRs = null;
						definitions.add(new DatabaseTableTaskDefinition(tableName, dataItemNames));
					}
					rs.close();
					rs = null;
					closeConnection(con);
				} catch (SQLException sqle) {
					logger.error("Unable to retrieve metadata from database.", sqle);
				}
				
			}
		}
		
		return definitions;
	}
	
	@Override
	public DatabaseTableTaskDefinition getDefinition(String name) {
		DatabaseTableTaskDefinition taskDefinition = null;
		if(name != null){
			Connection conn = getConnection();
			try {
				DatabaseMetaData metadata = conn.getMetaData();
				ResultSet rs = metadata.getColumns(null, null, name, null);
				List<String> dataItemNames = new ArrayList<String>();
				while(rs.next()){
					dataItemNames.add(rs.getString("COLUMN_NAME"));
				}
				taskDefinition = new DatabaseTableTaskDefinition(name, dataItemNames);
				rs.close();
				rs = null;
				closeConnection(conn);
			} catch (SQLException sqle) {
				logger.error("Unable to retrieve table metadata.", sqle);
			}
		}
		return taskDefinition;
	}

	@Override
	public SubmissionStatus save(Submission submission,
			Map<String, String> taskToConnectorMappings,
			TaskDefinition taskDefinition, String dcTaskDefinitionName) {
		SubmissionStatus status = new SubmissionStatus();
		status.setValid(false);
		status.setMessage("Not implemented yet.");
		status.setType(SubmissionStatus.ErrorType.COMMS);
		return status;
	}
}
