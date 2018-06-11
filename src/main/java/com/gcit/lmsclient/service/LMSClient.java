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
import com.gcit.lmsclient.entity.Author;
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
	// Branches
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
		HttpEntity<Author> response = 
				restTemplate.exchange(builder.toString(), HttpMethod.POST, entity, Author.class);
		return response.getBody();
	}
	
	/**
	@CrossOrigin
	@PatchMapping("/authors/{authorId}")
	public void editAuthor(@PathVariable int authorId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTHORS_URL)
				.queryParam("name", name);
	}**/
	
	
	@CrossOrigin
	@DeleteMapping("/authors/{authorId}")
	public void deleteAuthor(@PathVariable int authorId) {
		restTemplate.delete(AUTHORS_URL + "/{id}", authorId);
	}
	
	@CrossOrigin
	@DeleteMapping("/authors/{authorId}/books/{bookId}")
	public void deleteAuthorFromBook(@PathVariable int authorId, @PathVariable int bookId) {
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
		restTemplate.exchange(uri, HttpMethod.DELETE, entity, Author.class);
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
	public void deleteBranch(@PathVariable int branchId) {
		restTemplate.delete(BRANCHES_URL + "/{id}", branchId);
	}
	
	@CrossOrigin
	@PostMapping("/branches")
	public LibraryBranch saveBranch(@RequestBody LibraryBranch branch) {
		ResponseEntity<LibraryBranch> response = 
				restTemplate.postForEntity(BRANCHES_URL, branch, LibraryBranch.class);
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
	@DeleteMapping("/publisher/{publisherId}")
	public void deletePublisher(@PathVariable int publisherId) {
		restTemplate.delete(PUBLISHERS_URL + "/{id}", publisherId);
	}
	
	@CrossOrigin
	@PostMapping("/publishers")
	public Publisher savePublisher(@RequestBody Publisher sentPublisher) {
		ResponseEntity<Publisher> response = 
				restTemplate.postForEntity(PUBLISHERS_URL, sentPublisher, Publisher.class);
		return response.getBody();
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
	@DeleteMapping("/genre/{genreId}")
	public void deleteGenre(@PathVariable int genreId) {
		restTemplate.delete(GENRES_URL + "/{id}", genreId);
	}
	
	@CrossOrigin
	@PostMapping("/genres")
	public Genre saveGenre(@RequestParam String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTHORS_URL)
				.queryParam("name", name);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<Genre> response = 
				restTemplate.exchange(builder.toString(), HttpMethod.POST, entity, Genre.class);
		return response.getBody();
	}
	
	@CrossOrigin
	@DeleteMapping("/admin/genres/{genreId}/books/{bookId}")
	public void deleteGenreFromBook(@PathVariable int genreId, @PathVariable int bookId) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("genreId", genreId);
		params.put("bookId", bookId);
		String url = AUTHORS_URL + "/{authorId}/books/{bookId}";
		URI uri = UriComponentsBuilder.fromUriString(url)
		        .buildAndExpand(params)
		        .toUri();
		restTemplate.exchange(uri, HttpMethod.DELETE, entity, Genre.class);
	}
	
	@CrossOrigin
	@PostMapping("/books")
	public LibraryBranch saveBook(@RequestBody BookDTO bookDto) {
		ResponseEntity<LibraryBranch> response = 
				restTemplate.postForEntity(BRANCHES_URL, branch, LibraryBranch.class);
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
	@DeleteMapping("/publisher/{publisherId}")
	public void deletePublisher(@PathVariable int publisherId) {
		restTemplate.delete(PUBLISHERS_URL + "/{id}", publisherId);
	}
	
}
