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
/**
 * Main class of the QoD application. All of the work of application startup is performed (directly
 * or indirectly) by the {@link SpringApplication} class, which initiates the process of
 * discovery/reflection to instantiate the components required by a Spring Data/Spring MVC
 * application.
 */
@SpringBootApplication
public class SamuraiServerApplication {

  @Value("${oauth.clientId}")
  private String clientId;

  /**
   * Main entry point for the QoD Spring Boot application. Any command line arguments will be
   * forwarded to {@link SpringApplication#run(Class, String...)}.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(SamuraiServerApplication.class, args);
  }
/*

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(clientId);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//looks at this evertime we make a request
    http.authorizeRequests().anyRequest().hasRole("USER");
  }
*/

}
