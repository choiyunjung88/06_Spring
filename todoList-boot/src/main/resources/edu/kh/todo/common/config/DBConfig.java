package edu.kh.todo.common.config;

import java.io.IOException;

import javax.sql.DataSource;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration

@PropertySource("classpath:/config.properties") // 경로 파일 내용 읽어옴
public class DBConfig {
	// 필드
	@Autowired // DI, 의존성 주입 등록된 bean 중에서 타입ㅂ이 일치하거나 상속관계에 있는 Bean을 지정된 필드에 주입 == 의존성 주입
	private ApplicationContext applicationContext; // application scope 객체 : 즉, 현재 프로젝트
	////////////////HikariCP 설정////////////////////////
	@Bean //개발자가 수동으로 생성한 객체의 관리를 스프링에게 넘기는 어노테이션
	@ConfigurationProperties(prefix="spring.datasource.hikari") // 접두사가 일치하는 값만 읽어옴
	public HikariConfig hikariconfig() {
		return new HikariConfig();
	}
	
	public DataSource dataSource(HikariConfig config) {//Connection 생성 + Connection Pool 지원하는 객체를 참조하기 위한 java 인터페이스
		// 매개변수 HikariConfig config
		// -> 등록된 Bean 중 HikariConfig 타입의 Bean 자동으로 주입
		
		DataSource dataSource = new HikariDataSource(config);
		return dataSource;
		
	}
	
	
	/////// mybatis 설정 //////
	
	public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		// mapper.xml(SQL) 파일이 모이는 경로 지정
		// -> Mybatis 코드 수행시 mapper.xml을 읽을 수 있음
		// sessionFactoryBean.setMapperLocations("현재프로젝트.자원.어떤파일");
		
		sessionFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mappers/**.xml"));
		
		// 해당 패키지 내 모든 클래스의 별칭을 등록
		// mybatis는 특정 클래스 지정 시 패키지명.클래스명을 모두 작성해야 함
		// 너무길다 긴이름을 짧게 부를 별칭 설정할 수 있음
		// setTypeAliasesPackage("패키지") 이용 시
		// 클래스 파일명이 별칭으로 등록
		// ex) edu.kh.todo.model.dto.Todo -> Todo(별칭 등록)
		sessionFactoryBean.setTypeAliasesPackage("edu.kh.todo");
		
		// 마이바티스 설정 파일 경로 지정
		sessionFactoryBean.setConfigLocation(
				applicationContext.getResource("classPath:/mybatis-config.xml")
				);
		// 설정 내용이 모두 적용된 객체 반환
		return sessionFactoryBean.getObject();
		
		
		
	}
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}