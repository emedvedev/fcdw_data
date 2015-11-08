// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.server.processor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.jdbc.Expectation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.staticmock.AbstractMethodMockingControl.Expectations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.org.funcube.fcdw.dao.EpochDao;
import uk.org.funcube.fcdw.dao.UserDao;
import uk.org.funcube.fcdw.domain.EpochEntity;
import uk.org.funcube.fcdw.server.processor.UserHexString;

/**
 * @author g4dpz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class DataProcessorTest {
    
    private final String NAYIF1_HEX = "08cad8288277de60009010c00718f14306860646080040c40100400004100001fc475b00ba9ffd9f" +
                                      "2584ee32faefd8cceff813351302e0bfb336ffff62eaa5975ae9925600011030010441001d7001e9" +
                                      "3627ffc4ea0f9762e9a5564a01924000041000017041e91d0f019436ffff62eaa5974aa992520001" +
                                      "1040010441001d7001e93627ffa5eadf975ea9a5524a01914000041000017041e91d1b019536dfff" +
                                      "5eeaa5964aa9915200011030010441001d7001e9360fffd2eacf965ea9a5524a0191300004100001" +
                                      "8041e91d1b018436dfff5eeaa5963aa9915200011030010441001d8001e9361bffa4eacf965ea9a5" +
                                      "523a01913000041000018041e91d2701";
    
    private UserDao userDaoMock = Mockito.mock(UserDao.class);
    private EpochDao epochDaoMock = Mockito.mock(EpochDao.class);
    private EpochEntity[] epochArray = new EpochEntity[] {new EpochEntity(8L, 0L, new Timestamp(Calendar.getInstance().getTimeInMillis()))};
    private List<EpochEntity> epochs = Arrays.asList(epochArray);
    
    private DataProcessor dataProcessor = new DataProcessor(epochDaoMock);
    
    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(userDaoMock);
        Mockito.reset(epochDaoMock);
    }
    
    @Test
    public void processNajif1Data() {
        
        when(epochDaoMock.findAll()).thenReturn(epochs);

        final UserHexString userHexString = new UserHexString(null, swapBytes(NAYIF1_HEX), Calendar.getInstance().getTime());
        
        dataProcessor.processHexFrame(userHexString);
        
    }

    private String swapBytes(final String origHex) {
        final char[] temp = new char[origHex.length()];
        int i = 0;
        for (char c : origHex.toCharArray()) {
            switch(i % 4) {
                case 0:
                    temp[i+2] = c;
                    break;
                case 1:
                    temp[i+2] = c;
                    break;
                case 2:
                    temp[i-2] = c;
                    break;
                case 3:
                    temp[i-2] = c;
                    break;
            }
            i++; 
        }
        return new String(temp);
    }
    

}
