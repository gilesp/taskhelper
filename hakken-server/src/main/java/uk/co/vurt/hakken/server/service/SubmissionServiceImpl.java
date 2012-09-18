package uk.co.vurt.hakken.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.domain.job.SubmissionStatus;
import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;
import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.persistence.DataItemDAO;
import uk.co.vurt.hakken.server.persistence.SubmissionDAO;
import uk.co.vurt.hakken.server.task.TaskRegistry;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private static final Logger logger = LoggerFactory
			.getLogger(SubmissionServiceImpl.class);

	private TaskRegistry taskRegistry;
	private SubmissionDAO submissionDao;
	private DataItemDAO dataItemDao;
	private MappingService mappingService;
	private DataConnectorService connectorService;
	private LogService logService;

	@Autowired
	public void setSubmissionDao(SubmissionDAO dao) {
		dao.setClazz(Submission.class);
		this.submissionDao = dao;
	}

	@Autowired
	public void setDataItemDao(DataItemDAO dataItemDao) {
		dataItemDao.setClazz(DataItem.class);
		this.dataItemDao = dataItemDao;
	}

	@Autowired
	public void setMappingService(MappingService mappingService) {
		this.mappingService = mappingService;
	}

	@Autowired
	public void setConnectorService(DataConnectorService connectorService) {
		this.connectorService = connectorService;
	}

	@Autowired
	public void setTaskRegistry(TaskRegistry taskRegistry) {
		this.taskRegistry = taskRegistry;
	}

	@Override
	public Submission get(Long id) {
		return submissionDao.get(id);
	}

	@Override
	public void store(Submission submission) {
		for (DataItem dataItem : submission.getDataItems()) {
			if (dataItem.getId() != null) {
				dataItemDao.update(dataItem);
			} else {
				dataItemDao.save(dataItem);
			}
		}
		Submission existing = submissionDao.getByJobId(submission.getJobId());
		if (existing != null) {
			submission.setId(existing.getId());
			logger.debug("Updating submission for job " + submission.getJobId()
					+ ", " + existing.getId());
			submissionDao.update(submission);
			logService.log(submission.getUsername(), "Updated submission "
					+ submission.getId() + " job: " + submission.getJobId());
		} else {
			logger.debug("Saving new submission.");
			submissionDao.save(submission);
			logService.log(submission.getUsername(), "New submission "
					+ submission.getId() + " job: " + submission.getJobId());
		}
	}

	@Override
	public List<Submission> getAll() {
		return submissionDao.getAll();
	}

	@Override
	public void delete(long id) {
		submissionDao.deleteById(id);
	}

	public SubmissionStatus submit(Submission submission) {
		// lookup dataconnector and save instance
		ServiceMapping serviceMapping = mappingService
				.getMappingForTaskDefinition(submission.getTaskDefinitionName());

		DataConnectorTaskDefinitionMapping dcTaskDefMapping = serviceMapping
				.getDataConnectorTaskDefinitionMapping();

		DataConnector connector = connectorService
				.getDataConnector(dcTaskDefMapping.getDataConnectorName());

		SubmissionStatus status = connector.save(submission,
				serviceMapping.getTaskToConnectorMappings(),
				taskRegistry.getTask(submission.getTaskDefinitionName()));
		logService.log(
				submission.getUsername(),
				"Submitted job "
						+ submission.getJobId()
						+ ". Status: "
						+ status
						+ (!status.isValid() ? " Error: " + status.getMessage()
								: ""));
		return status;
	}

	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
