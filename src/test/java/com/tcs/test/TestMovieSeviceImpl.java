package com.tcs.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.tcs.domain.Movie;
import com.tcs.dto.MovieDto;
import com.tcs.exception.DuplicateMovieException;
import com.tcs.exception.IdNotFoundException;
import com.tcs.repo.MovieRepo;
import com.tcs.service.impl.MovieSeviceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TestMovieSeviceImpl {

	@Mock
	private MovieRepo movieRepo;

	@InjectMocks
	private MovieSeviceImpl movieServiceImpl;

	@Test
	@Order(1)
	public void testAddMovieSuccess() {
		MovieDto movieDto = new MovieDto(1, "Maruthi", "SreeRamaa", LocalDate.of(2023, 1, 1), LocalTime.of(12, 30));

		Movie entity = new Movie();
		BeanUtils.copyProperties(movieDto, entity);

		when(movieRepo.findByMovieName("Sample Movie")).thenReturn(null);
		when(movieRepo.save(entity)).thenReturn(entity);

		MovieDto result = movieServiceImpl.addMovie(movieDto);

		assertNotNull(result);
	}

	@Test
	@Order(2)
	public void testAddMovieDuplicateMovieException() {

		MovieDto movieDto = new MovieDto();
		movieDto.setMovieName("Duplicate Movie");

		when(movieRepo.findByMovieName("Duplicate Movie")).thenReturn(new Movie());

		assertThrows(DuplicateMovieException.class, () -> {
			movieServiceImpl.addMovie(movieDto);
		});
	}

	@Test
	@Order(3)
	public void testGetMovieDetails_Success() {
		Integer movieId = 1;
		Movie movie = new Movie();
		movie.setMovieId(movieId);
		when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));

		MovieDto result = movieServiceImpl.getMovieDetails(movieId);

		assertNotNull(result);
		assertEquals(movieId, result.getMovieId());
	}

	@Test
	@Order(4)
	public void testGetMovieDetails_IdNotFoundException() {
		Integer movieId = 2;
		when(movieRepo.findById(movieId)).thenReturn(Optional.empty());

		assertThrows(IdNotFoundException.class, () -> {
			movieServiceImpl.getMovieDetails(movieId);
		});
	}

	@Test
	@Order(5)
	public void testGetAllMovies_Success() {
		
		List<Movie> entityMovies = new ArrayList<>();
		entityMovies.add(new Movie());
		entityMovies.add(new Movie());
		when(movieRepo.findAll()).thenReturn(entityMovies);

		
		List<MovieDto> result = movieServiceImpl.getAllMovies();

	
		assertNotNull(result);
		assertEquals(entityMovies.size(), result.size());
	}

	@Test
	@Order(6)
	public void testGetAllMovies_EmptyList() {

		when(movieRepo.findAll()).thenReturn(new ArrayList<>());

		List<MovieDto> result = movieServiceImpl.getAllMovies();

		assertNotNull(result);
		assertTrue(result.isEmpty());

	}

	@Test
	@Order(7)
	public void testDeleteMovie_Success() {
		Integer movieId = 1;
		Movie movie = new Movie();
		movie.setMovieId(movieId);
		when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));

		
		assertDoesNotThrow(() -> movieServiceImpl.deleteMovie(movieId));


		verify(movieRepo, times(1)).deleteById(movieId);
	}

	@Test
	@Order(8)
	public void testDeleteMovie_IdNotFoundException() {

		Integer movieId = 2;
		when(movieRepo.findById(movieId)).thenReturn(Optional.empty());

		IdNotFoundException ex = assertThrows(IdNotFoundException.class, () -> {
			movieServiceImpl.deleteMovie(movieId);
		});
		assertEquals(ex.getMessage(), "IdNotFound::" + movieId);

	}

}
