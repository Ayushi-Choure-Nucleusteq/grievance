/**
 * Configuration class to set up custom filters.
 */
package com.grievance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grievance.filter.Authentication;
import com.grievance.repository.MemberRepo;

@Configuration
public class FilterConfig {

    /** Repository for Member entities. */
    @Autowired
    private MemberRepo memberRepo;

    /**
     * Constructor for FilterConfig.
     *
     * @param amemberRepo the member repository
     */
    public FilterConfig(final MemberRepo amemberRepo) {
        super();
        this.memberRepo = amemberRepo;
    }

    /**
     * Bean configuration for the Authentication filter.
     *
     * @return a FilterRegistrationBean for the Authentication filter.
     */
    @Bean
    public FilterRegistrationBean<Authentication> registrationBeanAdmin() {
        FilterRegistrationBean<Authentication> registrationBean =
                new FilterRegistrationBean<Authentication>();
        registrationBean.setFilter(new Authentication(memberRepo));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
