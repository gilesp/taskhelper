package uk.co.vurt.hakken.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.Submission;
import uk.co.vurt.hakken.server.persistence.DataItemDAO;
import uk.co.vurt.hakken.server.persistence.SubmissionDAO;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	private static final Logger logger = LoggerFactory.getLogger(SubmissionServiceImpl.class);
	

	SubmissionDAO submissionDao;
	DataItemDAO dataItemDao;
	
	@Autowired
	public void setSubmissionDao(SubmissionDAO dao) {
		dao.setClazz(Submission.class);
		this.submissionDao = dao;
	}
	
	@Autowired
	public void setDataItemDao(DataItemDAO dataItemDao){
		dataItemDao.setClazz(DataItem.class);
		this.dataItemDao = dataItemDao;
	}
	
	@Override
	public Submission get(Long id) {
		return submissionDao.get(id);
	}

	@Override
	public void store(Submission submission) {
		for(DataItem dataItem: submission.getDataItems()){
			if(dataItem.getId() != null){
				dataItemDao.update(dataItem);
			} else {
				dataItemDao.save(dataItem);
			}
		}
		if(submissionDao.getByJobId(submission.getJobId()) != null){
			submissionDao.update(submission);
		} else {
			submissionDao.save(submission);
		}
	}

}
