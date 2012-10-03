package uk.co.vurt.hakken.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.server.log.LogEntry;
import uk.co.vurt.hakken.server.persistence.LogEntryDAO;

@Service
public class LogServiceImpl implements LogService {

	LogEntryDAO logDao;
	
	@Override
	public LogEntry get(Long id) {
		return logDao.get(id);
	}

	@Override
	public List<LogEntry> getAll(){
		return logDao.getAll();
	}
	
	@Override
	public void log(String username, String message) {
		logDao.save(new LogEntry(username, message));
	}

	@Autowired
	public void setLogDao(LogEntryDAO logDao) {
		logDao.setClazz(LogEntry.class);
		this.logDao = logDao;
	}

	
}
