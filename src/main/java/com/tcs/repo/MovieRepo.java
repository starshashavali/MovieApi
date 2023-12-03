package com.tcs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.domain.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
	Movie findByMovieName(String movieName);
}
