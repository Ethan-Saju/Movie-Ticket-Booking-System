package com.movie.booking.services;

import com.movie.booking.exceptions.APIException;
import com.movie.booking.exceptions.ResourceNotFoundException;
import com.movie.booking.models.Movie;
import com.movie.booking.models.Show;
import com.movie.booking.models.Theatre;
import com.movie.booking.payloads.APIResponse;
import com.movie.booking.payloads.ShowDTO;
import com.movie.booking.repositories.MovieRepository;
import com.movie.booking.repositories.ShowRepository;
import com.movie.booking.repositories.TheatreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {
    
    private final ModelMapper modelMapper;
    private final ShowRepository showRepository;
    private final TheatreRepository theatreRepository;
    private final MovieRepository movieRepository;
    
    @Autowired
    public ShowService(ModelMapper modelMapper, ShowRepository showRepository , TheatreRepository theatreRepository, MovieRepository movieRepository) {
        this.modelMapper = modelMapper;
        this.showRepository = showRepository;
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
    }


    public List<ShowDTO> getShowsByTheatre(Long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre","theatreId",theatreId));

        List<Show> shows = showRepository.findByTheatre(theatre);

        if(shows.isEmpty()) {
            throw new APIException("No shows found");
        }


        return shows.stream().map(
                this::mapToDTO
        ).collect(Collectors.toList());
    }

    public List<ShowDTO> getShowsByMovie(Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie","movieId",movieId));

        List<Show> shows = showRepository.findByMovie(movie);
        if(shows.isEmpty()) {
            throw new APIException("No shows found");
        }

        return shows.stream().map(
           this::mapToDTO
        ).collect(Collectors.toList());


    }

    public ShowDTO addShow(ShowDTO showDTO) {

        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie","movieId",showDTO.getMovieId()));

        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(()->new ResourceNotFoundException("Theatre","theatreId",showDTO.getTheatreId()));



        LocalDateTime startTime = showDTO.getStartTime();
        LocalDateTime endTime = startTime.plusMinutes(movie.getDuration());

        List<Show> shows = showRepository.findOverlappingShows(theatre, startTime , endTime);
        if (!shows.isEmpty()) {
            throw new APIException("Overlapping show found");
        }


        Show show = modelMapper.map(showDTO, Show.class);
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setStartTime(startTime);
        show.setEndTime(endTime);

        Show savedShow = showRepository.save(show);


        return mapToDTO(savedShow);

    }

    public APIResponse deleteShow(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show","showId",showId));

        showRepository.delete(show);

        return new APIResponse("Show deleted successfully" , true);

    }

    private ShowDTO mapToDTO(Show show) {
        ShowDTO dto = modelMapper.map(show, ShowDTO.class);
        dto.setMovieId(show.getMovie().getMovieId());
        dto.setMovieName(show.getMovie().getName());
        dto.setTheatreId(show.getTheatre().getTheatreId());
        dto.setTheatreName(show.getTheatre().getName());
        return dto;
    }

}
