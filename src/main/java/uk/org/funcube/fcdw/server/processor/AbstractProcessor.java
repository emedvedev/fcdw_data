// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.EpochDao;
import uk.org.funcube.fcdw.domain.EpochEntity;

/**
 * @author g4dpz
 *
 */
public class AbstractProcessor {
	
	@Autowired
	EpochDao epochDao;

	/**
	 * 
	 */
	public AbstractProcessor() {
		super();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	protected List<EpochEntity> getEpoch(long satelliteId) {
		List<EpochEntity> epochList = epochDao.findBySatelliteId(satelliteId);
		return epochList;
	}

}