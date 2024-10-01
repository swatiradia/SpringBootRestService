package com.springbootproject.SpringBootRestService;

import com.springbootproject.SpringBootRestService.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// @SpringBootApplication is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.
// It marks this class as the entry point of the Spring Boot application, enabling component scanning, auto-configuration, and other features.
@SpringBootApplication
public class SpringBootRestServiceApplication /*implements CommandLineRunner*/ {

	// @Autowired injects a bean (such as a service or repository) into this class at runtime.
	@Autowired
	LibraryRepository repository;

	public static void main(String[] args) {

// SpringApplication.run() is used to launch the Spring Boot application.
// It starts the Spring application context, performs component scanning, and initializes all Spring-managed beans.
// It takes the application class (SpringBootRestServiceApplication.class) and command-line arguments (args) as parameters.
		SpringApplication.run(SpringBootRestServiceApplication.class, args);

	}

//	@Override
//	public void run(String[] args) throws Exception {
//		Library lib = repository.findById("fdsefr343").get();
//		System.out.println(lib.getAuthor());
//		Library lib2 = new Library();
//		lib2.setAisle(4234);
//		lib2.setAuthor("3242");
//		lib2.setBook_name("Ddfgfdg");
//		lib2.setIsbn("swsdfa");
//		lib2.setId("dsfds43");
//		repository.save(lib2);
//		repository.delete(lib2);
//		List<Library> allRecords = repository.findAll();
//		for(Library item : allRecords){
//			System.out.println(item.getBook_name());
//		}
//	}

}
