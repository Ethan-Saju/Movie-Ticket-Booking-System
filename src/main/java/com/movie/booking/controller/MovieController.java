package com.movie.booking.controller;



import com.movie.booking.configs.AppConstants;
import com.movie.booking.payloads.APIResponse;
import com.movie.booking.payloads.MovieDTO;
import com.movie.booking.payloads.MovieResponse;
import com.movie.booking.services.MovieService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class MovieController {

    public final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/public/movies")
    public ResponseEntity<MovieResponse> getAllMovies(
            @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy" , defaultValue = "movieId") String sortBy,
            @RequestParam(name="sortOrder" , defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ) {
        return new ResponseEntity<>(movieService.getAllMovies(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/movies/{movieId}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("movieId") long movieId) {
        return new ResponseEntity<>(movieService.getMovieById(movieId), HttpStatus.OK);
    }

    @PostMapping("/admin/movies")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @PatchMapping("admin/movies/{movieId}")
    public ResponseEntity<MovieDTO>  updateMovie(@PathVariable("movieId") long movieId, @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.updateMovie(movieId, movieDTO), HttpStatus.OK);
    }


    @DeleteMapping("admin/movies/{movieId}")
    public ResponseEntity<APIResponse> deleteMovie(@PathVariable("movieId") long movieId) {
        return new ResponseEntity<>(movieService.deleteMovie(movieId) , HttpStatus.OK);
    }
}
