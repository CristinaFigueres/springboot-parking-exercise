package com.demo.parking.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final Contact DEFAULT_CONTACT = new Contact(
      "Cristina Figueres", "", "cristina.figueres@gmail.com");
  
  public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
      "Parking Exercise API", "REST services related to the parking management. ", "1.0",
      "urn:tos", DEFAULT_CONTACT, 
      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = 
      new HashSet<String>(Arrays.asList("application/json"));

  @Bean
  public Docket api() {
	  
	  List<SecurityScheme> schemeList = new ArrayList<>();
	  schemeList.add(new BasicAuth("basicAuth"));

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(DEFAULT_API_INFO)
        .securitySchemes(schemeList)
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
  }
}