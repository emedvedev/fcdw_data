// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.TleDao;
import uk.org.funcube.fcdw.domain.TleEntity;
import uk.org.funcube.fcdw.satellite.GroundStationPosition;
import uk.org.funcube.fcdw.satellite.SatPos;
import uk.org.funcube.fcdw.satellite.Satellite;
import uk.org.funcube.fcdw.satellite.SatelliteFactory;
import uk.org.funcube.fcdw.satellite.TLE;
import uk.org.funcube.fcdw.server.shared.SatellitePosition;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;

@Controller
@RequestMapping(value = "data/predictor")
public class PredictorServiceRestImpl implements PredictorServiceRest {
	
	@Autowired
	TleDao tleDao;
	
	@Autowired
	UTCClock clock;
	
	//@Transactional(readOnly = true)
	@RequestMapping(value = "/{catnum}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody	
	public SatellitePosition get(@PathVariable Long catnum) {
		
		SatellitePosition position = null;
		
		List<TleEntity> tleEntities = tleDao.findByCatnum(catnum);
		
		if (!tleEntities.isEmpty()) {
			final TleEntity tleEntity = tleEntities.get(0);
			final TLE tle = new TLE(
					tleEntity.getCatnum(), 
					tleEntity.getName(), 
					tleEntity.getSetnum(), 
					tleEntity.getYear(), 
					tleEntity.getRefepoch(), 
					tleEntity.getIncl(), 
					tleEntity.getRaan(), 
					tleEntity.getEccn(), 
					tleEntity.getArgper(), 
					tleEntity.getMeanan(), 
					tleEntity.getMeanmo(), 
					tleEntity.getDrag(), 
					tleEntity.getNddot6(), 
					tleEntity.getBstar(), 
					tleEntity.getOrbitnum(), 
					tleEntity.getEpoch(), 
					tleEntity.getXndt2o(), 
					tleEntity.getXincl(), 
					tleEntity.getXnodeo(), 
					tleEntity.getEo(), 
					tleEntity.getOmega(), 
					tleEntity.getMo(), 
					tleEntity.getXno(), 
					tleEntity.isDeepspace(), 
					tleEntity.getCreateddate());
			
			Satellite satellite = SatelliteFactory.createSatellite(tle);
			
			satellite.calculateSatelliteVectors(clock.currentDate());
			satellite.calculateSatelliteGroundTrack();
			
			SatPos satPos 
				= satellite.calculateSatPosForGroundStation(new GroundStationPosition(0.0, 0.0, 0.0));
			
			final NumberFormat numberFormat = NumberFormat.getNumberInstance();
	        numberFormat.setMaximumFractionDigits(2);
			
			position = new SatellitePosition(
					numberFormat.format(satPos.getLatitude() / (Math.PI * 2.0) * 360),
					numberFormat.format(satPos.getLongitude() / (Math.PI * 2.0) * 360),
					satPos.isEclipsed() ? "yes" : "no",
					numberFormat.format(satPos.getEclipseDepth()));
		}
		
		return position;
	}

}
