// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import uk.org.funcube.fcdw.server.extract.csv.RealTimeCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.HighResCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.WodCsvExtractor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessorImpl;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessor;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessorImpl;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessor;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessorImpl;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;
import uk.org.funcube.fcdw.service.PredictorService;

@EnableWebMvc
@ComponentScan(basePackages = { "uk.org.funcube.fcdw" })
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	Clock clock() {
		return new UTCClock();
	}

	@Bean
	HighResolutionProcessor highDefinitionProcessor() {
		return new HighResolutionProcessorImpl();
	}

	@Bean
	WholeOrbitDataProcessor wholeOrbitDataProcessor() {
		return new WholeOrbitDataProcessorImpl();
	}

	@Bean
	FitterMessageProcessor fitterMessageProcessor() {
		return new FitterMessageProcessorImpl();
	}
	
	@Bean
	WodCsvExtractor wodCsvExtractor() {
		return new WodCsvExtractor();
	}
	
	@Bean
	HighResCsvExtractor highResCsvExtractor() {
		return new HighResCsvExtractor();
	}
	
	@Bean
	RealTimeCsvExtractor realTimeCsvExtractor() {
		return new RealTimeCsvExtractor();
	}
	
	@Bean
	TleProcessor tleProcessor() {
		return new TleProcessor();
	}
	
	@Bean
	PredictorService predictorService() {
		return new PredictorService();
	}

	@Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public UrlBasedViewResolver configureUrlBasedViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		return resolver;

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/scripts/**").addResourceLocations(
				"/scripts/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/images/**").addResourceLocations(
				"/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}

}
