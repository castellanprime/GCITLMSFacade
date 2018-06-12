package com.gcit.lmsclient.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.gcit.lmsclient.entity.BookDTO;
import com.gcit.lmsclient.entity.BookLoanDTO;
import com.gcit.lmsclient.entity.BookLoan;
import com.gcit.lmsclient.entity.Author;
import com.gcit.lmsclient.entity.Book;
import com.gcit.lmsclient.entity.Genre;
import com.gcit.lmsclient.entity.LibraryBranch;
import com.gcit.lmsclient.entity.Publisher;

@RestController
@RequestMapping("/lmsclient/")
public class LMSClient {
	
	// Urls
	private final String AUTHORS_URL = "http://localhost:9080/lmsspringboot/admin/authors";
	private final String BRANCHES_URL = "http://localhost:9080/lmsspringboot/admin/branches";
	private final String PUBLISHERS_URL = "http://localhost:9080/lmsspringboot/admin/publishers";
	private final String GENRES_URL = "http://localhost:9080/lmsspringboot/admin/genres";
	private final String BOOKS_URL = "http://localhost:9080/lmsspringboot/admin/books";
	private final String LOANS_URL = "http://localhost:9080/lmsspringboot/admin/loans";
	
	// Authors
	@Autowired
	RestTemplate restTemplate;
	
	@CrossOrigin
	@GetMapping("/authors")
	public List<Author> getAllAuthors() {
		ResponseEntity<List<Author>> response = 
				restTemplate.exchange(AUTHORS_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<Author>>(){});
		return response.getBody();
	}
	
	@CrossOrigin
	@PostMapping("/authors")
	public Author saveAuthor(@RequestParam String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTHORS_URL)
				.queryParam("name", name);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Author> response = 
				restTemplate.exchange(builder.build().toString(), HttpMethod.POST, entity, Author.class);
		return response.getBody();
	}
	
	
	@CrossOrigin
	@PatchMapping("/authors/{authorId}")
	public Author modifyAuthor(@PathVariable int authorId, @RequestParam(value="name", required=false) String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String targetUrl = AUTHORS_URL + "/" + authorId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetUrl)
				.queryParam("name", name);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Author> response = 
				restTemplate.exchange(builder.build().toString(), HttpMethod.PATCH, entity, Author.class);
		return response.getBody();
	}
	
	
	@CrossOrigin
	@DeleteMapping("/authors/{authorId}")
	public HttpHeaders deleteAuthor(@PathVariable int authorId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String targetUrl = AUTHORS_URL + "/" + authorId;
		ResponseEntity<?> response = 
				restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, Author.class);
		return response.getHeaders();
	}
	
	@CrossOrigin
	@DeleteMapping("/authors/{authorId}/books/{bookId}")
	public HttpHeaders deleteAuthorFromBook(@PathVariable int authorId, @PathVariable int bookId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("authorId", authorId);
		params.put("bookId", bookId);
		String url = AUTHORS_URL + "/{authorId}/books/{bookId}";
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
		ResponseEntity<?> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Author.class);
		return response.getHeaders();
	}
	
	// Branches
	
	@CrossOrigin
	@PostMapping("/branches")
	public LibraryBranch saveBranch(@RequestBody LibraryBranch branch) {
		ResponseEntity<LibraryBranch> response = 
				restTemplate.postForEntity(BRANCHES_URL, branch, LibraryBranch.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@PatchMapping("/branches/{branchId}")
	public LibraryBranch modifyBranch(@PathVariable int branchId, 
			@RequestBody LibraryBranch sentLibraryBranch) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String targetUrl = BRANCHES_URL + "/" + branchId;
		HttpEntity<?> entity = new HttpEntity<>(sentLibraryBranch, headers);
		ResponseEntity<LibraryBranch> response = 
				restTemplate.exchange(targetUrl, HttpMethod.PATCH, entity, LibraryBranch.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@GetMapping("/branches")
	public List<LibraryBranch> getAllBranches() {
		ResponseEntity<List<LibraryBranch>> response = 
				restTemplate.exchange(BRANCHES_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<LibraryBranch>>(){});
		return response.getBody();
	}
	
	
	@CrossOrigin
	@DeleteMapping("/branches/{branchId}")
	public HttpHeaders deleteBranch(@PathVariable int branchId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String targetUrl = BRANCHES_URL + "/" + branchId;
		ResponseEntity<?> response = 
		restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, LibraryBranch.class);
		return response.getHeaders();
	}

	
	@CrossOrigin
	@PostMapping("/publishers")
	public Publisher savePublisher(@RequestBody Publisher sentPublisher) {
		ResponseEntity<Publisher> response = 
				restTemplate.postForEntity(PUBLISHERS_URL, sentPublisher, Publisher.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@GetMapping("/publishers")
	public List<Publisher> getAllPublishers() {
		ResponseEntity<List<Publisher>> response = 
				restTemplate.exchange(PUBLISHERS_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<Publisher>>(){});
		return response.getBody();
	}
	
	@CrossOrigin
	@PatchMapping("/publishers/{publisherId}")
	public Publisher modifyPublisher(@PathVariable int publisherId, 
			@RequestBody Publisher sentPublisher) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String targetUrl = PUBLISHERS_URL + "/" + publisherId;
		HttpEntity<?> entity = new HttpEntity<>(sentPublisher, headers);
		ResponseEntity<Publisher> response = 
				restTemplate.exchange(targetUrl, HttpMethod.PATCH, entity, Publisher.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@DeleteMapping("/publishers/{publisherId}")
	public HttpHeaders deletePublisher(@PathVariable int publisherId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String targetUrl = PUBLISHERS_URL + "/" + publisherId;
		ResponseEntity<?> response = 
				restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, Publisher.class);
		return response.getHeaders();
	}
	
	@CrossOrigin
	@GetMapping("/genres")
	public List<Genre> getAllGenres() {
		ResponseEntity<List<Genre>> response = 
				restTemplate.exchange(GENRES_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<Genre>>(){});
		return response.getBody();
	}
	
	
	@CrossOrigin
	@DeleteMapping("/genres/{genreId}")
	public HttpHeaders deleteGenre(@PathVariable int genreId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String targetUrl = GENRES_URL + "/" + genreId;
		ResponseEntity<?> response = 
			restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, Genre.class);
		return response.getHeaders();
	}
	
	@CrossOrigin
	@PostMapping("/genres")
	public Genre saveGenre(@RequestParam String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GENRES_URL)
				.queryParam("name", name);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<Genre> response = 
				restTemplate.exchange(builder.build().toString(), HttpMethod.POST, entity, Genre.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@DeleteMapping("/genres/{genreId}/books/{bookId}")
	public HttpHeaders deleteGenreFromBook(@PathVariable int genreId, @PathVariable int bookId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("genreId", genreId);
		params.put("bookId", bookId);
		String url = GENRES_URL + "/{genreId}/books/{bookId}";
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
		ResponseEntity<Genre> response = 
				restTemplate.exchange(uri, HttpMethod.DELETE, entity, Genre.class);
		return response.getHeaders();
	}
	
	@CrossOrigin
	@PatchMapping("/genres/{genreId}")
	public HttpHeaders modifyGenre(@PathVariable int genreId, @RequestParam String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(GENRES_URL + "/" + genreId)
				.queryParam("name", name);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Genre> response = 
				restTemplate.exchange(builder.build().toString(), HttpMethod.PATCH, entity, Genre.class);
		return response.getHeaders();
	}
	

	@CrossOrigin
	@PostMapping("/books")
	public Book saveBook(@RequestBody BookDTO bookDto) {
		ResponseEntity<Book> response = 
				restTemplate.postForEntity(BOOKS_URL, bookDto, Book.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		ResponseEntity<List<Book>> response = 
				restTemplate.exchange(BOOKS_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<Book>>(){});
		return response.getBody();
	}
	
	@CrossOrigin
	@DeleteMapping("/books/{bookId}")
	public HttpHeaders deleteBook(@PathVariable int bookId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String targetUrl = BOOKS_URL + "/" + bookId;
		ResponseEntity<?> response = 
			restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, Book.class);
		return response.getHeaders();
	}
	
	@CrossOrigin
	@PatchMapping("/books/{bookId}")
	public Book modifyBook(@PathVariable int bookId, 
			@RequestParam(value="title", required=false) String title, 
			@RequestParam(value="publisherId", required=false) Integer publisherId,
			@RequestParam(value="authorId", required=false) Integer authorId,
			@RequestParam(value="genreId", required=false) Integer genreId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		String targetUrl = BOOKS_URL + "/" + bookId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(targetUrl)
				.queryParam("title", title)
				.queryParam("publisherId", publisherId)
				.queryParam("authorId", authorId)
				.queryParam("genreId", genreId);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<Book> response = 
				restTemplate.exchange(builder.build().toString(), HttpMethod.PATCH, entity, Book.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@GetMapping("/loans")
	public List<BookLoanDTO> getAllBookLoans(){
		ResponseEntity<List<BookLoanDTO>> response = 
				restTemplate.exchange(LOANS_URL, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<BookLoanDTO>>(){});
		return response.getBody();
	}
	
	@CrossOrigin
	@PatchMapping("/loans/dueDate")
	public BookLoan changeLoanDueDate(@RequestBody BookLoan loan) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(loan, headers);
		String targetUrl = LOANS_URL + "/dueDate";
		ResponseEntity<BookLoan> response = 
				restTemplate.exchange(targetUrl, HttpMethod.PATCH, entity, BookLoan.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@GetMapping("/loans/current")
	public List<BookLoan> getAllCurrentLoansForBranch(@RequestParam int branchId){
		String targetUrl = LOANS_URL + "/current";
		ResponseEntity<List<BookLoan>> response = 
				restTemplate.exchange(targetUrl, HttpMethod.GET, null, 
						new ParameterizedTypeReference<List<BookLoan>>(){});
		return response.getBody();
	}
}
