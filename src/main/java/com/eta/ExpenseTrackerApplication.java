package com.eta;

import com.eta.interceptors.AuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("http://localhost:5500");// For particular url
		corsConfiguration.addAllowedOrigin("*");//For all
		corsConfiguration.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", corsConfiguration);
		registrationBean.setFilter(new CorsFilter(source));
		registrationBean.setOrder(0);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<AuthInterceptor> filterRegistrationBean() {
		FilterRegistrationBean<AuthInterceptor> filterRegistrationBean = new FilterRegistrationBean<>();
		AuthInterceptor interceptor = new AuthInterceptor();
		filterRegistrationBean.setFilter(interceptor);
		filterRegistrationBean.addUrlPatterns("/api/categories/*");
		return filterRegistrationBean;
	}
}
