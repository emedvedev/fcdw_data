// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.HighResolutionDao;
import uk.org.funcube.fcdw.domain.HighResolutionEntity;

@Controller
@RequestMapping(value = "data/highres")
public class HighResolutionServiceRestImpl implements HighResolutionServiceRest {


	
	@Autowired
	HighResolutionDao highResolutionDao;
	
	// get all data for one orbit for a given satellite
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<List<String>> getAllHighResForSatellite(
			@PathVariable(value = "satelliteId") Long satelliteId) {
		
		Long maxId = highResolutionDao.findMaxId(satelliteId);
		
		// as we process set of High Resolution data for a satellite all at once. We won't
		// get interleaved with other satellite WOD
		Long firstItem = maxId - 60;
		
		List<HighResolutionEntity> oneHourHighRes = highResolutionDao.getLastHour(satelliteId, firstItem);
		List<List<String>> channelList = new ArrayList<List<String>>();
		
		return channelList;
	}

}
