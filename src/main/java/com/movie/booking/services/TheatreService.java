package com.movie.booking.services;

import com.movie.booking.exceptions.APIException;
import com.movie.booking.exceptions.ResourceNotFoundException;
import com.movie.booking.models.Theatre;
import com.movie.booking.payloads.*;
import com.movie.booking.repositories.ShowRepository;
import com.movie.booking.repositories.TheatreRepository;
import com.movie.booking.utils.PaginationUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository, ShowRepository showRepository, ModelMapper modelMapper) {
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
        this.modelMapper = modelMapper;
    }

    public TheatreResponse getAllTheatres(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {


        PaginatedResponse<TheatreDTO> paginatedResponse = PaginationUtils.buildPaginatedResponse(
                theatreRepository,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder,
                modelMapper,
                Theatre.class,
                TheatreDTO.class
        );

        return  modelMapper.map(paginatedResponse, TheatreResponse.class);
    }

    public TheatreDTO getTheatreById(long theatreId) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre", "theatreId", theatreId));

        return modelMapper.map(theatre , TheatreDTO.class);
    }

    public TheatreDTO addTheatre(TheatreDTO theatreDTO) {

        Theatre newTheatre = modelMapper.map(theatreDTO, Theatre.class);

        theatreRepository.findTheatreByName(newTheatre.getName())
                .ifPresent((t)->{
                    throw new APIException("Theatre already exists");
                });

        Theatre savedTheatre = theatreRepository.save(newTheatre);

        return modelMapper.map(savedTheatre , TheatreDTO.class);
    }

    public TheatreDTO updateTheatre(long theatreId, TheatreDTO theatreDTO) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre", "theatreId", theatreId));

        theatreRepository.findTheatreByName(theatreDTO.getName())
                .filter(t -> t.getTheatreId() != theatreId)
                .ifPresent(t -> {
                    throw new APIException("Theatre already exists");
                });

        modelMapper.map(theatreDTO, theatre);

        Theatre updatedTheatre = theatreRepository.save(theatre);

        return modelMapper.map(updatedTheatre, TheatreDTO.class);
    }


    @Transactional
    public APIResponse deleteTheatre(long theatreId) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre", "theatreId", theatreId));

        showRepository.deleteAllByTheatre(theatre);
        theatreRepository.delete(theatre);

        return new APIResponse("Theatre Deleted Successfully" , true);

    }
}
