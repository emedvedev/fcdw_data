// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.server.shared.SatellitePosition;
import uk.org.funcube.fcdw.server.util.UTCClock;
import uk.org.funcube.fcdw.service.PredictorService;

@Controller
@RequestMapping(value = "data/predictor")
public class PredictorServiceRestImpl implements PredictorServiceRest {
	
	@Autowired
	UTCClock clock;
	
	@Autowired
	PredictorService predictorService;
	
	@RequestMapping(value = "/{catnum}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody	
	public SatellitePosition get(@PathVariable Long catnum) {
		
		return predictorService.get(catnum, clock.currentDate());
	}

	public SatellitePosition get(long l, Date now) {
		// TODO Auto-generated method stub
		return null;
	}

}
