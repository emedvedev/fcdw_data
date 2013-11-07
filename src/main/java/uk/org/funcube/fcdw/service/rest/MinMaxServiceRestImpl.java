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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.MinMaxDao;
import uk.org.funcube.fcdw.domain.MinMaxEntity;
import uk.org.funcube.fcdw.server.shared.MinMax;

@Controller
@RequestMapping(value = "data/minmax")
public class MinMaxServiceRestImpl implements MinMaxServiceRest {
	
	@Autowired
	MinMaxDao minMaxTimeDao;
	
	//@Transactional(readOnly = true)
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET)
	@ResponseBody	
	public List<MinMax> get(@PathVariable Long satelliteId) {
		List<MinMax> minMaxValues = new ArrayList<MinMax>();
		List<MinMaxEntity> minMaxEntities = minMaxTimeDao.findBySatelliteId(satelliteId);
		for (MinMaxEntity minMaxEntity : minMaxEntities) {
			minMaxValues.add(new MinMax(minMaxEntity));
		}
		return minMaxValues;
	}

}
