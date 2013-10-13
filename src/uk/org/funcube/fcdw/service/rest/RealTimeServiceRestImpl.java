package uk.org.funcube.fcdw.service.rest;

import org.springframework.beans.factory.annotation.Autowired;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.RealTimeMinMaxDao;
import uk.org.funcube.fcdw.service.rest.model.JsonOutput;


public class RealTimeServiceRestImpl implements RealTimeServiceRest {
	
	@Autowired
	RealTimeDao realTimeDao;
	
	@Autowired
	RealTimeMinMaxDao realTimeMinMaxDao;

	@Override
	public JsonOutput getLatest(Long satelliteId) {
		// TODO Auto-generated method stub
		return null;
	}

}
