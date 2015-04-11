// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.TleDao;
import uk.org.funcube.fcdw.domain.TleEntity;
import uk.org.funcube.fcdw.satellite.TLE;
import uk.org.funcube.fcdw.server.util.Clock;

public class TleProcessor {

    private static final String ELEM_URL_CELESTRAK_CUBESAT = "http://celestrak.com/NORAD/elements/cubesat.txt";
    private static final String CELESTRAK_CUBESAT = "CELESTRAK_CUBESAT";

    private static final Logger LOGGER = Logger
            .getLogger(TleProcessor.class.getName());

    private static ConcurrentMap<String, TLE> allSatElems = new ConcurrentHashMap<String, TLE>();

    @Autowired
    TleDao tleDao;

    @Autowired
    Clock clock;

    public void process() {
        LOGGER.info("loadCelestrakKeps " + CELESTRAK_CUBESAT
                + (loadElemFromNetwork(CELESTRAK_CUBESAT) ? " succeed" : " failed"));

    }

    @Transactional(readOnly = false)
    private boolean loadElemFromNetwork(final String kepSource) {
        boolean success = false;
        URL url;
        try {

            url = new URL(ELEM_URL_CELESTRAK_CUBESAT);

            final List<TLE> tmpSatElems = TLE.importSat(url.openStream());

            for (TLE tle : tmpSatElems) {

                final String catalogueId = Long.toString(tle.getCatnum());

                TLE storedTle = allSatElems.get(catalogueId);

                if (storedTle == null) {
                    tle.setCreateddate(clock.currentDate());
                    tleDao.save(new TleEntity(tle));
                    allSatElems.put(catalogueId, tle);
                }
                else if (storedTle.getSetnum() < tle.getSetnum()) {
                    tle.setCreateddate(clock.currentDate());
                    allSatElems.replace(catalogueId, tle);
                }
            }

            success = true;

        }
        catch (final IOException e) {
            LOGGER.info("Unable to get " + kepSource + " keps.");
            success = false;
        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
        return success;
    }

}
