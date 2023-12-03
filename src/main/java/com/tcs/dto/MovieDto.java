package com.tcs.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

	private Integer movieId;
	@NotBlank(message = "Movie name not null")
	private String movieName;
	@NotBlank(message = "Movie genere not null")
	private String genere;

	private LocalDate releaseDate;
	
	private LocalTime time;
}
