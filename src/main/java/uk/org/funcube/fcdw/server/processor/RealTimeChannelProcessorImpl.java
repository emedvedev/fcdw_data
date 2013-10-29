// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
