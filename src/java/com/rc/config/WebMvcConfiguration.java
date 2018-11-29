package com.rc.config;


import com.rc.dao.impl.GameServiceDaoImpl;
import com.rc.services.OperationalServices;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.rc.services.ConsentTimer;
import com.rc.services.CsvOperationServices;
import com.rc.dao.GameServiceDao;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.rc")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	 @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        //resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/celltickappdb");
       dataSource.setUsername("ravendra");
       dataSource.setPassword("Chauhan@123");

        return dataSource;
    }

    @Bean
    public GameServiceDao getSolrServiceDao() {
        return new GameServiceDaoImpl(getDataSource());
    }
     @Bean
    public OperationalServices getOperationalServices() {
        return new OperationalServices();
    }
    @Bean
    public ConsentTimer getConsentTimer() {
        return new ConsentTimer();
    }
  
    @Bean
    public CsvOperationServices getCsvOperationServices() {
        return new CsvOperationServices();
    }
  
    
    
    
}