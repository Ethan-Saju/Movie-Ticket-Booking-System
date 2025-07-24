package com.movie.booking.services;


import com.movie.booking.exceptions.APIException;
import com.movie.booking.exceptions.ResourceNotFoundException;
import com.movie.booking.models.Movie;
import com.movie.booking.payloads.APIResponse;
import com.movie.booking.payloads.MovieDTO;
import com.movie.booking.payloads.MovieResponse;
import com.movie.booking.payloads.PaginatedResponse;
import com.movie.booking.repositories.MovieRepository;
import com.movie.booking.repositories.ShowRepository;
import com.movie.booking.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MovieService {

    private final ModelMapper modelMapper;
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    @Autowired
    MovieService(MovieRepository movieRepository,  ShowRepository showRepository,ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.modelMapper = modelMapper;
    }

    public MovieResponse getAllMovies(Integer pageNumber, Integer pageSize , String sortBy, String sortOrder){

        PaginatedResponse<MovieDTO> paginatedResponse = PaginationUtils.buildPaginatedResponse(
                movieRepository,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder,
                modelMapper,
                Movie.class,
                MovieDTO.class
        );


        return  modelMapper.map(paginatedResponse, MovieResponse.class);


    }


    public MovieDTO getMovieById(long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));

        return modelMapper.map(movie , MovieDTO.class);
    }



    public MovieDTO addMovie(MovieDTO movieDTO){

        Movie newMovie = modelMapper.map(movieDTO, Movie.class);

        movieRepository.findMovieByName(newMovie.getName())
                .ifPresent(m-> {
                    throw new APIException("Movie already exists");
                });

        Movie savedMovie =  movieRepository.save(newMovie);

        return modelMapper.map(savedMovie, MovieDTO.class);
    }

    public MovieDTO updateMovie(long movieId,MovieDTO movieDTO){



        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));

        movieRepository.findMovieByName(movieDTO.getName())
                        .filter(m->!m.getMovieId().equals(movieId))
                        .ifPresent(m-> {
                            throw new APIException("Movie already exists");
                        });


        modelMapper.map(movieDTO, movie);


        // Always override starring — even if it's empty
        movie.setStarring(movieDTO.getStarring());

        Movie updatedMovie = movieRepository.save(movie);
        return modelMapper.map(updatedMovie, MovieDTO.class);


    }

    @Transactional
    public APIResponse deleteMovie(long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));

        showRepository.deleteAllByMovie(movie);
        movieRepository.delete(movie);

        return new APIResponse("Movie Deleted Successfully", true);
    }


}
