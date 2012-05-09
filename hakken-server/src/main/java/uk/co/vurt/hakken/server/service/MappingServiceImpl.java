package uk.co.vurt.hakken.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.vurt.hakken.server.mapping.ServiceMapping;
import uk.co.vurt.hakken.server.persistence.AbstractDAO;
import uk.co.vurt.hakken.server.persistence.ServiceMappingDAO;

@Service
public class MappingServiceImpl implements MappingService {

	private static final Logger logger = LoggerFactory.getLogger(MappingServiceImpl.class);
	
	
	ServiceMappingDAO dao;
	
	@Override
	public ServiceMapping get(Long id) {
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
			dao.update(mapping);
		} else {
			dao.save(mapping);
		}
	}

}
