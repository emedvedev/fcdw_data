package uk.org.funcube.fcdw.server.shared;

import java.util.Date;

import org.jboss.logging.Logger;

public class RealTimeNayif1 extends RealTime {

    private static Logger LOG = Logger.getLogger(RealTimeNayif1.class.getName());
    
    private long panelVolts1;
    private long panelVolts2;
    private long panelVolts3;
    private long batteryVolts;
    
    private long panelCurr1;
    private long panelCurr2;
    private long panelCurr3;
    private long totPhotoCurr;
    private long totSystemCurr;
    
    private int stringPos = 0;

    private long rebootCount;
    private long epsErrorCount;
    private long boostTemp1;
    private long boostTemp2;
    private long boostTemp3;
    private long batteryTemp;
    private long latchUpCount5v;
    private long latchUpCount3v3;
    private long latchUpCount5vSW;
    private long resetCause;
    private long pptMode;

    private long imtqMode;
    private long imtqErrorCode;
    private long imtqConfigSet;
    private long imtqMcuTemp;

    private long solXPlus;
    private long solXMinus;
    private long solYPlus;
    private long solYMinus;
    private long solZPlus;
    private long solZMinus;

    private long busVolts3v3;
    private long busCurr3v3;
    private long busVolts5;
    private long busCurr5;

    private long rxDoppler;
    private long rxRSSI;
    private long rxTemp;
    private long rxCurr;
    private long txBusCurr3v3;
    private long txBusCurr5v;
    private long txRevPwr;
    private long txFwdPwr;
    private long txTemp;
    private long txCurr;
    private long antTemp0;
    private long antTemp1;

    private long antDepl0;
    private long antDepl1;
    private long antDepl2;
    private long antDepl3;

    public RealTimeNayif1() {
    }

    public RealTimeNayif1(int satelliteId, int frameType, Date createdDate, String binaryString) {
        super();
        setSatelliteId(satelliteId);
        setFrameType(frameType);
        setCreatedDate(createdDate);
        parseBinary(binaryString);
    }

    private void parseBinary(String binaryString) {
        
        /*
        14  Photo voltage 1
        14  Photo voltage 2
        14  Photo voltage 3
        14  Battery voltage
        */
        panelVolts1 = get14BitsAsULong(binaryString);
        panelVolts2 = get14BitsAsULong(binaryString);
        panelVolts3 = get14BitsAsULong(binaryString);
        batteryVolts = get14BitsAsULong(binaryString);
        
        /*
        10  Photo current 1
        10  Photo current 2
        10  Photo current 3
        10  Total photo current
        10  Total system current

        */
        panelCurr1 = get10BitsAsULong(binaryString);
        panelCurr2 = get10BitsAsULong(binaryString);
        panelCurr3 = get10BitsAsULong(binaryString);
        totPhotoCurr = get10BitsAsULong(binaryString);
        totSystemCurr = get10BitsAsULong(binaryString);
        
        /*
        8   Reboot count
        8   EPS software errors
        8   Boost converter temp 1
        8   Boost converter temp 2
        8   Boost converter temp 3
        8   Battery temp
        8   Latch up count 5v1
        8   Latch up count 3.3v1
        8   Latch up count 5v SW1
        8   Reset cause
        8   Power point tracking mode
        */
        
        rebootCount = get8BitsAsULong(binaryString);
        epsErrorCount = get8BitsAsULong(binaryString);
        boostTemp1 = get8BitsAsULong(binaryString);
        boostTemp2 = get8BitsAsULong(binaryString);
        boostTemp3 = get8BitsAsULong(binaryString);
        batteryTemp = get8BitsAsULong(binaryString);
        latchUpCount5v = get8BitsAsULong(binaryString);
        latchUpCount3v3 = get8BitsAsULong(binaryString);
        latchUpCount5vSW = get8BitsAsULong(binaryString);
        resetCause = get8BitsAsULong(binaryString);
        pptMode = get8BitsAsULong(binaryString);
        
        /*
        2   iMTQ mode
        3   iMTQ error code
        1   iMTQ configuration set flag
        8   iMTQ MCU temperature
         */
        imtqMode = get2BitsAsULong(binaryString);
        imtqErrorCode = get3BitsAsULong(binaryString);
        imtqConfigSet = get1BitsAsULong(binaryString);
        imtqMcuTemp = get8BitsAsULong(binaryString);
            
        /*
        10  Sun Sensor X+
        10  Sun Sensor X-
        10  Sun Sensor Y+
        10  Sun Sensor Y-
        10  Sun Sensor Z+
        10  Sun Sensor Z-
        */
        solXPlus = get10BitsAsULong(binaryString);
        solXMinus = get10BitsAsULong(binaryString);
        solYPlus = get10BitsAsULong(binaryString);
        solYMinus = get10BitsAsULong(binaryString);
        solZPlus = get10BitsAsULong(binaryString);
        solZMinus = get10BitsAsULong(binaryString);
        
        /*
        10  3.3 bus voltage
        10  3.3 bus current
        10  5.0 bus current
        10  5.0 bus voltage 
        */

        busVolts3v3 = get10BitsAsULong(binaryString);
        busCurr3v3 = get10BitsAsULong(binaryString);
        busVolts5 = get10BitsAsULong(binaryString);
        busCurr5 = get10BitsAsULong(binaryString);
        
        /*
        8   Receiver Doppler
        8   Receiver RSSI
        8   Temperature
        8   Receive current
        8   Transmit current 3.3V bus
        8   Transmit current 5.0V bus
        8   Reverse power
        8   Forward power
        8   Board temperature
        8   Board current
        8   Antenna temp 0
        8   Antenna temp 1
        */
        
        rxDoppler = get8BitsAsULong(binaryString);
        rxRSSI = get8BitsAsULong(binaryString);
        rxTemp = get8BitsAsULong(binaryString);
        rxCurr = get8BitsAsULong(binaryString);
        txBusCurr3v3 = get8BitsAsULong(binaryString);
        txBusCurr5v = get8BitsAsULong(binaryString);
        txRevPwr = get8BitsAsULong(binaryString);
        txFwdPwr = get8BitsAsULong(binaryString);
        txTemp = get8BitsAsULong(binaryString);
        txCurr = get8BitsAsULong(binaryString);
        antTemp0 = get8BitsAsULong(binaryString);
        antTemp1 = get8BitsAsULong(binaryString);
        
        /*
        1   Antenna deployment 0
        1   Antenna deployment 1
        1   Antenna deployment 2
        1   Antenna deployment 3
        */
        antDepl0 = get1BitsAsULong(binaryString);
        antDepl1 = get1BitsAsULong(binaryString);
        antDepl2 = get1BitsAsULong(binaryString);
        antDepl3 = get1BitsAsULong(binaryString);
        
        /*
        24  Sequence number
        */
        super.setSequenceNumber(get24BitsAsULong(binaryString));
        /*
        6   DTMF command count
        5   DTMF last command
        1   DTMF command success
        1   Data valid ASIB
        1   Data valid EPS
        1   Data valid PA
        1   Data valid RF
        1   Data valid iMTQ
        1   Data valid ANTS bus-B
        1   Data valid ANTS bus-A
        1   In eclipse mode
        1   In safe mode
        1   Hardware ABF On/Off
        1   Software ABF On/Off
        1   Deployment wait at next boot
        */

        
    }
    
    @Override
    public Boolean isEclipsed() {
        return panelCurr1 == 0 && panelCurr2 == 0 && panelCurr3 == 0;
    }

    public final long getPanelVolts1() {
        return panelVolts1;
    }

    public final void setPanelVolts1(long panelVolts1) {
        this.panelVolts1 = panelVolts1;
    }

    public final long getPanelVolts2() {
        return panelVolts2;
    }

    public final void setPanelVolts2(long panelVolts2) {
        this.panelVolts2 = panelVolts2;
    }

    public final long getPanelVolts3() {
        return panelVolts3;
    }

    public final void setPanelVolts3(long panelVolts3) {
        this.panelVolts3 = panelVolts3;
    }

    public final long getBatteryVolts() {
        return batteryVolts;
    }

    public final void setBatteryVolts(long batteryVolts) {
        this.batteryVolts = batteryVolts;
    }

    public final long getPanelCurr1() {
        return panelCurr1;
    }

    public final void setPanelCurr1(long panelCurr1) {
        this.panelCurr1 = panelCurr1;
    }

    public final long getPanelCurr2() {
        return panelCurr2;
    }

    public final void setPanelCurr2(long panelCurr2) {
        this.panelCurr2 = panelCurr2;
    }

    public final long getPanelCurr3() {
        return panelCurr3;
    }

    public final void setPanelCurr3(long panelCurr3) {
        this.panelCurr3 = panelCurr3;
    }

    public final long getTotPhotoCurr() {
        return totPhotoCurr;
    }

    public final void setTotPhotoCurr(long totPhotoCurr) {
        this.totPhotoCurr = totPhotoCurr;
    }

    public final long getTotSystemCurr() {
        return totSystemCurr;
    }

    public final void setTotSystemCurr(long totSystemCurr) {
        this.totSystemCurr = totSystemCurr;
    }

    public final long getRebootCount() {
        return rebootCount;
    }

    public final void setRebootCount(long rebootCount) {
        this.rebootCount = rebootCount;
    }

    public final long getEpsErrorCount() {
        return epsErrorCount;
    }

    public final void setEpsErrorCount(long epsErrorCount) {
        this.epsErrorCount = epsErrorCount;
    }

    public final long getBoostTemp1() {
        return boostTemp1;
    }

    public final void setBoostTemp1(long boostTemp1) {
        this.boostTemp1 = boostTemp1;
    }

    public final long getBoostTemp2() {
        return boostTemp2;
    }

    public final void setBoostTemp2(long boostTemp2) {
        this.boostTemp2 = boostTemp2;
    }

    public final long getBoostTemp3() {
        return boostTemp3;
    }

    public final void setBoostTemp3(long boostTemp3) {
        this.boostTemp3 = boostTemp3;
    }

    public final long getBatteryTemp() {
        return batteryTemp;
    }

    public final void setBatteryTemp(long batteryTemp) {
        this.batteryTemp = batteryTemp;
    }

    public final long getLatchUpCount5v() {
        return latchUpCount5v;
    }

    public final void setLatchUpCount5v(long latchUpCount5v) {
        this.latchUpCount5v = latchUpCount5v;
    }

    public final long getLatchUpCount3v3() {
        return latchUpCount3v3;
    }

    public final void setLatchUpCount3v3(long latchUpCount3v3) {
        this.latchUpCount3v3 = latchUpCount3v3;
    }

    public final long getLatchUpCount5vSW() {
        return latchUpCount5vSW;
    }

    public final void setLatchUpCount5vSW(long latchUpCount5vSW) {
        this.latchUpCount5vSW = latchUpCount5vSW;
    }

    public final long getResetCause() {
        return resetCause;
    }

    public final void setResetCause(long resetCause) {
        this.resetCause = resetCause;
    }

    public final long getPptMode() {
        return pptMode;
    }

    public final void setPptMode(long pptMode) {
        this.pptMode = pptMode;
    }

    public final long getImtqMode() {
        return imtqMode;
    }

    public final void setImtqMode(long imtqMode) {
        this.imtqMode = imtqMode;
    }

    public final long getImtqErrorCode() {
        return imtqErrorCode;
    }

    public final void setImtqErrorCode(long imtqErrorCode) {
        this.imtqErrorCode = imtqErrorCode;
    }

    public final long getImtqConfigSet() {
        return imtqConfigSet;
    }

    public final void setImtqConfigSet(long imtqConfigSet) {
        this.imtqConfigSet = imtqConfigSet;
    }

    public final long getImtqMcuTemp() {
        return imtqMcuTemp;
    }

    public final void setImtqMcuTemp(long imtqMcuTemp) {
        this.imtqMcuTemp = imtqMcuTemp;
    }

    public final long getSolXPlus() {
        return solXPlus;
    }

    public final void setSolXPlus(long solXPlus) {
        this.solXPlus = solXPlus;
    }

    public final long getSolXMinus() {
        return solXMinus;
    }

    public final void setSolXMinus(long solXMinus) {
        this.solXMinus = solXMinus;
    }

    public final long getSolYPlus() {
        return solYPlus;
    }

    public final void setSolYPlus(long solYPlus) {
        this.solYPlus = solYPlus;
    }

    public final long getSolYMinus() {
        return solYMinus;
    }

    public final void setSolYMinus(long solYMinus) {
        this.solYMinus = solYMinus;
    }

    public final long getSolZPlus() {
        return solZPlus;
    }

    public final void setSolZPlus(long solZPlus) {
        this.solZPlus = solZPlus;
    }

    public final long getSolZMinus() {
        return solZMinus;
    }

    public final void setSolZMinus(long solZMinus) {
        this.solZMinus = solZMinus;
    }

    public final long getBusVolts3v3() {
        return busVolts3v3;
    }

    public final void setBusVolts3v3(long busVolts3v3) {
        this.busVolts3v3 = busVolts3v3;
    }

    public final long getBusCurr3v3() {
        return busCurr3v3;
    }

    public final void setBusCurr3v3(long busCurr3v3) {
        this.busCurr3v3 = busCurr3v3;
    }

    public final long getBusVolts5() {
        return busVolts5;
    }

    public final void setBusVolts5(long busVolts5) {
        this.busVolts5 = busVolts5;
    }

    public final long getBusCurr5() {
        return busCurr5;
    }

    public final void setBusCurr5(long busCurr5) {
        this.busCurr5 = busCurr5;
    }

    public final long getRxDoppler() {
        return rxDoppler;
    }

    public final void setRxDoppler(long rxDoppler) {
        this.rxDoppler = rxDoppler;
    }

    public final long getRxRSSI() {
        return rxRSSI;
    }

    public final void setRxRSSI(long rxRSSI) {
        this.rxRSSI = rxRSSI;
    }

    public final long getRxTemp() {
        return rxTemp;
    }

    public final void setRxTemp(long rxTemp) {
        this.rxTemp = rxTemp;
    }

    public final long getRxCurr() {
        return rxCurr;
    }

    public final void setRxCurr(long rxCurr) {
        this.rxCurr = rxCurr;
    }

    public final long getTxBusCurr3v3() {
        return txBusCurr3v3;
    }

    public final void setTxBusCurr3v3(long txBusCurr3v3) {
        this.txBusCurr3v3 = txBusCurr3v3;
    }

    public final long getTxBusCurr5v() {
        return txBusCurr5v;
    }

    public final void setTxBusCurr5v(long txBusCurr5v) {
        this.txBusCurr5v = txBusCurr5v;
    }

    public final long getTxRevPwr() {
        return txRevPwr;
    }

    public final void setTxRevPwr(long txRevPwr) {
        this.txRevPwr = txRevPwr;
    }

    public final long getTxFwdPwr() {
        return txFwdPwr;
    }

    public final void setTxFwdPwr(long txFwdPwr) {
        this.txFwdPwr = txFwdPwr;
    }

    public final long getTxTemp() {
        return txTemp;
    }

    public final void setTxTemp(long txTemp) {
        this.txTemp = txTemp;
    }

    public final long getTxCurr() {
        return txCurr;
    }

    public final void setTxCurr(long txCurr) {
        this.txCurr = txCurr;
    }

    public final long getAntTemp0() {
        return antTemp0;
    }

    public final void setAntTemp0(long antTemp0) {
        this.antTemp0 = antTemp0;
    }

    public final long getAntTemp1() {
        return antTemp1;
    }

    public final void setAntTemp1(long antTemp1) {
        this.antTemp1 = antTemp1;
    }

    public final long getAntDepl0() {
        return antDepl0;
    }

    public final void setAntDepl0(long antDepl0) {
        this.antDepl0 = antDepl0;
    }

    public final long getAntDepl1() {
        return antDepl1;
    }

    public final void setAntDepl1(long antDepl1) {
        this.antDepl1 = antDepl1;
    }

    public final long getAntDepl2() {
        return antDepl2;
    }

    public final void setAntDepl2(long antDepl2) {
        this.antDepl2 = antDepl2;
    }

    public final long getAntDepl3() {
        return antDepl3;
    }

    public final void setAntDepl3(long antDepl3) {
        this.antDepl3 = antDepl3;
    }

    private long get1BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 1), 2);
        stringPos += 1;
        return value;
    }

    private long get2BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 2), 2);
        stringPos += 2;
        return value;
    }

    private long get3BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 3), 2);
        stringPos += 3;
        return value;
    }

    private long get8BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 8), 2);
        stringPos += 8;
        return value;
    }

    private long get10BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 10), 2);
        stringPos += 10;
        return value;
    }
    
    private long get14BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 14), 2);
        stringPos += 14;
        return value;
    }

    private long get24BitsAsULong(String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + 24), 2);
        stringPos += 24;
        return value;
    }

}
