package uk.co.vurt.hakken.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.job.JobDefinition;
import uk.co.vurt.hakken.domain.task.TaskDefinition;
import uk.co.vurt.hakken.server.connector.DataConnector;
import uk.co.vurt.hakken.server.connector.Instance;
import uk.co.vurt.hakken.server.mapping.DataConnectorTaskDefinitionMapping;
import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.persistence.JobDAO;
import uk.co.vurt.hakken.server.task.TaskRegistry;

@Service
public class JobServiceImpl implements JobService{

	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	
	JobDAO dao;
	
	@Autowired
	TaskRegistry taskRegistry;
	@Autowired
	MappingService mappingService;
	@Autowired
	DataConnectorService connectorService;

	public JobDefinition getByName(String name){
		return dao.getByName(name);
	}

	@Autowired
	public void setDao(JobDAO dao) {
		dao.setClazz(JobDefinition.class);
		this.dao = dao;
	}

	@Override
	public List<JobDefinition> getForUserSince(String username, Date lastUpdated) {
		List<JobDefinition> jobs = new ArrayList<JobDefinition>();
		
		//for each task definition, lookup instance provider/data connector and invoke.
		List<TaskDefinition> taskDefinitions = taskRegistry.getAllTasks();
		for(TaskDefinition definition: taskDefinitions){
			List<Instance> instances = new ArrayList<Instance>();
			ServiceMapping mapping = mappingService.getMappingForTaskDefinition(definition.getName());
			if(mapping != null){
				DataConnectorTaskDefinitionMapping dcMapping = mapping.getDataConnectorTaskDefinitionMapping();
				DataConnector connector = connectorService.getDataConnector(dcMapping.getDataConnectorName());
				
				logger.debug("Retrieved connector");
				instances.addAll(connector.getInstances(connector.getDefinition(dcMapping.getTaskDefinitionName()), dcMapping.getProperties(), username, lastUpdated));
				
				logger.debug("Total instances: " + instances.size());

				for(Instance instance: instances){
					logger.debug("Instance: " + instance);
					
					logger.debug("Task Definition ID: " + definition.getId());
					
					JobDefinition job = new JobDefinition(instance.getId(),
							instance.getName(),
							definition.getId(),
							instance.getCreated(),
							instance.getDue(),
							"AWAITING",
							instance.getNotes());
					
					Set<DataItem> dataItems = new HashSet<DataItem>();
					Set<String> connectorDataItemNames = mapping.getConnectorToTaskMappings().keySet();
					for(String connectorDataItemName: connectorDataItemNames){
						logger.debug(connectorDataItemName + ": " + instance.getDataItems().containsKey(connectorDataItemName));
						if(instance.getDataItems().containsKey(connectorDataItemName)){
							DataItem dataItem = new DataItem();
							logger.debug("value: " + instance.getDataItems().get(connectorDataItemName));
							dataItem.setValue(instance.getDataItems().get(connectorDataItemName));
							String taskDiName = mapping.getConnectorToTaskMappings().get(connectorDataItemName);
							String[] parts = taskDiName.split("@@");
							String pageName = parts[0];
							String diName = parts[1];
							dataItem.setPageName(pageName);
							dataItem.setName(diName);
							if(definition.getPage(pageName) != null && definition.getPage(pageName).getPageItem(diName) != null){
								dataItem.setType(definition.getPage(pageName).getPageItem(diName).getType());
							}
							dataItems.add(dataItem);
						}
					}
					job.setDataItems(dataItems);
					jobs.add(job);
				}
			}
		}
		
		return jobs;
	}

	@Override
	public JobDefinition get(Long id) {
		return dao.get(id);
	}
}
