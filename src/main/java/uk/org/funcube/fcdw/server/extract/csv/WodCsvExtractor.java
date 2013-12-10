// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.extract.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.WholeOrbitDataDao;
import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;
import uk.org.funcube.fcdw.server.util.Clock;

import com.csvreader.CsvWriter;

public class WodCsvExtractor {
	
	@Autowired
	Clock clock;
	
	@Autowired
	WholeOrbitDataDao wholeOrbitDataDao;
	
	@Transactional(readOnly=true)
	public void extract(long satelliteId) {
		
		Date currentDate = clock.currentDate();
		
		Timestamp latestSatelliteTime = wholeOrbitDataDao.getLatestSatelliteTime(satelliteId);
		
		Timestamp since = new Timestamp(latestSatelliteTime.getTime() - (24 * 60 * 60 * 1000));
		
		List<WholeOrbitDataEntity> wod24 = wholeOrbitDataDao.getSinceSatelliteTime(satelliteId, since);
		
		File fileLocation = new File(System.getProperty("csv.wod"));
		
		if (fileLocation.exists()) {
			fileLocation.delete();
		}
		
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation, true), ',');
			
			// write out the headers
			csvOutput.write("Satellite Time");
			csvOutput.write("Black Chassis");
			csvOutput.write("Silver Chasis");
			csvOutput.write("Black Panel");
			csvOutput.write("Silver Panel");
			csvOutput.write("Solar Panel +X");
			csvOutput.write("Solar Panel -X");
			csvOutput.write("Solar Panel +Y");
			csvOutput.write("Solar Panel -Y");
			csvOutput.write("Solar Panel Voltage X");
			csvOutput.write("Solar Panel Voltage Y");
			csvOutput.write("Solar Panel Voltage Z");
			csvOutput.write("Total Photo Current");
			csvOutput.write("Battery Voltage");
			csvOutput.write("Total System Current");
			csvOutput.endRecord();
			
			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
