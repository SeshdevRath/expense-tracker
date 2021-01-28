package com.eta;

import com.eta.interceptors.AuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
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
