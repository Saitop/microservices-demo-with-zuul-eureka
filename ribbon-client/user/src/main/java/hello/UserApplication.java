package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class UserApplication {

//  @LoadBalanced
//  @Bean
//  RestTemplate restTemplate(){
//    return new RestTemplate();
//  }

  @Autowired
  RestTemplate restTemplate;

  @RequestMapping("/hi")
  public String hi(@RequestParam(value="name", defaultValue="Artaban") String name) {
    String greeting = this.restTemplate.getForObject("http://say-hello/greeting", String.class);
    return String.format("%s, %s!", greeting, name);
  }

  @RequestMapping("/math-in-compute-service")
  public String add() {
    Integer sum = this.restTemplate.getForObject("http://compute-service/math/add?a=1&b=20", Integer.class);

    return String.format("the sum is, %s!", sum);
  }

  @RequestMapping("/get-math-by-zuul-gateway")
  public String getSumByZuul() {
    Integer sum = this.restTemplate.getForObject("http://zuul/compute-service/math/add?a=1&b=90", Integer.class);

    return String.format("the sum by zuul is, %s!", sum);
  }

  //get sample
//  private static void getEmployeeById()
//  {
//    final String uri = "http://localhost:8080/springrestexample/employees/{id}";
//
//    Map<String, String> params = new HashMap<String, String>();
//    params.put("id", "1");
//
//    RestTemplate restTemplate = new RestTemplate();
//    EmployeeVO result = restTemplate.getForObject(uri, EmployeeVO.class, params);
//
//    System.out.println(result);
//  }
//
  //post sample
//  private static void createEmployee()
//  {
//    final String uri = "http://localhost:8080/springrestexample/employees";
//
//    EmployeeVO newEmployee = new EmployeeVO(-1, "Adam", "Gilly", "test@email.com");
//
//    RestTemplate restTemplate = new RestTemplate();
//    EmployeeVO result = restTemplate.postForObject( uri, newEmployee, EmployeeVO.class);
//
//    System.out.println(result);
//  }

  //put sample
//  private static void deleteEmployee()
//  {
//    final String uri = "http://localhost:8080/springrestexample/employees/{id}";
//
//    Map<String, String> params = new HashMap<String, String>();
//    params.put("id", "2");
//
//    EmployeeVO updatedEmployee = new EmployeeVO(2, "New Name", "Gilly", "test@email.com");
//
//    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.put ( uri, updatedEmployee, params);
//  }

  //delete sample
//  private static void deleteEmployee()
//  {
//    final String uri = "http://localhost:8080/springrestexample/employees/{id}";
//
//    Map<String, String> params = new HashMap<String, String>();
//    params.put("id", "2");
//
//    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.delete ( uri,  params );
//  }

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}

