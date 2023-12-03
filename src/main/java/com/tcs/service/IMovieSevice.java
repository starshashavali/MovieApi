package com.tcs.service;

import java.util.List;

import com.tcs.dto.MovieDto;

public interface IMovieSevice {

	public MovieDto addMovie(MovieDto movieDto);

	public MovieDto getMovieDetails(Integer movieId);

	public List<MovieDto> getAllMovies();

	public void deleteMovie(Integer movieId);

}
