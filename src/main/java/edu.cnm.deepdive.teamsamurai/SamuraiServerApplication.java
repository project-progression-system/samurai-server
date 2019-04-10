/*
 *  Copyright 2019 Lance Zotigh, Alex Rauenzahn, Thomas Herrera & Deep Dive Coding
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.teamsamurai;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Main class of the Samurai Server application. All of the work of application startup is performed (directly
 * or indirectly) by the {@link SpringApplication} class, which initiates the process of
 * discovery/reflection to instantiate the components required by a Spring Data/Spring MVC
 * application.
 */
@EnableWebSecurity
@EnableResourceServer
@SpringBootApplication
public class SamuraiServerApplication extends ResourceServerConfigurerAdapter {

  @Value("${oauth.clientId}")
  private String clientId;
  /**
   * Main entry point for the Samurai Server Spring Boot application. Any command line arguments will be
   * forwarded to {@link SpringApplication#run(Class, String...)}.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(SamuraiServerApplication.class, args);
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(clientId);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//looks at this every time we make a request
    http.authorizeRequests().anyRequest().hasRole("USER"); //permitAll() to turn the access level to any. hasRole("USER") changes access to have security.
  }

}
