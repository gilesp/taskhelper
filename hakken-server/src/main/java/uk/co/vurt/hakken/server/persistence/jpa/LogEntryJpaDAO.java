package uk.co.vurt.hakken.server.persistence.jpa;

import org.springframework.stereotype.Repository;

import uk.co.vurt.hakken.server.log.LogEntry;

@Repository
public class LogEntryJpaDAO extends AbstractJpaDAO<Long, LogEntry> implements
		uk.co.vurt.hakken.server.persistence.LogEntryDAO {

}
