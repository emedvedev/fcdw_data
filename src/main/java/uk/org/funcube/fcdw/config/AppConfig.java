/*
	This file is part of the FUNcube Data Warehouse

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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import uk.org.funcube.fcdw.server.extract.csv.HighRes24CsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.HighResCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.RealTimeCsvExtractor;
import uk.org.funcube.fcdw.server.extract.csv.WodCsvExtractor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessorImpl;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessor;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessorImpl;
import uk.org.funcube.fcdw.server.processor.TleProcessor;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessor;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessorImpl;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;
import uk.org.funcube.fcdw.service.PredictorService;

@EnableWebMvc
@ComponentScan(basePackages = { "uk.org.funcube.fcdw" })
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	public AppConfig() {
		super();
	}

	@Override
	public void configureDefaultServletHandling(
			final DefaultServletHandlerConfigurer configurer) {
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
	HighRes24CsvExtractor highRes24CsvExtractor() {
		return new HighRes24CsvExtractor();
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
		final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public UrlBasedViewResolver configureUrlBasedViewResolver() {
		final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
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
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/scripts/**").addResourceLocations(
				"/scripts/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/images/**").addResourceLocations(
				"/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

}
