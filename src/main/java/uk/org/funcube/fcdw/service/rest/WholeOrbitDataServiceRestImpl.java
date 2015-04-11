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

import uk.org.funcube.fcdw.dao.WholeOrbitDataDao;
import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;


@Controller
@RequestMapping(value = "data/wod")
public class WholeOrbitDataServiceRestImpl extends AbstractServiceRestImpl implements WholeOrbitDataServiceRest {
	
	@Autowired
	WholeOrbitDataDao wholeOrbitDataDao;
	
	// get all data for one orbit for a given satellite
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<List<String>> getAllWodForSatellite(
			@PathVariable(value = "satelliteId") Long satelliteId) {
		
		Long maxId = wholeOrbitDataDao.findMaxId(satelliteId);
		// as we process set of WOD data for a satellite all at once. We won't
		// get interleaved with other satellite WOD
		Long firstItem = maxId - 104;
		
		List<WholeOrbitDataEntity> oneHourWod = wholeOrbitDataDao.getSinceId(satelliteId, firstItem);
		List<List<String>> channelList = new ArrayList<List<String>>();
		
		List<String> c1Values = new ArrayList<String>();
		List<String> c2Values = new ArrayList<String>();
		List<String> c3Values = new ArrayList<String>();
		List<String> c4Values = new ArrayList<String>();
		List<String> c5Values = new ArrayList<String>();
		List<String> c6Values = new ArrayList<String>();
		List<String> c7Values = new ArrayList<String>();
		List<String> c8Values = new ArrayList<String>();
		List<String> c9Values = new ArrayList<String>();
		List<String> c10Values = new ArrayList<String>();
		List<String> c11Values = new ArrayList<String>();
		List<String> c12Values = new ArrayList<String>();
		List<String> c13Values = new ArrayList<String>();
		List<String> c14Values = new ArrayList<String>();
		
		/*
		 * MaterialScience1 = new MultiplierOffsetTelemetryValue(-0.024,75.244,this.Get12bitsAsInt(rawStream)); // black structure
            MaterialScience2 = new MultiplierOffsetTelemetryValue(-0.024, 74.750, this.Get12bitsAsInt(rawStream)); // silver structure
            MaterialScience3 = new MultiplierOffsetTelemetryValue(-0.024, 75.039, this.Get12bitsAsInt(rawStream)); // black strip
            MaterialScience4 = new MultiplierOffsetTelemetryValue(-0.024, 75.987, this.Get12bitsAsInt(rawStream)); // silver strip

            PanelTempX1 = new MultiplierOffsetTelemetryValue(-0.2073, 158.239, this.Get10bitsAsInt(rawStream));
            PanelTempX2 = new MultiplierOffsetTelemetryValue(-0.2083, 159.227, this.Get10bitsAsInt(rawStream));
            PanelTempY1 = new MultiplierOffsetTelemetryValue(-0.2076, 158.656, this.Get10bitsAsInt(rawStream));
            PanelTempY2 = new MultiplierOffsetTelemetryValue(-0.2087, 159.045, this.Get10bitsAsInt(rawStream));
		 */
		
		for (WholeOrbitDataEntity entity : oneHourWod) {
			c1Values.add(String.format("%4.1f", scaleAndOffset(entity.getC1(), -0.024, 75.244)));
			c2Values.add(String.format("%4.1f", scaleAndOffset(entity.getC2(), -0.024, 74.750)));
			c3Values.add(String.format("%4.1f", scaleAndOffset(entity.getC3(), -0.024, 75.039)));
			c4Values.add(String.format("%4.1f", scaleAndOffset(entity.getC4(), -0.024, 75.987)));
			c5Values.add(String.format("%4.1f", scaleAndOffset(entity.getC5(), -0.2073, 158.239)));
			c6Values.add(String.format("%4.1f", scaleAndOffset(entity.getC6(), -0.2083, 159.227)));
			c7Values.add(String.format("%4.1f", scaleAndOffset(entity.getC7(), -0.2076, 158.656)));
			c8Values.add(String.format("%4.1f", scaleAndOffset(entity.getC8(), -0.2087, 159.045)));
			c9Values.add(Long.toString(entity.getC9()));
			c10Values.add(Long.toString(entity.getC10()));
			c11Values.add(Long.toString(entity.getC11()));
			c12Values.add(Long.toString(entity.getC12()));
			c13Values.add(Long.toString(entity.getC13()));
			c14Values.add(Long.toString(entity.getC14()));
		}
		
		channelList.add(c1Values);
		channelList.add(c2Values);
		channelList.add(c3Values);
		channelList.add(c4Values);
		channelList.add(c5Values);
		channelList.add(c6Values);
		channelList.add(c7Values);
		channelList.add(c8Values);
		channelList.add(c9Values);
		channelList.add(c10Values);
		channelList.add(c11Values);
		channelList.add(c12Values);
		channelList.add(c13Values);
		channelList.add(c14Values);
		
		return channelList;
		
	}
	
	// get one channel for one orbit for a given satellite
//	@RequestMapping(value = "/{satelliteId}/{channel}", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public String getWodChannelForSatellite(
//			@PathVariable(value = "satelliteId") Long satelliteId,
//			@PathVariable(value = "channel") Long channel) {
//	}
}
