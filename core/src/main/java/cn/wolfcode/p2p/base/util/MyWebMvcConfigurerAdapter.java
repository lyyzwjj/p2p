package cn.wolfcode.p2p.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Component
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Autowired
	private LoginCheckInteceptor loginCheckInteceptor;
	@Value("${app.imgfolder}")
	private String imgFolder;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInteceptor);
		super.addInterceptors(registry);
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**").addResourceLocations("file:/"+imgFolder);
		super.addResourceHandlers(registry);
	}
	
	
}
