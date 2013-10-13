package uk.org.funcube.fcdw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import uk.org.funcube.fcdw.server.processor.FitterMessageProcessor;
import uk.org.funcube.fcdw.server.processor.FitterMessageProcessorImpl;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessor;
import uk.org.funcube.fcdw.server.processor.HighResolutionProcessorImpl;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessor;
import uk.org.funcube.fcdw.server.processor.WholeOrbitDataProcessorImpl;
import uk.org.funcube.fcdw.server.util.Clock;
import uk.org.funcube.fcdw.server.util.UTCClock;


@EnableWebMvc
@ComponentScan(basePackages = {"uk.org.funcube.fcdw"})
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
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
    public InternalResourceViewResolver configureInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	
	@Bean
	public UrlBasedViewResolver configureUrlBasedViewResolver () {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
        return resolver;
		
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

}
