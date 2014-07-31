// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.io.IOException;
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
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.server.shared.FC2AntsSummary;
import uk.org.funcube.fcdw.server.shared.FC2EpsSummary;
import uk.org.funcube.fcdw.server.shared.FC2RealTimeSummary;
import uk.org.funcube.fcdw.server.shared.FC2SoftwareSummary;
import uk.org.funcube.fcdw.server.shared.PaSummary;
import uk.org.funcube.fcdw.server.shared.RealTimeFC2;
import uk.org.funcube.fcdw.server.shared.RfSummary;


@Controller
@RequestMapping(value = "data/fc2realtime")
public class FC2RealTimeServiceRestImpl extends AbstractServiceRestImpl implements RealTimeServiceRest {
	
	public FC2RealTimeServiceRestImpl() {
	}
	
	@Autowired
	RealTimeDao realTimeDao;
	
	//@Transactional(readOnly = true)
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String get(@PathVariable Long satelliteId, @RequestParam(value = "callback") String callback) {
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = new HashMap<String, Object>();
		
		Long maxSequenceNumber = realTimeDao.findMaxSequenceNumber(satelliteId);
		Long maxFrameType = realTimeDao.findMaxFrameType(satelliteId, maxSequenceNumber);
		List<RealTimeEntity> entities 
			= realTimeDao.findBySatelliteIdAndSequenceNumberAndFrameType(satelliteId, maxSequenceNumber, maxFrameType);
		
		if (entities.isEmpty()) {
			return callback + "([error:" + "No data found" + "]);";
		}
		
		RealTimeEntity realTimeEntity = entities.get(0);
		
		RealTimeFC2 realTimeFC2 = new RealTimeFC2(realTimeEntity);

	    map.put("data", 
    		new FC2RealTimeSummary(
    		    new FC2EpsSummary(
    		    		realTimeFC2.getBattery0CurrentString(), 
    		    		realTimeFC2.getBattery0VoltsString(),
    					realTimeFC2.getBattery0TemperatureString(), 
    					realTimeFC2.getBattery1CurrentString(),
    					realTimeFC2.getBattery1VoltsString(), 
    					realTimeFC2.getBattery1TemperatureString(),
    					realTimeFC2.getBattery2CurrentString(), 
    					realTimeFC2.getBattery2VoltsString(),
    					realTimeFC2.getBattery2TemperatureString()
    		    ),
    		    new PaSummary(
    		    		realTimeFC2.getForwardPowerString(), 
    		    		realTimeFC2.getReversePowerString(), 
    		    		realTimeFC2.getPaDeviceTemperatureString(), 
    		    		realTimeFC2.getPaBusCurrentString()
    		    ),
    		    new RfSummary(
    		    		realTimeFC2.getReceiverTemperatureString(), 
    		    		realTimeFC2.getReceiverCurrentString(), 
    		    		realTimeFC2.getTransmitCurrent3v3String(), 
    		    		realTimeFC2.getTransmitCurrent5v0String()
    		    ),
    		    new FC2AntsSummary(
    		    		realTimeFC2.getAntennaTemperatureString(), 
    		    		realTimeFC2.getAntennaStatus0String(), 
    		    		realTimeFC2.getAntennaStatus1String(), 
    		    		realTimeFC2.getAntennaStatus2String(), 
    		    		realTimeFC2.getAntennaStatus3String()
    		    ),
    		    new FC2SoftwareSummary(
    		    		realTimeFC2.getSequenceNumberString(),
    		    		realTimeFC2.isEclipsedString(),
    		    		realTimeFC2.getObcSoftResetCountString(),
    		    		realTimeFC2.getEpsHardResetCountString()
    		    )
    	));
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
