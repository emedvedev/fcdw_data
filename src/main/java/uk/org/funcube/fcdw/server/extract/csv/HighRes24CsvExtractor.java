// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.extract.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.HighResolutionDao;
import uk.org.funcube.fcdw.domain.HighResolutionEntity;
import uk.org.funcube.fcdw.server.util.Clock;

import com.csvreader.CsvWriter;

public class HighRes24CsvExtractor {

    private static double[] SOL_ILLUMINATION = new double[1024];

    static {
        setupSunSensors();
    }

    @Autowired
    Clock clock;

    @Autowired
    HighResolutionDao highResolutionDao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void extract(final long satelliteId) {
        
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        final Timestamp since = new Timestamp(cal.getTimeInMillis());

        final List<HighResolutionEntity> highResOneHour = highResolutionDao.getSinceCreatedDate(satelliteId, since);

        if (highResOneHour.size() == 0) {
            return;
        }

        final File fileLocation = new File(System.getProperty("csv.hires24"));

        if (fileLocation.exists()) {
            fileLocation.delete();
        }

        try {
            // use FileWriter constructor that specifies open for appending
            final CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation, true), ',');

            // write out the headers
            csvOutput.write("Satellite Date/Time UTC");
            csvOutput.write("Sun Sensor +X log Lux");
            csvOutput.write("Sun Sensor +Y log Lux");
            csvOutput.write("Sun Sensor -Y log Lux");
            csvOutput.write("Sun Sensor +Z log Lux");
            csvOutput.write("Sun Sensor -Z log Lux");
            csvOutput.write("Tot. Photo Curr. mA");
            csvOutput.write("Battery mV");
            csvOutput.endRecord();

            long tsLong = 0;
            String c1 = "";
            String c2 = "";
            String c3 = "";
            String c4 = "";
            String c5 = "";
            String c6 = "";
            String c7 = "";

            for (HighResolutionEntity entity : highResOneHour) {

                Timestamp satelliteTime = entity.getSatelliteTime();

                if (tsLong == 0) {
                    tsLong = satelliteTime.getTime();
                    c1 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC1().intValue()]));
                    c2 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC2().intValue()]));
                    c3 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC3().intValue()]));
                    c4 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC4().intValue()]));
                    c5 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC5().intValue()]));
                    c6 = String.format("%4.0f", new Double(entity.getC6() * 2.0));
                    c7 = String.format("%3.0f", new Double(entity.getC7() * 2.0));

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5, c6,
                            c7);
                }
                else {

                    final long timeDiff = satelliteTime.getTime() - tsLong;
                    if (timeDiff > 1000) {
                        // fill in the gaps
                        final long gaps = (timeDiff / 1000);
                        for (long i = 1; i < gaps; i++) {
                            final Timestamp intervalTime = new Timestamp(tsLong + (1000 * i));
                            writeRecord(csvOutput, intervalTime, c1, c2, c3, c4, c5, c6,
                                    c7);
                        }
                    }

                    c1 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC1().intValue()]));
                    c2 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC2().intValue()]));
                    c3 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC3().intValue()]));
                    c4 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC4().intValue()]));
                    c5 = String.format("%5.3f", new Double(SOL_ILLUMINATION[entity.getC5().intValue()]));
                    c6 = String.format("%4.0f", new Double(entity.getC6() * 2.0));
                    c7 = String.format("%3.0f", new Double(entity.getC7() * 2.0));

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5, c6,
                            c7);

                    tsLong = satelliteTime.getTime();
                }
            }

            csvOutput.close();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }

    }

    private static void setupSunSensors() {
        final double[][] tempToAdc = new double[][] {
                {5, 0}, {9, 2.158}, {19, 2.477}, {23, 2.64}, {33, 2.8},
                {50, 2.881}, {56, 2.889}, {100, 3.04}, {133, 3.14},
                {200, 3.25}, {265, 3.346}, {333, 3.475}, {400, 3.58},
                {467, 3.69}, {533, 3.81}, {600, 3.92}, {666, 4.03},
                {700, 4.079}, {732, 4.13}, {800, 4.245}, {866, 4.325},
                {933, 4.38}, {984, 4.42}, {992, 4.5319}, {999, 4.6438},
                {1007, 4.7557}, {1015, 4.8676}, {1023, 4.9795}};

        for (int i = 0; i < 1024; ++i) {
            if (i < 984) {
                // calc values for all possible 8bit values
                for (int j = 0; j < tempToAdc.length; j++) {
                    if (i < tempToAdc[j][0]) {
                        // get this current value
                        final double[] currentPair = tempToAdc[j];
                        // get the previous value
                        double[] previousPair = new double[] {0, 0};
                        if (j != 0) {
                            previousPair = tempToAdc[j - 1];
                        }
                        // get the adc difference
                        final double adcDiff = currentPair[0] - previousPair[0];
                        // get the value difference
                        final double valueDiff = currentPair[1] - previousPair[1];
                        // scale per unit
                        double increment = valueDiff / adcDiff;
                        // calculate the sol value for this adc value
                        double value = previousPair[1] + (i - previousPair[0]) * increment;
                        // save it in the array
                        SOL_ILLUMINATION[i] = value;
                        // break;
                        break;
                    }
                }
            }
            else {
                SOL_ILLUMINATION[i] = 4.420;
            }
        }
    }

    private void writeRecord(CsvWriter csvOutput, Timestamp satelliteTime,
            String c1, String c2, String c3, String c4, String c5, String c6,
            String c7) throws IOException {
        for (int i = 0; i < 15; i++) {

            switch (i) {
                case 0:
                    csvOutput.write(satelliteTime.toString());
                    break;
                case 1:
                    csvOutput.write(c1);
                    break;
                case 2:
                    csvOutput.write(c2);
                    break;
                case 3:
                    csvOutput.write(c3);
                    break;
                case 4:
                    csvOutput.write(c4);
                    break;
                case 5:
                    csvOutput.write(c5);
                    break;
                case 6:
                    csvOutput.write(c6);
                    break;
                case 7:
                    csvOutput.write(c7);
                    break;
            }

        }

        csvOutput.endRecord();
    }

}
