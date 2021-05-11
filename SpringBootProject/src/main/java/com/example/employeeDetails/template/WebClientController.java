package com.example.employeeDetails.template;

import com.example.employeeDetails.model.Employee;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/webClient/api/employees")
public class WebClientController {

    private static final String BASE_URL = "http://localhost:8080/api/employees/";

    WebClient webClient;

    public WebClientController() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping(value = "/{bearerToken}")
    public List<Employee> getAllEmployees(@PathVariable(name = "bearerToken") String bearerToken) {

        List<Employee> employees = webClient
                .get()
                .uri(BASE_URL)
                .header("authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList().block();
        return employees;
    }

    @GetMapping(value = "/{employeeId}/{bearerToken}")
    public Employee getEmployeeById(@PathVariable(name = "employeeId") Long employeeId,
                                    @PathVariable(name = "bearerToken") String bearerToken) {

        Employee employees = webClient
                .get()
                .uri(BASE_URL + employeeId)
                .header("authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
        return employees;
    }

    @PostMapping(value = "/{bearerToken}")
    public Employee addEmployee(@RequestBody Employee employee,
                                @PathVariable(name = "bearerToken") String bearerToken) {

        Employee result = webClient
                .post()
                .uri(BASE_URL)
                .header("authorization", "Bearer " + bearerToken)
                .body(Mono.just(employee), Employee.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
        return result;
    }

    @PutMapping(value = "/{employeeId}/{bearerToken}")
    public Employee updateEmployeeById(@PathVariable Long employeeId, @RequestBody Employee employee,
                                       @PathVariable(name = "bearerToken") String bearerToken) {

        Employee result = webClient
                .put()
                .uri(BASE_URL + employeeId)
                .header("authorization", "Bearer " + bearerToken)
                .syncBody(employee)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
        return result;
    }

    @DeleteMapping("/{employeeId}/{bearerToken}")
    public Boolean deleteEmployeeById(@PathVariable Long employeeId,
                                      @PathVariable(name = "bearerToken") String bearerToken) {

        Boolean result = false;
        Employee employee = webClient
                .delete()
                .uri(BASE_URL + employeeId)
                .header("authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
        if (employee != null)
            result = true;
        System.out.println("The Response is: " + employee);
        System.out.println(result);
        return result;
    }

}