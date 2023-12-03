package com.tcs.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.tcs.dto.MovieDto;
import com.tcs.service.IMovieSevice;

@WebMvcTest
@TestMethodOrder(OrderAnnotation.class)
public class TestMovieRestController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IMovieSevice moviesservice;

	public static final String converJsonToString(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	@Test
	@Order(1)
	void testAddMovie() throws Exception {
		MovieDto movieDto = new MovieDto();
		when(moviesservice.addMovie(movieDto)).thenReturn(movieDto);
		mockMvc.perform(
				post("/movies/api/save").contentType(MediaType.APPLICATION_JSON).content(converJsonToString(movieDto)))
				.andExpect(status().isCreated());
	}

	@Test
	@Order(2)
	void testGetMovieDtls() throws Exception {
		MovieDto movieDto = new MovieDto(1, "Maruthi", "SreeRamaa", LocalDate.of(2023, 1, 1), LocalTime.of(12, 30));

		when(moviesservice.getMovieDetails(1)).thenReturn(movieDto);
		mockMvc.perform(get("/movies/api/{movieId}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	void testGetAllMovies() throws Exception {
		List<MovieDto> movieDto = new ArrayList<>();
		movieDto.add(new MovieDto(1, "Maruthi", "SreeRamaa", LocalDate.of(2023, 1, 1), LocalTime.of(12, 30)));
		movieDto.add(new MovieDto(2, "Siva", "SreeRamaa", LocalDate.of(2023, 1, 1), LocalTime.of(12, 30)));
		when(moviesservice.getAllMovies()).thenReturn(movieDto);

		mockMvc.perform(get("/movies/api/AllMovies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteMovie() throws Exception {
		   Integer movieId = 1;
	        doNothing().when(moviesservice).deleteMovie(movieId);

	        // When
	        mockMvc.perform(delete("/movies/api/{movieId}", movieId)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());

	        // Then
	        verify(moviesservice).deleteMovie(movieId);
	    }

	}

