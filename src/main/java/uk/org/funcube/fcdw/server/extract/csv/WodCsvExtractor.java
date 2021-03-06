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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.SatelliteStatusDao;
import uk.org.funcube.fcdw.dao.WholeOrbitDataDao;
import uk.org.funcube.fcdw.domain.SatelliteStatusEntity;
import uk.org.funcube.fcdw.domain.WholeOrbitDataEntity;
import uk.org.funcube.fcdw.server.util.Clock;

import com.csvreader.CsvWriter;

public class WodCsvExtractor {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss zzz";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    protected final SimpleDateFormat SDTF = new SimpleDateFormat(DATE_TIME_FORMAT);
    protected final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    Clock clock;

    @Autowired
    WholeOrbitDataDao wholeOrbitDataDao;

    @Autowired
    SatelliteStatusDao satelliteStatusDao;

    private static Logger LOG = Logger.getLogger(WodCsvExtractor.class.getName());

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void extract(final long satelliteId) {

        final Date currentDate = clock.currentDate();

        final Timestamp latestSatelliteTime = wholeOrbitDataDao.getLatestSatelliteTime(satelliteId);

        final Timestamp since = new Timestamp(latestSatelliteTime.getTime() - (24 * 60 * 60 * 1000));

        final List<WholeOrbitDataEntity> wod24 = wholeOrbitDataDao.getSinceSatelliteTime(satelliteId, since);

        if (wod24.size() == 0) {
            return;
        }

        final File fileLocation = new File(System.getProperty("csv.wod"));

        if (fileLocation.exists()) {
            fileLocation.delete();
        }

        writeFile(wod24, fileLocation);

    }

    /**
     * @param wod24
     * @param fileLocation
     */
    private void writeFile(List<WholeOrbitDataEntity> wod, File fileLocation) {
        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation, true), ',');

            // write out the headers
            csvOutput.write("Satellite Date/Time UTC");
            csvOutput.write("Black Chassis deg. C");
            csvOutput.write("Silver Chassis deg. C");
            csvOutput.write("Black Panel deg. C");
            csvOutput.write("Silver Panel deg. C");
            csvOutput.write("Solar Panel +X deg. C");
            csvOutput.write("Solar Panel -X deg. C");
            csvOutput.write("Solar Panel +Y deg. C");
            csvOutput.write("Solar Panel -Y deg. C");
            csvOutput.write("Solar Panel X mV");
            csvOutput.write("Solar Panel Y mV");
            csvOutput.write("Solar Panel Z mV");
            csvOutput.write("Tot. Photo Curr. mA ");
            csvOutput.write("Battery mV");
            csvOutput.write("Tot. System Curr. mA");
            csvOutput.endRecord();

            long tsLong = 0;
            String c1 = "";
            String c2 = "";
            String c3 = "";
            String c4 = "";
            String c5 = "";
            String c6 = "";
            String c7 = "";
            String c8 = "";
            String c9 = "";
            String c10 = "";
            String c11 = "";
            String c12 = "";
            String c13 = "";
            String c14 = "";

            for (WholeOrbitDataEntity entity : wod) {

                Timestamp satelliteTime = new Timestamp(entity.getCreatedDate().getTime());

                if (tsLong == 0) {
                    tsLong = satelliteTime.getTime();
                    c1 = scale(entity.getC1(), -0.024, 75.244);
                    c2 = scale(entity.getC2(), -0.024, 74.750);
                    c3 = scale(entity.getC3(), -0.024, 75.039);
                    c4 = scale(entity.getC4(), -0.024, 75.987);
                    c5 = scale(entity.getC5(), -0.2073, 158.239);
                    c6 = scale(entity.getC6(), -0.2083, 159.227);
                    c7 = scale(entity.getC7(), -0.2076, 158.656);
                    c8 = scale(entity.getC8(), -0.2087, 159.045);
                    c9 = String.format("%4d", entity.getC9());
                    c10 = String.format("%4d", entity.getC10());
                    c11 = String.format("%4d", entity.getC11());
                    c12 = String.format("%4d", entity.getC12());
                    c13 = String.format("%4d", entity.getC13());
                    c14 = String.format("%4d", entity.getC14());

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5, c6,
                            c7, c8, c9, c10, c11, c12, c13, c14);
                }
                else {

                    final long timeDiff = satelliteTime.getTime() - tsLong;
                    if (timeDiff > 60000) {
                        // fill in the gaps
                        long gaps = (timeDiff / 60000);
                        for (long i = 1; i < gaps; i++) {
                            Timestamp intervalTime = new Timestamp(tsLong + (60000 * i));
                            writeRecord(csvOutput, intervalTime, c1, c2, c3, c4, c5, c6,
                                    c7, c8, c9, c10, c11, c12, c13, c14);
                        }
                    }

                    c1 = scale(entity.getC1(), -0.024, 75.244);
                    c2 = scale(entity.getC2(), -0.024, 74.750);
                    c3 = scale(entity.getC3(), -0.024, 75.039);
                    c4 = scale(entity.getC4(), -0.024, 75.987);
                    c5 = scale(entity.getC5(), -0.2073, 158.239);
                    c6 = scale(entity.getC6(), -0.2083, 159.227);
                    c7 = scale(entity.getC7(), -0.2076, 158.656);
                    c8 = scale(entity.getC8(), -0.2087, 159.045);
                    c9 = String.format("%4d", entity.getC9());
                    c10 = String.format("%4d", entity.getC10());
                    c11 = String.format("%4d", entity.getC11());
                    c12 = String.format("%4d", entity.getC12());
                    c13 = String.format("%4d", entity.getC13());
                    c14 = String.format("%4d", entity.getC14());

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5, c6,
                            c7, c8, c9, c10, c11, c12, c13, c14);

                    tsLong = satelliteTime.getTime();
                }
            }

            csvOutput.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRecord(CsvWriter csvOutput, Timestamp satelliteTime,
            String c1, String c2, String c3, String c4, String c5, String c6,
            String c7, String c8, String c9, String c10, String c11,
            String c12, String c13, String c14) throws IOException {
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
                case 8:
                    csvOutput.write(c8);
                    break;
                case 9:
                    csvOutput.write(c9);
                    break;
                case 10:
                    csvOutput.write(c10);
                    break;
                case 11:
                    csvOutput.write(c11);
                    break;
                case 12:
                    csvOutput.write(c12);
                    break;
                case 13:
                    csvOutput.write(c13);
                    break;
                case 14:
                    csvOutput.write(c14);
                    break;
            }

        }

        csvOutput.endRecord();
    }

    @Transactional(readOnly = false)
    public void extractWeekly(final long satelliteId) {
        final List<SatelliteStatusEntity> satelliteStatuses = satelliteStatusDao.findBySatelliteId(satelliteId);

        if (satelliteStatuses.isEmpty()) {
            LOG.error("No satellite status found for satellite id: " + satelliteId);
            return;
        }

        final SatelliteStatusEntity satelliteStatus = satelliteStatuses.get(0);
        final Timestamp lastDumpTime = satelliteStatus.getLastWodDump();
        LOG.debug("Last dump time recorded: " + SDTF.format(new Date(lastDumpTime.getTime())));
        final Date lastDumpDate = new Date(lastDumpTime.getTime() + 1000);
        final Timestamp newDumpTime = new Timestamp(lastDumpTime.getTime() + (7 * 24 * 60 * 60 * 1000));
        LOG.debug("New end dump time calculated: " + SDTF.format(new Date(newDumpTime.getTime())));
        final Date newDumpDate = new Date(newDumpTime.getTime());

        final String fileName = "wod" + SDF.format(newDumpDate) + ".csv";

        final String fileSeparator = System.getProperty("file.separator");

        final File fileLocation = new File(System.getProperty("wodweekly.dir") + fileSeparator + fileName);

        LOG.debug("Dumping WOD for period : "
                + SDTF.format(lastDumpDate)
                + " to "
                + SDTF.format(newDumpDate) + " "
                + "to file " + fileName);

        final List<WholeOrbitDataEntity> wodWeekly = wholeOrbitDataDao.getBySatelliteIdAndValidAndCreatedDateBetween(
                satelliteId, lastDumpDate, newDumpDate);

        LOG.debug("First date in list:" + SDTF.format(wodWeekly.get(0).getCreatedDate()));
        LOG.debug("Last date list:" + SDTF.format(wodWeekly.get(wodWeekly.size() - 1).getCreatedDate()));

        if (!wodWeekly.isEmpty()) {

            writeFile(wodWeekly, fileLocation);

            satelliteStatus.setLastWodDump(newDumpTime);
            satelliteStatusDao.save(satelliteStatus);
        }
    }

    private String scale(final Long adc, final Double multiplier, final Double offset) {
        final double value = (adc * multiplier) + offset;
        return String.format("%6.2f", value);
    }

}
