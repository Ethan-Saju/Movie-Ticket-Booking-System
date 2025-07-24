package com.movie.booking.controller;

import com.movie.booking.payloads.APIResponse;
import com.movie.booking.payloads.ShowDTO;
import com.movie.booking.services.ShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ShowController {

    public final ShowService showService;

    @Autowired
    ShowController(ShowService showService){
        this.showService = showService;
    }

    @GetMapping("/public/shows/by-theatre/{theatreId}")
    public ResponseEntity<List<ShowDTO>> getShowsByTheatre(@PathVariable Long theatreId) {
        return new ResponseEntity<>(showService.getShowsByTheatre(theatreId) , HttpStatus.OK);
    }

    @GetMapping("/public/shows/by-movie/{movieId}")
    public ResponseEntity<List<ShowDTO>> getShowsByMovie(@PathVariable Long movieId) {
        return new ResponseEntity<>(showService.getShowsByMovie(movieId) , HttpStatus.OK);
    }

    @PostMapping("/admin/shows")
    public ResponseEntity<ShowDTO> createShow(@RequestBody ShowDTO showDTO) {
        return new ResponseEntity<>(showService.addShow(showDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/shows/{showId}")
    public ResponseEntity<APIResponse> deleteShow(@PathVariable Long showId) {
        return new ResponseEntity<>(showService.deleteShow(showId) , HttpStatus.OK);
    }



}
