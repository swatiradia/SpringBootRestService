package com.springbootproject.SpringBootRestService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

// The @RestController annotation tells Spring Boot that this class is a controller that will handle HTTP requests.
// It also means that the methods inside this class will return data directly in the response body (typically JSON),
// instead of returning a view or template.
@RestController
public class GreetingController {
// Autowired annotation makes Spring boot create object in runtime.
    @Autowired
     Greeting greeting;

    AtomicLong counter = new AtomicLong();
    // @GetMapping is a shortcut for @RequestMapping(method = RequestMethod.GET).
    // This tells Spring Boot that this method should be called whenever there is an HTTP GET request to the "/greeting" endpoint.
    @GetMapping("/greeting")
    public Greeting greeting(
            // @RequestParam binds a query parameter from the URL to the method parameter.
            // In this case, the value of "name" in the URL (e.g., "/greeting?name=John") will be assigned to the 'name' variable.
            @RequestParam(value = "name") String name){
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey " +name+ ", This is my first Spring Boot Project");
        return greeting;
    }
}
