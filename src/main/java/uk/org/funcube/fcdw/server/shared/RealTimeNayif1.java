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
    private boolean imtqConfigSet;
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

    private boolean antDepl0;
    private boolean antDepl1;
    private boolean antDepl2;
    private boolean antDepl3;

    private long channelCurren5V;

    private long imtqUptime;

    private long dtmfCmdCount;

    private long dtmfLastCmd;

    private boolean dtmfCmdSuccess;

    private boolean dataValidASIB;

    private boolean dataValidEPS;

    private boolean dataValidPA;

    private boolean dataValidRF;

    private boolean dataValidiMTQ;

    private boolean dataValidAntsBusB;

    private boolean dataValidAntsBusA;

    private boolean inEclipseMode;

    private boolean inSafeMode;

    private boolean hardwareABFOnOff;

    private boolean softwareABFOnOff;

    private boolean deploymentWait;

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
        
        panelVolts1 = getBitsAsULong(14, binaryString);
        panelVolts2 = getBitsAsULong(14, binaryString);
        panelVolts3 = getBitsAsULong(14, binaryString);
        batteryVolts = getBitsAsULong(14, binaryString);
        panelCurr1 = getBitsAsULong(10, binaryString);
        panelCurr2 = getBitsAsULong(10, binaryString);
        panelCurr3 = getBitsAsULong(10, binaryString);
        totPhotoCurr = getBitsAsULong(10, binaryString);
        totSystemCurr = getBitsAsULong(10, binaryString);
        rebootCount = getBitsAsULong(8, binaryString);
        boostTemp1 = getBitsAsULong(8, binaryString);
        boostTemp2 = getBitsAsULong(8, binaryString);
        boostTemp3 = getBitsAsULong(8, binaryString);
        batteryTemp = getBitsAsULong(8, binaryString);
        latchUpCount5v = getBitsAsULong(8, binaryString);
        channelCurren5V = getBitsAsULong(8, binaryString);
        resetCause = getBitsAsULong(4, binaryString);
        pptMode = getBitsAsULong(4, binaryString);
        imtqMode = getBitsAsULong(2, binaryString);
        imtqErrorCode = getBitsAsULong(3, binaryString);
        imtqConfigSet = getBooleanBit(binaryString);
        imtqMcuTemp = getBitsAsULong(8, binaryString);
        solXPlus = getBitsAsULong(10, binaryString);
        solXMinus = getBitsAsULong(10, binaryString);
        solYPlus = getBitsAsULong(10, binaryString);
        solYMinus = getBitsAsULong(10, binaryString);
        solZPlus = getBitsAsULong(10, binaryString);
        solZMinus = getBitsAsULong(10, binaryString);
        busVolts3v3 = getBitsAsULong(10, binaryString);
        imtqUptime = getBitsAsULong(20, binaryString);
        busVolts5 = getBitsAsULong(10, binaryString);
        rxDoppler = getBitsAsULong(8, binaryString);
        rxRSSI = getBitsAsULong(8, binaryString);
        rxTemp = getBitsAsULong(8, binaryString);
        rxCurr = getBitsAsULong(8, binaryString);
        txBusCurr3v3 = getBitsAsULong(8, binaryString);
        txBusCurr5v = getBitsAsULong(8, binaryString);
        txRevPwr = getBitsAsULong(8, binaryString);
        txFwdPwr = getBitsAsULong(8, binaryString);
        txTemp = getBitsAsULong(8, binaryString);
        txCurr = getBitsAsULong(8, binaryString);
        antTemp0 = getBitsAsULong(8, binaryString);
        antTemp1 = getBitsAsULong(8, binaryString);
        antDepl0 = getBooleanBit(binaryString);
        antDepl1 = getBooleanBit(binaryString);
        antDepl2 = getBooleanBit(binaryString);
        antDepl3 = getBooleanBit(binaryString);
        final long sequenceNumber = getBitsAsULong(24, binaryString);
        super.setSequenceNumber(sequenceNumber);
        dtmfCmdCount = getBitsAsULong(6, binaryString);
        dtmfLastCmd = getBitsAsULong(5, binaryString);
        dtmfCmdSuccess = getBooleanBit(binaryString);
        dataValidASIB = getBooleanBit(binaryString);
        dataValidEPS = getBooleanBit(binaryString);
        dataValidPA = getBooleanBit(binaryString);
        dataValidRF = getBooleanBit(binaryString);
        dataValidiMTQ = getBooleanBit(binaryString);
        dataValidAntsBusB = getBooleanBit(binaryString);
        dataValidAntsBusA = getBooleanBit(binaryString);
        inEclipseMode = getBooleanBit(binaryString);
        inSafeMode = getBooleanBit(binaryString);
        hardwareABFOnOff = getBooleanBit(binaryString);
        softwareABFOnOff = getBooleanBit(binaryString);
        deploymentWait = getBooleanBit(binaryString);
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

    public final boolean getImtqConfigSet() {
        return imtqConfigSet;
    }

    public final void setImtqConfigSet(boolean imtqConfigSet) {
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

    public final boolean getAntDepl0() {
        return antDepl0;
    }

    public final void setAntDepl0(boolean antDepl0) {
        this.antDepl0 = antDepl0;
    }

    public final boolean getAntDepl1() {
        return antDepl1;
    }

    public final void setAntDepl1(boolean antDepl1) {
        this.antDepl1 = antDepl1;
    }

    public final boolean getAntDepl2() {
        return antDepl2;
    }

    public final void setAntDepl2(boolean antDepl2) {
        this.antDepl2 = antDepl2;
    }

    public final boolean getAntDepl3() {
        return antDepl3;
    }

    public final void setAntDepl3(boolean antDepl3) {
        this.antDepl3 = antDepl3;
    }
    
    private long getBitsAsULong(int length, String binaryString) {
        final long value = Long.parseLong(binaryString.substring(stringPos, stringPos + length), 2);
        stringPos += length;
        return value;
    }
    
    private boolean getBooleanBit(String binaryString) {
        final boolean value = (binaryString.substring(stringPos, stringPos + 1).equals("1"));
        stringPos += 1;
        return value;
    }

    public final long getChannelCurren5V() {
        return channelCurren5V;
    }

    public final void setChannelCurren5V(long channelCurren5V) {
        this.channelCurren5V = channelCurren5V;
    }

    public final long getImtqUptime() {
        return imtqUptime;
    }

    public final void setImtqUptime(long imtqUptime) {
        this.imtqUptime = imtqUptime;
    }

    public final long getDtmfCmdCount() {
        return dtmfCmdCount;
    }

    public final void setDtmfCmdCount(long dtmfCmdCount) {
        this.dtmfCmdCount = dtmfCmdCount;
    }

    public final long getDtmfLastCmd() {
        return dtmfLastCmd;
    }

    public final void setDtmfLastCmd(long dtmfLastCmd) {
        this.dtmfLastCmd = dtmfLastCmd;
    }

    public final boolean getDtmfCmdSuccess() {
        return dtmfCmdSuccess;
    }

    public final void setDtmfCmdSuccess(boolean dtmfCmdSuccess) {
        this.dtmfCmdSuccess = dtmfCmdSuccess;
    }

    public final boolean getDataValidASIB() {
        return dataValidASIB;
    }

    public final void setDataValidASIB(boolean dataValidASIB) {
        this.dataValidASIB = dataValidASIB;
    }

    public final boolean getDataValidEPS() {
        return dataValidEPS;
    }

    public final void setDataValidEPS(boolean dataValidEPS) {
        this.dataValidEPS = dataValidEPS;
    }

    public final boolean getDataValidPA() {
        return dataValidPA;
    }

    public final void setDataValidPA(boolean dataValidPA) {
        this.dataValidPA = dataValidPA;
    }

    public final boolean getDataValidRF() {
        return dataValidRF;
    }

    public final void setDataValidRF(boolean dataValidRF) {
        this.dataValidRF = dataValidRF;
    }

    public final boolean getDataValidiMTQ() {
        return dataValidiMTQ;
    }

    public final void setDataValidiMTQ(boolean dataValidiMTQ) {
        this.dataValidiMTQ = dataValidiMTQ;
    }

    public final boolean getDataValidAntsBusB() {
        return dataValidAntsBusB;
    }

    public final void setDataValidAntsBusB(boolean dataValidAntsBusB) {
        this.dataValidAntsBusB = dataValidAntsBusB;
    }

    public final boolean getDataValidAntsBusA() {
        return dataValidAntsBusA;
    }

    public final void setDataValidAntsBusA(boolean dataValidAntsBusA) {
        this.dataValidAntsBusA = dataValidAntsBusA;
    }

    public final boolean getInEclipseMode() {
        return inEclipseMode;
    }

    public final void setInEclipseMode(boolean inEclipseMode) {
        this.inEclipseMode = inEclipseMode;
    }

    public final boolean getInSafeMode() {
        return inSafeMode;
    }

    public final void setInSafeMode(boolean inSafeMode) {
        this.inSafeMode = inSafeMode;
    }

    public final boolean getHardwareABFOnOff() {
        return hardwareABFOnOff;
    }

    public final void setHardwareABFOnOff(boolean hardwareABFOnOff) {
        this.hardwareABFOnOff = hardwareABFOnOff;
    }

    public final boolean getSoftwareABFOnOff() {
        return softwareABFOnOff;
    }

    public final void setSoftwareABFOnOff(boolean softwareABFOnOff) {
        this.softwareABFOnOff = softwareABFOnOff;
    }

    public final boolean getDeploymentWait() {
        return deploymentWait;
    }

    public final void setDeploymentWait(boolean deploymentWait) {
        this.deploymentWait = deploymentWait;
    }

}
