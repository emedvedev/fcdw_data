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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.server.shared.AntsSummary;
import uk.org.funcube.fcdw.server.shared.AsibSummary;
import uk.org.funcube.fcdw.server.shared.EpsSummary;
import uk.org.funcube.fcdw.server.shared.PaSummary;
import uk.org.funcube.fcdw.server.shared.RealTimeSummary;
import uk.org.funcube.fcdw.server.shared.RfSummary;
import uk.org.funcube.fcdw.server.shared.SoftwareSummary;


@Controller
@RequestMapping(value = "data/realtime")
public class RealTimeServiceRestImpl extends AbstractServiceRestImpl implements RealTimeServiceRest {
	
	private static final String PA_MILLI_WATT_FORMAT = "%4.1f";
	private static final String MILLI_VOLT_FORMAT = "%4d";
	private static final String TEMPERATURE_FORMAT = "%4d";
	private static final String SOL_TEMPERATURE_FORMAT = "%5.1f";
	private static final String ANTS_TEMPERATURE_FORMAT = "%5.1f";
	private static final String PA_TEMPERATURE_FORMAT = "%4.1f";
	private static final String PA_MILLI_AMPS_FORMAT = "%4.1f";
	private static final String MILLI_AMPS_FORMAT = "%4d";
	private static final String UNDEPLOYED = "Undeployed";
	private static final String DEPLOYED = "Deployed";
	
	public RealTimeServiceRestImpl() {
		setupPaTemps();
		setupAntsTemps();
	}
	
	@Autowired
	RealTimeDao realTimeDao;
	
	//@Transactional(readOnly = true)
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String get(@RequestParam(value = "callback") String callback) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long maxId = realTimeDao.findMaxId(2L);
		List<RealTimeEntity> entities = realTimeDao.findById(maxId);
		
		if (entities.isEmpty()) {
			return callback + "([error:" + "No data found" + "]);";
		}
		
		RealTimeEntity realTimeEntity = entities.get(0);
		
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
			return objectMapper.writeValueAsString(new JSONPObject(callback, map));
		} catch (JsonGenerationException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		} catch (JsonMappingException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		} catch (IOException e) {
			return callback + "([error:" + e.getMessage() + "]);";
		}
		
	}
	
	//@Transactional(readOnly = true)
	@RequestMapping(value = "/{satelliteId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String get(@PathVariable Long satelliteId, @RequestParam(value = "callback") String callback) {
		ObjectMapper objectMapper = new ObjectMapper();
	    Map<String, Object> map = new HashMap<String, Object>();
		
		Long maxId = realTimeDao.findMaxId(satelliteId);
		List<RealTimeEntity> entities = realTimeDao.findById(maxId);
		
		if (entities.isEmpty()) {
			return callback + "([error:" + "No data found" + "]);";
		}
		
		RealTimeEntity realTimeEntity = entities.get(0);

	    map.put("data", 
    		new RealTimeSummary(
    		    new EpsSummary(
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC4()), 
    		    		String.format(MILLI_VOLT_FORMAT, realTimeEntity.getC5()), 
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC6()), 
    		    		String.format(TEMPERATURE_FORMAT, realTimeEntity.getC12())),
    		    new AsibSummary(
    		    		String.format(SOL_TEMPERATURE_FORMAT, scaleAndOffset(realTimeEntity.getC20(), -0.2073, 158.239)), 
    		    		String.format(SOL_TEMPERATURE_FORMAT, scaleAndOffset(realTimeEntity.getC21(), -0.2083, 159.227)),
    		    		String.format(SOL_TEMPERATURE_FORMAT, scaleAndOffset(realTimeEntity.getC22(), -0.2076, 158.656)), 
    		    		String.format(SOL_TEMPERATURE_FORMAT, scaleAndOffset(realTimeEntity.getC23(), -0.2087, 159.045)), 
    		    		String.format(MILLI_VOLT_FORMAT, (int) (4.0 * realTimeEntity.getC24())), 
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC25()), 
    		    		String.format(MILLI_VOLT_FORMAT, (int) (6.0 * realTimeEntity.getC26()))),
    		    new RfSummary(
    		    		String.format(SOL_TEMPERATURE_FORMAT, scaleAndOffset(realTimeEntity.getC34(), -0.857, 193.672)), 
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC35()), 
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC36()), 
    		    		String.format(MILLI_AMPS_FORMAT, realTimeEntity.getC37())),
    		    new PaSummary(
    		    		String.format(PA_MILLI_WATT_FORMAT, getPaPower(realTimeEntity.getC39())), 
    		    		String.format(PA_MILLI_WATT_FORMAT, getPaPower(realTimeEntity.getC38())), 
    		    		String.format(PA_TEMPERATURE_FORMAT, getPaTemp(realTimeEntity.getC40().intValue())), 
    		    		String.format(PA_MILLI_AMPS_FORMAT, getPaCurrent(realTimeEntity.getC41()))),
    		    new AntsSummary(
    		    		String.format(ANTS_TEMPERATURE_FORMAT, getAntsTemp(realTimeEntity.getC42()
    		    				.intValue())), 
	    				String.format(ANTS_TEMPERATURE_FORMAT, getAntsTemp(realTimeEntity.getC43()
	    						.intValue())), 
						(realTimeEntity.getC44()) ? DEPLOYED : UNDEPLOYED, 
						(realTimeEntity.getC45()) ? DEPLOYED : UNDEPLOYED, 
						(realTimeEntity.getC46()) ? DEPLOYED : UNDEPLOYED, 
						(realTimeEntity.getC47()) ? DEPLOYED : UNDEPLOYED),
    		    new SoftwareSummary(
    		    		Long.toString(realTimeEntity.getSequenceNumber()),
    		    		realTimeEntity.getC49().toString(), 
    		    		realTimeEntity.getC50().toString(), 
    		    		realTimeEntity.getC51().toString(), 
    		    		realTimeEntity.getC52().toString(), 
    		    		realTimeEntity.getC53().toString(), 
    		    		realTimeEntity.getC54().toString(), 
    		    		realTimeEntity.getC55().toString(), 
    		    		realTimeEntity.getC56().toString(), 
    		    		realTimeEntity.getC57().toString())
    		    
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

	private static double getPaPower(final Long value) {
		return 0.005 * Math.pow(value.doubleValue(), 2.0629);
	}

	private static double getPaCurrent(final long value) {
		return (value * 0.5496) + 2.5425;
	}

	private static final double getPaTemp(final long value) {
		if (value == 99999 || value == -99999) {
			return 0.0;
		}
		return PA_TEMPS[(int) value];
	}

	public static final double getAntsTemp(final int value) {
		return ANTS_TEMPS[value];
	}
	

	private static final double[] PA_TEMPS = new double[256];
	

	private static final double[] ANTS_TEMPS = new double[256];

	private static void setupAntsTemps() {
		// data from ANTS manual, start and end values added
		// to ensure out of range is obvious (-255 or +255)
		int[][] tempToAdc = new int[][] { { -255, Integer.MIN_VALUE }, { -255, 2616 }, { -50, 2616 }, { -49, 2607 }, { -48, 2598 },
				{ -47, 2589 }, { -46, 2580 }, { -45, 2571 }, { -44, 2562 }, { -43, 2553 }, { -42, 2543 }, { -41, 2533 }, { -40, 2522 },
				{ -39, 2512 }, { -38, 2501 }, { -37, 2491 }, { -36, 2481 }, { -35, 2470 }, { -34, 2460 }, { -33, 2449 }, { -32, 2439 },
				{ -31, 2429 }, { -30, 2418 }, { -29, 2408 }, { -28, 2397 }, { -27, 2387 }, { -26, 2376 }, { -25, 2366 }, { -24, 2355 },
				{ -23, 2345 }, { -22, 2334 }, { -21, 2324 }, { -20, 2313 }, { -19, 2302 }, { -18, 2292 }, { -17, 2281 }, { -16, 2271 },
				{ -15, 2260 }, { -14, 2250 }, { -13, 2239 }, { -12, 2228 }, { -11, 2218 }, { -10, 2207 }, { -9, 2197 }, { -8, 2186 },
				{ -7, 2175 }, { -6, 2164 }, { -5, 2154 }, { -4, 2143 }, { -3, 2132 }, { -2, 2122 }, { -1, 2111 }, { 0, 2100 }, { 1, 2089 },
				{ 2, 2079 }, { 3, 2068 }, { 4, 2057 }, { 5, 2047 }, { 6, 2036 }, { 7, 2025 }, { 8, 2014 }, { 9, 2004 }, { 10, 1993 },
				{ 11, 1982 }, { 12, 1971 }, { 13, 1961 }, { 14, 1950 }, { 15, 1939 }, { 16, 1928 }, { 17, 1918 }, { 18, 1907 },
				{ 19, 1896 }, { 20, 1885 }, { 21, 1874 }, { 22, 1864 }, { 23, 1853 }, { 24, 1842 }, { 25, 1831 }, { 26, 1820 },
				{ 27, 1810 }, { 28, 1799 }, { 29, 1788 }, { 30, 1777 }, { 31, 1766 }, { 32, 1756 }, { 33, 1745 }, { 34, 1734 },
				{ 35, 1723 }, { 36, 1712 }, { 37, 1701 }, { 38, 1690 }, { 39, 1679 }, { 40, 1668 }, { 41, 1657 }, { 42, 1646 },
				{ 43, 1635 }, { 44, 1624 }, { 45, 1613 }, { 46, 1602 }, { 47, 1591 }, { 48, 1580 }, { 49, 1569 }, { 50, 1558 },
				{ 51, 1547 }, { 52, 1536 }, { 53, 1525 }, { 54, 1514 }, { 55, 1503 }, { 56, 1492 }, { 57, 1481 }, { 58, 1470 },
				{ 59, 1459 }, { 60, 1448 }, { 61, 1436 }, { 62, 1425 }, { 63, 1414 }, { 64, 1403 }, { 65, 1391 }, { 66, 1380 },
				{ 67, 1369 }, { 68, 1358 }, { 69, 1346 }, { 70, 1335 }, { 71, 1324 }, { 72, 1313 }, { 73, 1301 }, { 74, 1290 },
				{ 75, 1279 }, { 76, 1268 }, { 77, 1257 }, { 78, 1245 }, { 79, 1234 }, { 80, 1223 }, { 81, 1212 }, { 82, 1201 },
				{ 83, 1189 }, { 84, 1178 }, { 85, 1167 }, { 86, 1155 }, { 87, 1144 }, { 88, 1133 }, { 89, 1122 }, { 90, 1110 },
				{ 91, 1099 }, { 92, 1088 }, { 93, 1076 }, { 94, 1065 }, { 95, 1054 }, { 96, 1042 }, { 97, 1031 }, { 98, 1020 },
				{ 99, 1008 }, { 100, 997 }, { 101, 986 }, { 102, 974 }, { 103, 963 }, { 104, 951 }, { 105, 940 }, { 106, 929 },
				{ 107, 917 }, { 108, 906 }, { 109, 895 }, { 110, 883 }, { 111, 872 }, { 112, 860 }, { 113, 849 }, { 114, 837 },
				{ 115, 826 }, { 116, 814 }, { 117, 803 }, { 118, 791 }, { 119, 780 }, { 120, 769 }, { 121, 757 }, { 122, 745 },
				{ 123, 734 }, { 124, 722 }, { 125, 711 }, { 126, 699 }, { 127, 688 }, { 128, 676 }, { 129, 665 }, { 130, 653 },
				{ 131, 642 }, { 132, 630 }, { 133, 618 }, { 134, 607 }, { 135, 595 }, { 136, 584 }, { 137, 572 }, { 138, 560 },
				{ 139, 549 }, { 140, 537 }, { 141, 525 }, { 142, 514 }, { 143, 502 }, { 144, 490 }, { 145, 479 }, { 146, 467 },
				{ 147, 455 }, { 148, 443 }, { 149, 432 }, { 150, 420 }, { 255, 420 }, { 255, Integer.MIN_VALUE } };
		for (int i = 0; i < 256; ++i) {
			// calc values for all possible 8bit values
			double adc = (i * 3300.0) / 255.75;
			for (int j = 0; j < tempToAdc.length; j++) {
				if (j != 0 && adc > tempToAdc[j][1]) {
					double t1 = tempToAdc[j][0];
					double a1 = tempToAdc[j][1];
					double diffa = tempToAdc[j - 1][1] - a1;
					double difft = tempToAdc[j - 1][0] - t1;
					ANTS_TEMPS[i] = ((adc - a1) * (difft / diffa)) + t1;
					break;
				}
			}
		}
	}

	private static void setupPaTemps() {
		// Data from adc 79 to 252 measured,
		// 0-79 continues using gradient of last
		// three values, 252 to 255 likewise

		final double[][] tempToAdc = { { 87.983, Double.MIN_VALUE }, { 87.983, 0 }, {
		/*
		 * first measured value
		 */55.3, 79 }, { 49.6, 91 }, { 45.3, 103 }, { 41.1, 115 }, { 37.6, 125 }, { 35.7, 129 }, { 33.6, 137 }, { 30.6, 145 },
				{ 27.6, 154 }, { 25.1, 161 }, { 22.6, 169 }, { 20, 176 }, { 17.6, 183 }, { 15.1, 189 }, { 12.6, 196 }, { 10, 203 },
				{ 7.5, 208 }, { 5, 214 }, { 2.4, 220 }, { 0, 224 }, { -2.9, 230 }, { -5, 233 }, { -7.5, 237 }, { -10, 241 },
				{ -12.3, 244 }, { -15, 247 }, { /*
												 * last measured value
												 */-20, 252 }, { -22.846, 255 }, { -22.846, Double.MAX_VALUE } };

		// calc values for all possible 8bit values
		for (int adc = 0; adc < 256; ++adc) {
			for (int j = 0; j < tempToAdc.length; j++) {
				if (adc != 0 && adc < tempToAdc[j][1]) {
					double t1 = tempToAdc[j][0];
					double a1 = tempToAdc[j][1];
					double diffa = tempToAdc[j - 1][1] - a1;
					double difft = tempToAdc[j - 1][0] - t1;
					double value = ((adc - a1) * (difft / diffa)) + t1;
					PA_TEMPS[adc] = value;
					break;
				}
			}
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
