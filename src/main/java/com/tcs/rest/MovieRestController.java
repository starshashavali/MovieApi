package com.tcs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.ApiResponse;
import com.tcs.dto.MovieDto;
import com.tcs.service.IMovieSevice;

@RestController
@RequestMapping("/movies/api")
public class MovieRestController {

	@Autowired
	private IMovieSevice movieService;

	@PostMapping("/save")
	public ResponseEntity<?> addMovies(@RequestBody MovieDto movieDto) {
		MovieDto status = movieService.addMovie(movieDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(status);
	}

	@GetMapping("/{movieId}")
	public ResponseEntity<?> getMovieDtls(@PathVariable Integer movieId) {
		MovieDto status = movieService.getMovieDetails(movieId);
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}

	@GetMapping("/AllMovies")
	public ResponseEntity<?> getAllMovies() {
		List<MovieDto> list = movieService.getAllMovies();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable Integer movieId) {
		movieService.deleteMovie(movieId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Deleted successfully..."+movieId,true));
	}

}
