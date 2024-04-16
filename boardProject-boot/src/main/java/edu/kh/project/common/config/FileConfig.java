package edu.kh.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileConfig implements WebMvcConfigurer{

	// WebMvcConfigurer : Spring MVC 프레임 워크에서 제공하는 인터페이스 중 하나로,
	// 스프링 구성을 커스터 마이징하고 확장하기 위한 메서드를 제공함.
	// 주로 웹 어플리케이션의 설정을 조정하거나 추가하는데 사용됨.
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;
	
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize;
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize;
	
	@Value("${spring.servlet.multipart.location}")
	private String location;

	//요청 주소에 따라 서버 컴퓨터의 어떤 경로에 접근할지 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/myPage/file/**") // 클라이언트 요청 주소 패턴
		.addResourceLocations("file:///C:/uploadFiles/test/");
		// 클라이언트가 /myPage/file/** 패턴으로 이미지를 요청할 때
		// 요청을 연결해서 처리해줄 서버 폴더 경로 연결
	}
	
	
	@Bean
	public MultipartConfigElement configElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		factory.setLocation(location);
		return factory.createMultipartConfig();
	}
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver
				= new StandardServletMultipartResolver();
		return multipartResolver;
	}
}
