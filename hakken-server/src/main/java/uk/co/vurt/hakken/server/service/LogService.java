package uk.co.vurt.hakken.server.service;

import java.util.List;

import uk.co.vurt.hakken.server.log.LogEntry;

public interface LogService extends Service<Long, LogEntry> {

	public void log(String username, String message);
	
	public List<LogEntry> getAll();
}
