package uk.co.vurt.hakken.server.service;

import uk.co.vurt.hakken.server.mapping.ServiceMapping;

public interface MappingService extends Service<Long, ServiceMapping> {

	public void save(ServiceMapping mapping);
}
