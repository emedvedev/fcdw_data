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

import uk.org.funcube.fcdw.dao.UserRankingDao;
import uk.org.funcube.fcdw.server.shared.SatellitePosition;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.service.PredictorService;
import uk.org.funcube.fcdw.service.rest.model.QslData;

@Controller
@RequestMapping(value = "data/qsl")
public class QslServiceRestImpl implements PredictorServiceRest {
	
	@Autowired
	UserRankingDao userRankingDao;
	
	@RequestMapping(value = "/{siteId}/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody	
	public QslData get(@PathVariable String siteId, @PathVariable Long satelliteId) {
		
		return new QslData(userRankingDao.findBySatelliteIdAndSiteId(satelliteId, siteId));
	}

}
