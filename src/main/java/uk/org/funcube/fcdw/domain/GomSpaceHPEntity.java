// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.jboss.logging.Logger;

import uk.org.funcube.fcdw.server.processor.DataProcessor;

@Entity
@DiscriminatorValue("gomSpace")
public class GomSpaceHPEntity extends HighResolutionEntity {

    private static Logger LOG = Logger.getLogger(GomSpaceHPEntity.class.getName());

    public GomSpaceHPEntity() {
        super();
    }

    public GomSpaceHPEntity(long satelliteId, long sequenceNumber, String binaryDataString, Date createdDate) {
        setSatelliteId(satelliteId);
        setCreatedDate(createdDate);
        setSequenceNumber(sequenceNumber);

        int pos = 0;

        for (; pos < 50; pos += 10) {
            final String substring = binaryDataString.substring(pos, pos + 10);
            final long value = Long.parseLong(
                    substring, 2);
            switch (pos / 10) {
                case 0:
                    setC1(value);
                    break;
                case 1:
                    setC2(value);
                    break;
                case 2:
                    setC3(value);
                    break;
                case 3:
                    setC4(value);
                    break;
                case 4:
                    setC5(value);
                    break;
            }
        }

        int start = pos;

        for (; pos < 80; pos += 15) {
            final String substring = binaryDataString.substring(pos, pos + 15);
            final long value = Long.parseLong(
                    substring, 2);
            switch ((pos - start) / 15) {
                case 0:
                    setC6(value);
                    break;
                case 1:
                    setC7(value);
                    break;
            }
        }
    }

    public GomSpaceHPEntity(
            long c1, long c2, long c3, long c4, long c5, long c6, long c7) {
        setC1(c1);
        setC2(c2);
        setC3(c3);
        setC4(c4);
        setC5(c5);
        setC6(c6);
        setC7(c7);
    }

    public Long getsunSensorPlusX() {
        return getC1();
    }

    public Long getsunSensorMinusX() {
        return getC2();
    }

    public Long getSunSensorPlusY() {
        return getC3();
    }

    public Long getSunSensorMinusY() {
        return getC4();
    }

    public Long getSunSensorMinusZ() {
        return getC5();
    }

    public Long getPhotoCurrent() {
        return getC6();
    }

    public Long getBatteryVoltage() {
        return getC7();
    }
}
