// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.io.IOException;
import java.util.HashMap;
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
import uk.org.funcube.fcdw.server.shared.AntsSummary;
import uk.org.funcube.fcdw.server.shared.AsibSummary;
import uk.org.funcube.fcdw.server.shared.EpsSummary;
import uk.org.funcube.fcdw.server.shared.PaSummary;
import uk.org.funcube.fcdw.server.shared.RealTimeSummary;
import uk.org.funcube.fcdw.server.shared.RfSummary;
import uk.org.funcube.fcdw.server.shared.SoftwareSummary;


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
	    map.put("data", 
    		new RealTimeSummary(
    		    new EpsSummary("0.0", "0.0", "0.0", "0.0"),
    		    new AsibSummary("0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0"),
    		    new RfSummary("0.0", "0.0", "0.0", "0.0"),
    		    new PaSummary("0.0", "0.0", "0.0", "0.0"),
    		    new AntsSummary("0.0", "0.0", "0", "1", "0", "1"),
    		    new SoftwareSummary("1234", "1", "1", "1", "1", "1", "1", "1", "1", "1")
    		    
    		    ));
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
	public String get(@PathVariable Long satelliteId, @RequestParam(value = "callback") String callback) {
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("data", new EpsSummary("0.0", "0.0", "0.0", "0.0"));
	    try {
			return objectMapper.writeValueAsString(new JSONPObject(callback, map));
		} catch (JsonGenerationException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		} catch (JsonMappingException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		} catch (IOException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		}
	}

}

/*
 * EPS:
Total Photo Current     0 mA     184     212
Battery Voltage     8226 mV     8286     8296
Total System Current     144 mA     190     208
Battery Temp     25 °C     25     25
ASIB:
Solar Panel Temp X+
Solar Panel Temp X-
Solar Panel Temp Y+
Solar Panel Temp Y-
3.3 Bus Voltage
3.3 Bus Current
5.0 Bus voltage
RF:
Temperature
Receive Current
Transmit Current 3.3V bus
Transmit Current 5.0V bus
PA:
Forward Power
Reverse Power
Device Temperature
Bus Current
AntS:
Antenna Temp 0
Antenna Temp 1
Antenna Deployment 0
Antenna Deployment 1
Antenna Deployment 2
Antenna Deployment 3
Software:
Sequence Number
Data Valid ASIB
Data Valid EPS
Data Valid PA
Data Valid RF
Data Valid MSE
Data Valid ANTS Bus-B
Data Valid ANTS Bus-A
In Eclipse Mode
In Safe Mode */
