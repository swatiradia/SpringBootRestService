package com.springbootproject.SpringBootRestService;

import com.springbootproject.SpringBootRestService.entity.Library;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assert;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest
public class SpringBootTestIntegration {

    @Test
    public void getAuthorNameBooksTest() throws JSONException {

        // Creates an instance of TestRestTemplate to simulate HTTP requests in this integration test.
        // This test is checking the functionality of the REST API by sending an HTTP GET request.
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        // Defines the expected JSON response in string format. This is the expected result that
        // the test will compare against the actual result from the API.
        String expectedJson = "[\n" +
                "    {\n" +
                "        \"id\": \"book12\",\n" +
                "        \"book_name\": \"US\",\n" +
                "        \"isbn\": \"book\",\n" +
                "        \"aisle\": 12,\n" +
                "        \"author\": \"Raj\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"book34\",\n" +
                "        \"book_name\": \"Hello\",\n" +
                "        \"isbn\": \"book\",\n" +
                "        \"aisle\": 34,\n" +
                "        \"author\": \"Raj\"\n" +
                "    }\n" +
                "]";

        // Sends a GET request to the /getBooks/author endpoint with the authorName query parameter set to "Raj".
        // This tests if the API correctly returns books authored by "Raj".
        ResponseEntity<String> response =
                testRestTemplate.getForEntity("http://localhost:8080/getBooks/author?authorName=Raj",
                        String.class);

        // Prints the HTTP status code (e.g., 200 OK, 404 Not Found) to the console for debugging purposes.
        System.out.println(response.getStatusCode());

        // Prints the actual response body returned by the API for further analysis or debugging.
        System.out.println(response.getBody());

        // This is where the actual test assertion happens.
        // JSONAssert.assertEquals() compares the actual response JSON with the expected JSON.
        // The 'false' parameter allows the comparison to ignore any additional fields in the actual response.
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);
    }


    @Test
    public void addBookIntegrationTest(){

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Library> request = new HttpEntity<>(buildLibrary(), headers);
        ResponseEntity<String> response =
                restTemplate.postForEntity("http://localhost:8080/addBook", request,String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println(response.getHeaders().get("uniqueId"));
        Assertions.assertEquals(buildLibrary().getId(),response.getHeaders().get("uniqueId").get(0));
    }

    public Library buildLibrary(){
        Library lib = new Library();
        lib.setBook_name("Malala");
        lib.setAuthor("Khaled");
        lib.setIsbn("sdfre");
        lib.setAisle(322);
        lib.setId("sdfre322");
        return lib;
    }
}
