package com.tcs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.domain.Movie;
import com.tcs.dto.MovieDto;
import com.tcs.exception.DuplicateMovieException;
import com.tcs.exception.IdNotFoundException;
import com.tcs.repo.MovieRepo;
import com.tcs.service.IMovieSevice;

@Service
public class MovieSeviceImpl implements IMovieSevice {
	@Autowired
	private MovieRepo movieRepo;

	@Override
	public MovieDto addMovie(MovieDto movieDto) {

		Movie movieName = movieRepo.findByMovieName(movieDto.getMovieName());
		if (movieName != null) {
			throw new DuplicateMovieException("Duplicate Movie::" + movieDto.getMovieName());

		}
		Movie entity = new Movie();
		BeanUtils.copyProperties(movieDto, entity);
		Movie save = movieRepo.save(entity);
		MovieDto dto = new MovieDto();
		BeanUtils.copyProperties(save, dto);
		return dto;
	}

	@Override
	public MovieDto getMovieDetails(Integer movieId) {
		Optional<Movie> optional = movieRepo.findById(movieId);
		if (optional.isPresent()) {
			Movie movie = optional.get();
			MovieDto movieDto = new MovieDto();
			BeanUtils.copyProperties(movie, movieDto);
			return movieDto;
		}
		throw new IdNotFoundException("IdNotFound::" + movieId);
	}

	@Override
	public List<MovieDto> getAllMovies() {
		List<Movie> entityMovies = movieRepo.findAll();
		List<MovieDto> movieDto = new ArrayList<>();
		for (Movie movie : entityMovies) {
			MovieDto dto = new MovieDto();
			BeanUtils.copyProperties(movie, dto);
			movieDto.add(dto);
		}
		return movieDto;
	}

	@Override
	public void deleteMovie(Integer movieId) {
		Optional<Movie> entity = movieRepo.findById(movieId);
		if (!entity.isPresent()) {
			throw new IdNotFoundException("IdNotFound::" + movieId);

		}
		movieRepo.deleteById(movieId);

	}

}
