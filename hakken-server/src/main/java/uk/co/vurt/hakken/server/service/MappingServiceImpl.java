package uk.co.vurt.hakken.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.persistence.ServiceMappingDAO;

@Service
public class MappingServiceImpl implements MappingService {

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
	
	Map<String, Long> definitionNameCache = new HashMap<String, Long>();
	
	ServiceMappingDAO dao;
	
	@Override
	public ServiceMapping get(Long id) {
		logger.debug("Getting mapping with id: " + id);
		return dao.get(id);
	}

	@Autowired
	public void setDao(ServiceMappingDAO dao) {
		logger.info("setting DAO clazz");
		dao.setClazz(ServiceMapping.class);
		logger.info("Setting DAO");
		this.dao = dao;
	}

	@Override
	public void save(ServiceMapping mapping) {
		
		if(get(mapping.getId()) != null){
			logger.info("Updating mapping.");
			dao.update(mapping);
		} else {
			logger.info("Saving new mapping.");
			dao.save(mapping);
		}
	}

	@Override
	public List<ServiceMapping> getAll() {
		return dao.getAll();
	}

	@Override
	public ServiceMapping getMappingForTaskDefinition(String taskDefinitionName) {
		logger.debug("Looking up mapping for " + taskDefinitionName);
		if(!definitionNameCache.containsKey(taskDefinitionName)){
			for(ServiceMapping mapping: getAll()){
				logger.debug("Checking mapping: " + mapping.getTaskDefinitionName());
				if(mapping.getTaskDefinitionName().equals(taskDefinitionName)){
					logger.debug("Adding mapping to cache: " + taskDefinitionName + " " + mapping.getId());
					definitionNameCache.put(taskDefinitionName, mapping.getId());
					break; //ugh
				}
			}
		}else {
			logger.debug("Mapping is in cache");
		}

		if(definitionNameCache.containsKey(taskDefinitionName)){
			return get(definitionNameCache.get(taskDefinitionName));
		} else {
			return null;
		}
	}

}
