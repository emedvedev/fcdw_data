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
		
		if (wod24.size() == 0) {
			return;
		}
		
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
			
			for (WholeOrbitDataEntity entity : wod24) {
				for (int i = 0; i < 15; i++) {
					
					switch (i) {
					case 0: 
						csvOutput.write(entity.getSatelliteTime().toString());
						break;
					case 1: 
						csvOutput.write(scale(entity.getC1(), -0.024, 75.244));
						break;
					case 2: 
						csvOutput.write(scale(entity.getC2(), -0.024, 74.750));
						break;
					case 3: 
						csvOutput.write(scale(entity.getC3(), -0.024, 75.039));
						break;
					case 4: 
						csvOutput.write(scale(entity.getC4(), -0.024, 75.987));
						break;
					case 5: 
						csvOutput.write(scale(entity.getC5(), -0.2073, 158.239));
						break;
					case 6: 
						csvOutput.write(scale(entity.getC6(), -0.2083, 159.227));
						break;
					case 7: 
						csvOutput.write(scale(entity.getC7(), -0.2076, 158.656));
						break;
					case 8: 
						csvOutput.write(scale(entity.getC8(), -0.2087, 159.045));
						break;
					case 9: 
						csvOutput.write(scale(entity.getC9(), 0.001, 0.0));
						break;
					case 10: 
						csvOutput.write(scale(entity.getC10(), 0.001, 0.0));
						break;
					case 11: 
						csvOutput.write(scale(entity.getC11(), 0.001, 0.0));
						break;
					case 12: 
						csvOutput.write(scale(entity.getC12(), 0.01, 0.0));
						break;
					case 13: 
						csvOutput.write(scale(entity.getC13(), 0.001, 0.0));
						break;
					case 14: 
						csvOutput.write(scale(entity.getC14(), 0.01, 0.0));
						break;
					}
					
				}

				csvOutput.endRecord();
			}
			
			csvOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	private String scale(Long adc, Double multiplier, Double offset) {
		double value = (adc * multiplier) + offset;
		return String.format("%6.2f", value);
	}

}
