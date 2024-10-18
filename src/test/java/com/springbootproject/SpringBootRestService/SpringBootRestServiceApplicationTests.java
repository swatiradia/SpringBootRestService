package com.springbootproject.SpringBootRestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootproject.SpringBootRestService.controller.LibraryController;
import com.springbootproject.SpringBootRestService.entity.AddBookResponse;
import com.springbootproject.SpringBootRestService.entity.Library;
import com.springbootproject.SpringBootRestService.repository.LibraryRepository;
import com.springbootproject.SpringBootRestService.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootRestServiceApplicationTests {

	@MockBean
	LibraryService libraryService;

	@MockBean
	LibraryRepository repository;

	@Autowired
	LibraryController controller;

	@Autowired
	private MockMvc mockMvc;


	public Library buildLibrary(){
		Library lib = new Library();
		lib.setBook_name("Malala");
		lib.setAuthor("Khaled");
		lib.setIsbn("sdfre");
		lib.setAisle(322);
		lib.setId("sdfre322");
		return lib;
	}

	public Library updateLibrary(){
		Library lib = new Library();
		lib.setBook_name("SpringBoot");
		lib.setAuthor("Koi");
		lib.setIsbn("sdfre");
		lib.setAisle(322);
		lib.setId("sdfre322");
		return lib;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogin(){
		LibraryService service = new LibraryService();
		String id = service.buildIdMethod("Zman", 24);
		assertEquals(id, "oldZman24");
	}

	@Test
	public void checkAddBookWhenDoesNotExist() {

		// library object holds all the data sent form buildLibrary method.
		Library lib = buildLibrary();

		// Mock the behavior of 'libraryService.buildIdMethod' to return the expected book ID using mockito
		// based on the library's ISBN and aisle
		when(libraryService.buildIdMethod(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());

		// Mock the behavior of 'libraryService.checkBookAlreadyExists' to return false,
		// indicating that the book does not already exist in the library
		when(libraryService.checkBookAlreadyExists(lib.getId())).thenReturn(false);

		// Call the 'addBook' method from the controller to add the new book to the library
		ResponseEntity response = controller.addBook(buildLibrary());

		// Assert that the HTTP response status is 'CREATED' (201),
		// indicating the book was successfully added
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);

		//  retrieve the response body
		AddBookResponse ad = (AddBookResponse) response.getBody();
		assertEquals(lib.getId(), ad.getId());
		assertEquals("Success!! Book is Added", ad.getMessage());
	}


	@Test
	public void checkAddBookWhenExists(){

		Library lib = buildLibrary();
		when(libraryService.buildIdMethod(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
		when(libraryService.checkBookAlreadyExists(lib.getId())).thenReturn(true);

		ResponseEntity response = controller.addBook(buildLibrary());
		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
		//  retrieve the response body
		AddBookResponse ad = (AddBookResponse) response.getBody();
		assertEquals(lib.getId(), ad.getId());
		assertEquals("Book already present!!", ad.getMessage());
	}

//	Mocking the endpoint call using MockMvc
	@Test
	public void addBookControllerTest() throws Exception {

		Library lib = buildLibrary();
		ObjectMapper map = new ObjectMapper();
	// Convert a Library object to a JSON string using ObjectMapper (serialization).
		String jsonString = map.writeValueAsString(lib);

	// Mock the service layer's method to generate an ID for the book using ISBN and aisle, and return it.
		when(libraryService.buildIdMethod(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());

	// Mock the service layer to simulate the book does not already exist in the database.
		when(libraryService.checkBookAlreadyExists(lib.getId())).thenReturn(false);

	// Mock the repository's save method to save the book and return the saved object.
		when(repository.save(any())).thenReturn(lib);

	// Simulate a POST request to the '/addBook' endpoint with a JSON payload and check the response.
		this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)) // Set the content type and attach the serialized JSON string
				.andDo(print()) // Print the request and response for better visibility in the test logs.

			// Assert that the HTTP status returned is 'Created' (201) to indicate success.
				.andExpect(status().isCreated())

			// Assert that the 'id' in the response JSON matches the library object's ID.
				.andExpect(jsonPath("$.id").value(lib.getId()))

			// Assert that the success message in the response is "Success!! Book is Added".
				.andExpect(jsonPath("$.message").value("Success!! Book is Added"));
	}

	@Test
	public void getBookByAuthorNameControllerTest () throws Exception {
		List<Library> libraryList = new ArrayList<>();
		libraryList.add(buildLibrary());
		libraryList.add(buildLibrary());
		when(repository.findAllByAuthor(any())).thenReturn(libraryList);
		this.mockMvc.perform(get("/getBooks/author").queryParam("authorName", "Khaled"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)))
				.andExpect(jsonPath("$.[0].id").value("sdfre322"));
	}

	@Test
	public void updateBookControllerTest() throws Exception {
		Library lib = new Library();
		ObjectMapper map = new ObjectMapper();
		String jsonSting = map.writeValueAsString(updateLibrary());
		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		this.mockMvc.perform(put("/updateBook/"+lib.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(jsonSting)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("{\"id\":\"sdfre322\",\"book_name\":\"SpringBoot\",\"isbn\":\"sdfre\",\"aisle\":322,\"author\":\"Koi\"}"));
	}

	@Test
	public void deleteBookControllerTest() throws Exception {
		when(libraryService.getBookById(any())).thenReturn(buildLibrary());
		doNothing().when(repository).delete(buildLibrary());
		this.mockMvc.perform(delete("/deleteBook").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "    \"id\" : \"sdfre322\"\n" + "}")).andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string("Book is Successfully deleted"));
	}

}
