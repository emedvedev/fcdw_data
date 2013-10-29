// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.server.shared.RealTime;


@Controller
@RequestMapping(value = "data/realtime")
public class RealTimeServiceRestImpl implements RealTimeServiceRest {
	
	@Autowired
	RealTimeDao realTimeDao;
	
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET)
	@ResponseBody
	public RealTime get(@PathVariable Long satelliteId) {
		return null;
	}

}
