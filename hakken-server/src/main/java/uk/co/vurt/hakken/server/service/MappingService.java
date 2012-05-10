package uk.co.vurt.hakken.server.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import uk.co.vurt.hakken.server.mapping.ServiceMapping;

public interface MappingService extends Service<Long, ServiceMapping> {

	@Transactional
	public void save(ServiceMapping mapping);
	
	@Transactional(readOnly = true)
	public List<ServiceMapping> getAll();
}
