package uk.org.funcube.fcdw.server.processor;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.dao.RealTimeMinMaxDao;


public class RealTimeChannelProcessorImpl implements FitterMessageProcessor {
	
	private static Logger LOG = Logger.getLogger(RealTimeChannelProcessorImpl.class.getName());
	
	@Autowired
	RealTimeDao realTimeDao;
	
	@Autowired
	RealTimeMinMaxDao realTimeMinMaxDao;

	@Override
	public void process(long satelliteId) {
	}

}
