package uk.org.funcube.fcdw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import uk.org.funcube.fcdw.server.processor.FitterMessageProcessor;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessor;
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
	
	@Scheduled(initialDelay=30000, fixedRate=120000)
	public void highDefinitionTask() {
		highDefinitionProcessor.process(0L);
		highDefinitionProcessor.process(1L);
		highDefinitionProcessor.process(2L);
		highDefinitionProcessor.process(3L);
	}
	
	@Scheduled(initialDelay=60000, fixedRate=120000)
	public void wholeOrbitDataTask() {
		wholeOrbitDataProcessor.process(0L);
		wholeOrbitDataProcessor.process(1L);
		wholeOrbitDataProcessor.process(2L);
		wholeOrbitDataProcessor.process(3L);
	}
	
	@Scheduled(initialDelay=90000, fixedRate=120000)
	public void fitterMessageTask() {
		fitterMessageProcessor.process(0L);
		fitterMessageProcessor.process(1L);
		fitterMessageProcessor.process(2L);
		fitterMessageProcessor.process(3L);
	}

}
