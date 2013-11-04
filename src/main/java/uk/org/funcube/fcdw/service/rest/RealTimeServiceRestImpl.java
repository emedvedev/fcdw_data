// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.server.shared.RealTimeSummary;


@Controller
@RequestMapping(value = "data/realtime")
public class RealTimeServiceRestImpl implements RealTimeServiceRest {
	
	@Autowired
	RealTimeDao realTimeDao;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestParam(value = "callback") String callBack) {
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("data", new RealTimeSummary());
	    try {
			return objectMapper.writeValueAsString(new JSONPObject(callBack, map));
		} catch (JsonGenerationException e) {
			return callBack + "([error:" + e.getMessage() + "]);";
		} catch (JsonMappingException e) {
			return callBack + "([error:" + e.getMessage() + "]);";
		} catch (IOException e) {
			return callBack + "([error:" + e.getMessage() + "]);";
		}
		
	}
	
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET)
	@ResponseBody
	public List<RealTimeSummary> get(@PathVariable Long satelliteId, @RequestParam(value = "callback") String callbackName) {
		List<RealTimeSummary> summaries = new ArrayList<RealTimeSummary>();
		summaries.add(new RealTimeSummary());
		return summaries;
	}

}
