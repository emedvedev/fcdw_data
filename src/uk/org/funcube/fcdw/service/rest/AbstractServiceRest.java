package uk.org.funcube.fcdw.service.rest;

import uk.org.funcube.fcdw.service.rest.model.JsonOutput;

public interface AbstractServiceRest {
	
	JsonOutput getLatest(Long satelliteId);

}
