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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import uk.org.funcube.fcdw.dao.RealTimeDao;
import uk.org.funcube.fcdw.domain.RealTimeEntity;
import uk.org.funcube.fcdw.server.util.Clock;

import com.csvreader.CsvWriter;

public class RealTimeCsvExtractor {

    private static double[] SOL_ILLUMINATION = new double[1024];
    protected static final double[] ANTS_TEMPS = new double[256];
    private static final double[] PA_TEMPS = new double[256];

    static {
        setupSunSensors();
        setupAntsTemps();
        setupPaTemps();
    }

    @Autowired
    Clock clock;

    @Autowired
    RealTimeDao realTimeDao;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public void extract(long satelliteId) {

        Date currentDate = clock.currentDate();

        Timestamp latestSatelliteTime = realTimeDao
                .getLatestSatelliteTime(satelliteId);

        Timestamp since = new Timestamp(latestSatelliteTime.getTime()
                - (250 * 60 * 1000));

        List<RealTimeEntity> realTime12 = realTimeDao.getSinceSatelliteTime(
                satelliteId, since);

        if (realTime12.size() == 0) {
            return;
        }

        File fileLocation = new File(System.getProperty("csv.realtime"));

        if (fileLocation.exists()) {
            fileLocation.delete();
        }

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(fileLocation,
                    true), ',');

            // write out the headers
            csvOutput.write("Satellite Date/Time UTC");
            csvOutput.write("Solar Panel Voltage X mV");
            csvOutput.write("Solar Panel Voltage Y mV");
            csvOutput.write("Solar Panel Voltage Z mV");
            csvOutput.write("Total Photo Current mA");
            csvOutput.write("Battery Voltage mV");
            csvOutput.write("Total System Current mA");
            csvOutput.write("Boost Converter Temp 1 C");
            csvOutput.write("Boost Converter Temp 2 C");
            csvOutput.write("Boost Converter Temp 3 C");
            csvOutput.write("Battery Temp C");
            csvOutput.write("Sun Sensor X+");
            csvOutput.write("Sun Sensor Y+");
            csvOutput.write("Sun Sensor Z+");
            csvOutput.write("Solar Panel Temp X+ C");
            csvOutput.write("Solar Panel Temp X- C");
            csvOutput.write("Solar Panel Temp Y+ C");
            csvOutput.write("Solar Panel Temp Y- C");
            csvOutput.write("3.3 Bus Voltage mV");
            csvOutput.write("3.3 Bus Current mA");
            csvOutput.write("5.0 Bus Voltage mV");
            csvOutput.write("RF Temperature C");
            csvOutput.write("Receive Current mA");
            csvOutput.write("RF Current 3.3V Bus mA");
            csvOutput.write("RF Current 5.0V Bus mA");
            csvOutput.write("PA Device Temperature C");
            csvOutput.write("PA Bus Current mA");
            csvOutput.write("Antenna Temp 0 C");
            csvOutput.write("Antenna Temp 1 C");
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
            String c15 = "";
            String c16 = "";
            String c17 = "";
            String c18 = "";
            String c19 = "";
            String c20 = "";
            String c21 = "";
            String c22 = "";
            String c23 = "";
            String c24 = "";
            String c25 = "";
            String c26 = "";
            String c27 = "";
            String c28 = "";
            String c29 = "";

            for (RealTimeEntity entity : realTime12) {

                Timestamp satelliteTime = entity.getSatelliteTime();

                if (tsLong == 0) {
                    tsLong = satelliteTime.getTime();

                    // Solar Panel Voltage X mV
                    c1 = String.format("%4d", entity.getC1());
                    // Solar Panel Voltage Y mV
                    c2 = String.format("%4d", entity.getC2());
                    // Solar Panel Voltage Z mV
                    c3 = String.format("%4d", entity.getC3());
                    // Total Photo Current mA
                    c4 = String.format("%4d", entity.getC4());
                    // Battery Voltage mV
                    c5 = String.format("%4d", entity.getC5());
                    // Total System Current mA
                    c6 = String.format("%4d", entity.getC6());
                    // Boost Converter Temp 1 C
                    c7 = String.format("%4d", twosComplement(entity.getC9()));
                    // Boost Converter Temp 2 C
                    c8 = String.format("%4d", twosComplement(entity.getC10()));
                    // Boost Converter Temp 3 C
                    c9 = String.format("%4d", twosComplement(entity.getC11()));
                    // Battery Temp C
                    c10 = String.format("%4d", twosComplement(entity.getC12()));
                    // Sun Sensor X+
                    c11 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC17().intValue()]);
                    // Sun Sensor Y+
                    c12 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC18().intValue()]);
                    // Sun Sensor Z+
                    c13 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC19().intValue()]);
                    // Solar Panel Temp X+ C
                    c14 = scale(entity.getC20(), -0.2073, 158.239);
                    // Solar Panel Temp X- C
                    c15 = scale(entity.getC21(), -0.2083, 159.227);
                    // Solar Panel Temp Y+ C
                    c16 = scale(entity.getC22(), -0.2076, 158.656);
                    // Solar Panel Temp Y- C
                    c17 = scale(entity.getC23(), -0.2087, 159.045);
                    // 3.3 Bus Voltage mV
                    c18 = scale(entity.getC24(), 4.0, 0.0);
                    // 3.3 Bus Current mA
                    c19 = scale(entity.getC25(), 1.0, 0.0);
                    // 5.0 Bus Voltage mV
                    c20 = scale(entity.getC26(), 6.0, 0.0);
                    // RF Temperature C
                    c21 = scale(entity.getC34(), -0.857, 193.672);
                    // Receive Current mA
                    c22 = String.format("%4d", entity.getC35());
                    // RF Current 3.3V Bus mA
                    c23 = String.format("%4d", entity.getC36());
                    // RF Current 5.0V Bus mA
                    c24 = String.format("%4d", entity.getC37());
                    // PA Device Temperature C
                    c25 = String.format("%4.1f", getPaTemp(entity.getC40()));
                    // PA Bus Current mA
                    c26 = String.format("%4.1f", getPaCurrent(entity.getC41()));
                    // Antenna Temp 0 C
                    c27 = String.format("%5.1f", getAntsTemp(entity.getC42()
                            .intValue()));
                    // Antenna Temp 1 C
                    c28 = String.format("%5.1f", getAntsTemp(entity.getC43()
                            .intValue()));

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5,
                            c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16,
                            c17, c18, c19, c20, c21, c22, c23, c24, c25, c26,
                            c27, c28);
                }
                else {

                    final long timeDiff = satelliteTime.getTime() - tsLong;
                    if (timeDiff > 5000) {
                        // fill in the gaps
                        long gaps = (timeDiff / 5000);
                        for (long i = 1; i < gaps; i++) {
                            Timestamp intervalTime = new Timestamp(tsLong
                                    + (5000 * i));
                            writeRecord(csvOutput, intervalTime, c1, c2, c3,
                                    c4, c5, c6, c7, c8, c9, c10, c11, c12, c13,
                                    c14, c15, c16, c17, c18, c19, c20, c21,
                                    c22, c23, c24, c25, c26, c27, c28);
                        }
                    }

                    // Solar Panel Voltage X mV
                    c1 = String.format("%4d", entity.getC1());
                    // Solar Panel Voltage Y mV
                    c2 = String.format("%4d", entity.getC2());
                    // Solar Panel Voltage Z mV
                    c3 = String.format("%4d", entity.getC3());
                    // Total Photo Current mA
                    c4 = String.format("%4d", entity.getC4());
                    // Battery Voltage mV
                    c5 = String.format("%4d", entity.getC5());
                    // Total System Current mA
                    c6 = String.format("%4d", entity.getC6());
                    // Boost Converter Temp 1 C
                    c7 = String.format("%4d", twosComplement(entity.getC9()));
                    // Boost Converter Temp 2 C
                    c8 = String.format("%4d", twosComplement(entity.getC10()));
                    // Boost Converter Temp 3 C
                    c9 = String.format("%4d", twosComplement(entity.getC11()));
                    // Battery Temp C
                    c10 = String.format("%4d", twosComplement(entity.getC12()));
                    // Sun Sensor X+
                    c11 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC17().intValue()]);
                    // Sun Sensor Y+
                    c12 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC18().intValue()]);
                    // Sun Sensor Z+
                    c13 = String.format("%4.2f", SOL_ILLUMINATION[entity
                            .getC19().intValue()]);
                    // Solar Panel Temp X+ C
                    c14 = scale(entity.getC20(), -0.2073, 158.239);
                    // Solar Panel Temp X- C
                    c15 = scale(entity.getC21(), -0.2083, 159.227);
                    // Solar Panel Temp Y+ C
                    c16 = scale(entity.getC22(), -0.2076, 158.656);
                    // Solar Panel Temp Y- C
                    c17 = scale(entity.getC23(), -0.2087, 159.045);
                    // 3.3 Bus Voltage mV
                    c18 = scale(entity.getC24(), 4.0, 0.0);
                    // 3.3 Bus Current mA
                    c19 = scale(entity.getC25(), 1.0, 0.0);
                    // 5.0 Bus Voltage mV
                    c20 = scale(entity.getC26(), 6.0, 0.0);
                    // RF Temperature C
                    c21 = scale(entity.getC34(), -0.857, 193.672);
                    // Receive Current mA
                    c22 = String.format("%4d", entity.getC35());
                    // RF Current 3.3V Bus mA
                    c23 = String.format("%4d", entity.getC36());
                    // RF Current 5.0V Bus mA
                    c24 = String.format("%4d", entity.getC37());
                    // PA Device Temperature C
                    c25 = String.format("%4.1f", getPaTemp(entity.getC40()));
                    // PA Bus Current mA
                    c26 = String.format("%4.1f", getPaCurrent(entity.getC41()));
                    // Antenna Temp 0 C
                    c27 = String.format("%5.1f", getAntsTemp(entity.getC42()
                            .intValue()));
                    // Antenna Temp 1 C
                    c28 = String.format("%5.1f", getAntsTemp(entity.getC43()
                            .intValue()));

                    writeRecord(csvOutput, satelliteTime, c1, c2, c3, c4, c5,
                            c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16,
                            c17, c18, c19, c20, c21, c22, c23, c24, c25, c26,
                            c27, c28);

                    tsLong = satelliteTime.getTime();
                }
            }

            csvOutput.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private long twosComplement(long value) {
        long channelValue = value;
        if (channelValue >= 128) {
            channelValue = ~channelValue ^ 255;
        }
        return channelValue;
    }

    private void writeRecord(CsvWriter csvOutput, Timestamp satelliteTime,
            String c1, String c2, String c3, String c4, String c5, String c6,
            String c7, String c8, String c9, String c10, String c11,
            String c12, String c13, String c14, String c15, String c16,
            String c17, String c18, String c19, String c20, String c21,
            String c22, String c23, String c24, String c25, String c26,
            String c27, String c28) throws IOException {
        for (int i = 0; i < 31; i++) {

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
                case 15:
                    csvOutput.write(c15);
                    break;
                case 16:
                    csvOutput.write(c16);
                    break;
                case 17:
                    csvOutput.write(c17);
                    break;
                case 18:
                    csvOutput.write(c18);
                    break;
                case 19:
                    csvOutput.write(c19);
                    break;
                case 20:
                    csvOutput.write(c20);
                    break;
                case 21:
                    csvOutput.write(c21);
                    break;
                case 22:
                    csvOutput.write(c22);
                    break;
                case 23:
                    csvOutput.write(c23);
                    break;
                case 24:
                    csvOutput.write(c24);
                    break;
                case 25:
                    csvOutput.write(c25);
                    break;
                case 26:
                    csvOutput.write(c26);
                    break;
                case 27:
                    csvOutput.write(c27);
                    break;
                case 28:
                    csvOutput.write(c28);
                    break;
            }

        }

        csvOutput.endRecord();
    }

    private String scale(final Long adc, final Double multiplier, final Double offset) {
        final double value = (adc * multiplier) + offset;
        return String.format("%6.2f", value);
    }

    private static void setupSunSensors() {
        final double[][] tempToAdc = new double[][] {{5, 0}, {9, 2.158},
                {19, 2.477}, {23, 2.64}, {33, 2.8}, {50, 2.881},
                {56, 2.889}, {100, 3.04}, {133, 3.14}, {200, 3.25},
                {265, 3.346}, {333, 3.475}, {400, 3.58}, {467, 3.69},
                {533, 3.81}, {600, 3.92}, {666, 4.03}, {700, 4.079},
                {732, 4.13}, {800, 4.245}, {866, 4.325}, {933, 4.38},
                {984, 4.42}, {992, 4.5319}, {999, 4.6438},
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
                        final double increment = valueDiff / adcDiff;
                        // calculate the sol value for this adc value
                        final double value = previousPair[1] + (i - previousPair[0])
                                * increment;
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

    private static void setupPaTemps() {
        // Data from adc 79 to 252 measured,
        // 0-79 continues using gradient of last
        // three values, 252 to 255 likewise

        final double[][] tempToAdc = {{87.983, Double.MIN_VALUE},
                {87.983, 0}, {55.3, 79}, {49.6, 91}, {45.3, 103}, {41.1, 115},
                {37.6, 125}, {35.7, 129}, {33.6, 137}, {30.6, 145},
                {27.6, 154}, {25.1, 161}, {22.6, 169}, {20, 176},
                {17.6, 183}, {15.1, 189}, {12.6, 196}, {10, 203},
                {7.5, 208}, {5, 214}, {2.4, 220}, {0, 224},
                {-2.9, 230}, {-5, 233}, {-7.5, 237}, {-10, 241},
                {-12.3, 244}, {-15, 247}, {-20, 252},
                {-22.846, 255}, {-22.846, Double.MAX_VALUE}};

        // calc values for all possible 8bit values
        for (int adc = 0; adc < 256; ++adc) {
            for (int j = 0; j < tempToAdc.length; j++) {
                if (adc != 0 && adc < tempToAdc[j][1]) {
                    final double t1 = tempToAdc[j][0];
                    final double a1 = tempToAdc[j][1];
                    final double diffa = tempToAdc[j - 1][1] - a1;
                    final double difft = tempToAdc[j - 1][0] - t1;
                    final double value = ((adc - a1) * (difft / diffa)) + t1;
                    PA_TEMPS[adc] = value;
                    break;
                }
            }
        }
    }

    private static void setupAntsTemps() {
        // data from ANTS manual, start and end values added
        // to ensure out of range is obvious (-255 or +255)
        final int[][] tempToAdc = new int[][] {{-255, Integer.MIN_VALUE},
                {-255, 2616}, {-50, 2616}, {-49, 2607}, {-48, 2598},
                {-47, 2589}, {-46, 2580}, {-45, 2571}, {-44, 2562},
                {-43, 2553}, {-42, 2543}, {-41, 2533}, {-40, 2522},
                {-39, 2512}, {-38, 2501}, {-37, 2491}, {-36, 2481},
                {-35, 2470}, {-34, 2460}, {-33, 2449}, {-32, 2439},
                {-31, 2429}, {-30, 2418}, {-29, 2408}, {-28, 2397},
                {-27, 2387}, {-26, 2376}, {-25, 2366}, {-24, 2355},
                {-23, 2345}, {-22, 2334}, {-21, 2324}, {-20, 2313},
                {-19, 2302}, {-18, 2292}, {-17, 2281}, {-16, 2271},
                {-15, 2260}, {-14, 2250}, {-13, 2239}, {-12, 2228},
                {-11, 2218}, {-10, 2207}, {-9, 2197}, {-8, 2186},
                {-7, 2175}, {-6, 2164}, {-5, 2154}, {-4, 2143},
                {-3, 2132}, {-2, 2122}, {-1, 2111}, {0, 2100},
                {1, 2089}, {2, 2079}, {3, 2068}, {4, 2057},
                {5, 2047}, {6, 2036}, {7, 2025}, {8, 2014},
                {9, 2004}, {10, 1993}, {11, 1982}, {12, 1971},
                {13, 1961}, {14, 1950}, {15, 1939}, {16, 1928},
                {17, 1918}, {18, 1907}, {19, 1896}, {20, 1885},
                {21, 1874}, {22, 1864}, {23, 1853}, {24, 1842},
                {25, 1831}, {26, 1820}, {27, 1810}, {28, 1799},
                {29, 1788}, {30, 1777}, {31, 1766}, {32, 1756},
                {33, 1745}, {34, 1734}, {35, 1723}, {36, 1712},
                {37, 1701}, {38, 1690}, {39, 1679}, {40, 1668},
                {41, 1657}, {42, 1646}, {43, 1635}, {44, 1624},
                {45, 1613}, {46, 1602}, {47, 1591}, {48, 1580},
                {49, 1569}, {50, 1558}, {51, 1547}, {52, 1536},
                {53, 1525}, {54, 1514}, {55, 1503}, {56, 1492},
                {57, 1481}, {58, 1470}, {59, 1459}, {60, 1448},
                {61, 1436}, {62, 1425}, {63, 1414}, {64, 1403},
                {65, 1391}, {66, 1380}, {67, 1369}, {68, 1358},
                {69, 1346}, {70, 1335}, {71, 1324}, {72, 1313},
                {73, 1301}, {74, 1290}, {75, 1279}, {76, 1268},
                {77, 1257}, {78, 1245}, {79, 1234}, {80, 1223},
                {81, 1212}, {82, 1201}, {83, 1189}, {84, 1178},
                {85, 1167}, {86, 1155}, {87, 1144}, {88, 1133},
                {89, 1122}, {90, 1110}, {91, 1099}, {92, 1088},
                {93, 1076}, {94, 1065}, {95, 1054}, {96, 1042},
                {97, 1031}, {98, 1020}, {99, 1008}, {100, 997},
                {101, 986}, {102, 974}, {103, 963}, {104, 951},
                {105, 940}, {106, 929}, {107, 917}, {108, 906},
                {109, 895}, {110, 883}, {111, 872}, {112, 860},
                {113, 849}, {114, 837}, {115, 826}, {116, 814},
                {117, 803}, {118, 791}, {119, 780}, {120, 769},
                {121, 757}, {122, 745}, {123, 734}, {124, 722},
                {125, 711}, {126, 699}, {127, 688}, {128, 676},
                {129, 665}, {130, 653}, {131, 642}, {132, 630},
                {133, 618}, {134, 607}, {135, 595}, {136, 584},
                {137, 572}, {138, 560}, {139, 549}, {140, 537},
                {141, 525}, {142, 514}, {143, 502}, {144, 490},
                {145, 479}, {146, 467}, {147, 455}, {148, 443},
                {149, 432}, {150, 420}, {255, 420},
                {255, Integer.MIN_VALUE}};
        for (int i = 0; i < 256; ++i) {
            // calc values for all possible 8bit values
            final double adc = (i * 3300.0) / 255.75;
            for (int j = 0; j < tempToAdc.length; j++) {
                if (j != 0 && adc > tempToAdc[j][1]) {
                    final double t1 = tempToAdc[j][0];
                    final double a1 = tempToAdc[j][1];
                    final double diffa = tempToAdc[j - 1][1] - a1;
                    final double difft = tempToAdc[j - 1][0] - t1;
                    ANTS_TEMPS[i] = ((adc - a1) * (difft / diffa)) + t1;
                    break;
                }
            }
        }
    }

    public static final double getAntsTemp(final int value) {
        return ANTS_TEMPS[value];
    }

    public static double getPaPower(final Long value) {
        return 0.005 * Math.pow(value.doubleValue(), 2.0629);
    }

    public static double getPaCurrent(final long value) {
        return (value * 0.5496) + 2.5425;
    }

    private static double getPaTemp(final long value) {
        if (value == 99999 || value == -99999) {
            return 0.0;
        }
        return PA_TEMPS[(int)value];
    }

}
