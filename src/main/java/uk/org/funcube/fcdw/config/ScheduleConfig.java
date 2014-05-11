// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
	private RealTimeCsvExtractor  realTimeCsvExtractor;
	
	@Autowired
	private DataProcessor dataProcessor;
	
	@Autowired
	private TleProcessor tleProcessor;
	
	@Scheduled(initialDelay=30000, fixedRate=120000)
	public void highDefinitionTask() {
		highDefinitionProcessor.process(0L);
		highDefinitionProcessor.process(1L);
		highDefinitionProcessor.process(2L);
		highDefinitionProcessor.process(3L);
	}
	
	@Scheduled(initialDelay=30000, fixedRate=86400000)
	public void downloadTleTask() {
		tleProcessor.process();
	}
	
	@Scheduled(initialDelay=60000, fixedRate=120000)
	public void wholeOrbitDataTask() {
		wholeOrbitDataProcessor.process(0L);
		wholeOrbitDataProcessor.process(1L);
		wholeOrbitDataProcessor.process(2L);
		wholeOrbitDataProcessor.process(3L);
	}
	
	@Scheduled(initialDelay=90000, fixedRate=120000)
	public void processFitterMessageTask() {
		fitterMessageProcessor.process(0L);
		fitterMessageProcessor.process(1L);
		fitterMessageProcessor.process(2L);
		fitterMessageProcessor.process(3L);
	}
	
	@Scheduled(cron="0 0 0 * * ?")
	public void truncateFitterMessageTask() {
		fitterMessageProcessor.truncate(0L);
		fitterMessageProcessor.truncate(1L);
		fitterMessageProcessor.truncate(2L);
		fitterMessageProcessor.truncate(3L);
	}
	
	@Scheduled(cron="0 0 */2 * * ?")
	public void wodCsvExtractorTask() {
		wodCsvExtractor.extract(2L);
	}
	
	@Scheduled(cron="0 5 * * * ?")
	public void highResCsvExtractorTask() {
		highResCsvExtractor.extract(2L);
	}
	
	@Scheduled(cron="*/5 * * * * ?")
	public void hexDataTask() {
		dataProcessor.processHexFrame();
	}
	
	@Scheduled(cron="0 10 * * * ?")
	public void realTimeCsvExtractorTask() {
		realTimeCsvExtractor.extract(2L);
	}

}
