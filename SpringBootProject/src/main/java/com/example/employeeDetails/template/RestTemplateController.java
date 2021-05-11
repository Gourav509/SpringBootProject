package com.example.employeeDetails.template;

import com.example.employeeDetails.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;


@RestController
@RequestMapping("/restTemplate/api/employees")
public class RestTemplateController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/")
    public String getEmployeeList() {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String token = request.getHeader("authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange(
                "http://localhost:8080/api/employees/", HttpMethod.GET, entity, String.class).getBody();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createEmployee(@RequestBody Employee employee) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String token = request.getHeader("authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);

        return restTemplate.exchange(
                "http://localhost:8080/api/employees/", HttpMethod.POST, entity, String.class).getBody();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String token = request.getHeader("authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);

        return restTemplate.exchange(
                "http://localhost:8080/api/employees/" + id, HttpMethod.PUT, entity, String.class).getBody();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") String id) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
        String token = request.getHeader("authorization");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        HttpEntity<Employee> entity = new HttpEntity<Employee>(headers);

        return restTemplate.exchange(
                "http://localhost:8080/api/employees/" + id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}