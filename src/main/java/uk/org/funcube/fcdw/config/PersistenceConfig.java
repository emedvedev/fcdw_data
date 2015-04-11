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

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "uk.org.funcube.fcdw.dao")
public class PersistenceConfig {

    @Bean
    @Resource(name = "jdbc/funcube")
    public DataSource dataSourceLookup() {
        JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        final DataSource dataSource = dsLookup.getDataSource("java:comp/env/jdbc/funcube");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceLookup());
        em.setPackagesToScan(new String[] {"uk.org.funcube.fcdw.domain"});

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    private Properties additionalProperties() {
        return new Properties() {
            private static final long serialVersionUID = -2580236965844364715L;

            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            }
        };
    }

}
