package uk.co.vurt.hakken.server.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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

import uk.co.vurt.hakken.util.StringUtils;

/**
 * Hideously inefficient jdbc data source.
 * 
 * At the very least, use a DataSource based connector instead.
 * 
 * @author giles.paterson
 *
 */
public class JDBCConnector extends AbstractDataConnector {

	private static final Logger logger = LoggerFactory.getLogger(JDBCConnector.class);
			
	private final static String CONNECTION_STRING_KEY = "jdbc_url";
	private final static String DRIVER_NAME_KEY = "driver";
	private final static String TABLE_NAME_KEY = "table";
	private final static String TABLE_USER_FIELD_KEY = "username";
	private final static String DB_USERNAME_KEY = "db_user";
	private final static String PASSWORD_KEY = "password";
//	private final static List<String> PROPERTIES = Arrays.asList(CONNECTION_STRING_KEY, DRIVER_NAME_KEY, DB_USERNAME_KEY, PASSWORD_KEY, TABLE_NAME_KEY, TABLE_USER_FIELD_KEY);
	
	private final static String INFO_STRING = "Connect to a database using JDBC.\nNeed to specify the url and other parameters.\nTODO: Complete this message.";

	private Properties properties;
	private Map<String, String> replacements;
	private List<String> dataItemNames = null;
	public JDBCConnector(){
		properties = new Properties();
		replacements = new HashMap<String, String>();
	}
	
	private final static String GET_INSTANCES_SQL = "Select * from [" + TABLE_NAME_KEY + "] where upper([" + TABLE_USER_FIELD_KEY + "]) = upper(?)";
	private final static String GET_ALL_SQL = "select * from [" + TABLE_NAME_KEY + "]";
	
	/*
	 * Oh god, my eyes!
	 */
	public List<String> getDataItems(){
		if(dataItemNames == null){
			Connection conn = getConnection();
			if(conn != null){
				dataItemNames = new ArrayList<String>();
				try{
					Statement statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(StringUtils.replaceTokens(GET_ALL_SQL, replacements));
					ResultSetMetaData metaData = rs.getMetaData();
					int columns = metaData.getColumnCount();
					for(int i = 1; i <= columns; i++){
						dataItemNames.add(metaData.getColumnName(i));
					}
					rs.close();
					statement.close();
				}catch(SQLException sqle){
					sqle.printStackTrace();
				}
				closeConnection(conn);
			}
		}
		return dataItemNames;
	}
	
	@Override
	public List<Instance> getInstances(String username, Date lastUpdated) {
		logger.debug("Retrieving instances from " + replacements.get(TABLE_NAME_KEY) + " for username " + username + " since " + lastUpdated);
		List<Instance> instances = new ArrayList<Instance>();
		Connection conn = getConnection();
		if(conn != null){
			try{
				logger.debug("SQL: " + StringUtils.replaceTokens(GET_INSTANCES_SQL, replacements));
				PreparedStatement ps = conn.prepareStatement(StringUtils.replaceTokens(GET_INSTANCES_SQL, replacements));
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int columns = metaData.getColumnCount();
				logger.debug("Retrieved " + columns + " columns");
				int id = 0;
				while(rs.next()){
					logger.debug("Retrieving instance " + id);
					Map<String, String> dataItems = new HashMap<String, String>();
//					for(String dataItemName: dataItemNames){
//						logger.debug("Retrieveing column: " + dataItemName);
//						dataItems.put(dataItemName, rs.getString(rs.findColumn(dataItemName)));
//					}
					for(int i = 1; i <= columns; i++){
						logger.debug("Storing Data Item: " + metaData.getColumnName(i) + ":" + rs.getString(i));
						dataItems.put(metaData.getColumnName(i), rs.getString(i));
					}
					instances.add(new Instance(id, username, new Date(), new Date(), "", dataItems));
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
	public List<DataConnectorTaskDefinition> getDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataConnectorTaskDefinition getDefinition(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	private void closeConnection(Connection conn){
		if(conn != null){
			try{
				conn.close();
				conn = null;
			}catch(SQLException sqle){
				//TODO: Handle exception on close
				sqle.printStackTrace();
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
	public void setTableName(String tableName){
		replacements.put(TABLE_NAME_KEY, tableName);
	}
	public void setUserField(String userField){
		replacements.put(TABLE_USER_FIELD_KEY, userField);
	}
}
