package com.movie.booking.controller;


import com.movie.booking.configs.AppConstants;
import com.movie.booking.payloads.*;
import com.movie.booking.services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class TheatreController {

    public final TheatreService theatreService;

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/public/theatres")
    public ResponseEntity<TheatreResponse> getAllTheatres(
            @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name="sortBy" , defaultValue = "theatreId") String sortBy,
            @RequestParam(name="sortOrder" , defaultValue = AppConstants.SORT_ORDER) String sortOrder
    ) {
        return new ResponseEntity<>(theatreService.getAllTheatres(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);
    }

    @GetMapping("/public/theatres/{theatreId}")
    public ResponseEntity<TheatreDTO> getTheatreById(@PathVariable("theatreId") long theatreId) {
        return new ResponseEntity<>(theatreService.getTheatreById(theatreId), HttpStatus.OK);
    }

    @PostMapping("/admin/theatres")
    public ResponseEntity<TheatreDTO> createTheatre(@RequestBody TheatreDTO theatreDTO) {
        return new ResponseEntity<>(theatreService.addTheatre(theatreDTO), HttpStatus.CREATED);
    }

    @PatchMapping("admin/theatres/{theatreId}")
    public ResponseEntity<TheatreDTO>  updateTheatre(@PathVariable("theatreId") long theatreId, @RequestBody TheatreDTO theatreDTO) {
        return new ResponseEntity<>(theatreService.updateTheatre(theatreId, theatreDTO), HttpStatus.OK);
    }


    @DeleteMapping("admin/theatres/{theatreId}")
    public ResponseEntity<APIResponse> deleteTheatre(@PathVariable("theatreId") long theatreId) {
        return new ResponseEntity<>(theatreService.deleteTheatre(theatreId) , HttpStatus.OK);
    }
}