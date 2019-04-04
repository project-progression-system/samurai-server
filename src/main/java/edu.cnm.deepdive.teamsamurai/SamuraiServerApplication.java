
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
