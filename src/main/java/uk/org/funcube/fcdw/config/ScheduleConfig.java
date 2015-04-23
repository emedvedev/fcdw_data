/*
	This file is part of the FUNcube Data Warehouse
	
	Copyright 2013,2014 (c) David A.Johnson, G4DPZ, AMSAT-UK

    The FUNcube Data Warehouse is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    The FUNcube Data Warehouse is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with The FUNcube Data Warehouse.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.org.funcube.fcdw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import uk.org.funcube.fcdw.server.extract.csv.HighRes24CsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.HighResCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.RealTimeCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.WodCsvExtractor;
import uk.org.funcube.fcdw.server.processor.DataProcessor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessor;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessor;
import uk.org.funcube.fcdw.server.processor.TleProcessor;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessor;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    HighResolutionProcessor highDefinitionProcessor;

    @Autowired
    WholeOrbitDataProcessor wholeOrbitDataProcessor;

    @Autowired
    FitterMessageProcessor fitterMessageProcessor;

    @Autowired
    private WodCsvExtractor wodCsvExtractor;

    @Autowired
    private HighResCsvExtractor highResCsvExtractor;

    @Autowired
    private HighRes24CsvExtractor highRes24CsvExtractor;

    @Autowired
    private RealTimeCsvExtractor realTimeCsvExtractor;

    @Autowired
    private DataProcessor dataProcessor;

    @Autowired
    private TleProcessor tleProcessor;

    @Scheduled(initialDelay = 30000, fixedRate = 120000)
    public void highDefinitionTask() {
        highDefinitionProcessor.process(0L);
        // highDefinitionProcessor.process(1L);
        highDefinitionProcessor.process(2L);
        // highDefinitionProcessor.process(3L);
    }

    @Scheduled(initialDelay = 30000, fixedRate = 86400000)
    public void tleProcessorTask() {
        tleProcessor.process();
    }

    @Scheduled(initialDelay = 30000, fixedRate = 86400000)
    public void sha2ProcessorTask() {
        // dataProcessor.processSha2();
    }

    @Scheduled(initialDelay = 60000, fixedRate = 120000)
    public void wholeOrbitDataTask() {
        wholeOrbitDataProcessor.process(0L);
        // wholeOrbitDataProcessor.process(1L);
        wholeOrbitDataProcessor.process(2L);
        // wholeOrbitDataProcessor.process(3L);
    }

    @Scheduled(initialDelay = 90000, fixedRate = 120000)
    public void processFitterMessageTask() {
        fitterMessageProcessor.process(0L);
        fitterMessageProcessor.process(1L);
        fitterMessageProcessor.process(2L);
        fitterMessageProcessor.process(3L);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void truncateFitterMessageTask() {
        fitterMessageProcessor.truncate(0L);
        fitterMessageProcessor.truncate(1L);
        fitterMessageProcessor.truncate(2L);
        fitterMessageProcessor.truncate(3L);
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    public void wodCsvExtractorTask() {
        wodCsvExtractor.extract(2L);
    }

    @Scheduled(cron = "0 5 * * * ?")
    public void highResCsvExtractorTask() {
        highResCsvExtractor.extract(2L);
    }

    @Scheduled(cron = "0 55 12 * * ?")
    public void highRes24CsvExtractorTask() {
        highRes24CsvExtractor.extract(2L);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void wodWeeklyCsvExtractorTask() {
        wodCsvExtractor.extractWeekly(2L);
    }

    @Scheduled(cron = "0 10 * * * ?")
    public void realTimeCsvExtractorTask() {
        realTimeCsvExtractor.extract(2L);
    }

}
