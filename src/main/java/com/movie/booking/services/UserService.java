package com.movie.booking.services;

import com.movie.booking.exceptions.APIException;
import com.movie.booking.exceptions.ResourceNotFoundException;
import com.movie.booking.models.AppUser;
import com.movie.booking.payloads.*;
import com.movie.booking.repositories.AppUserRepository;
import com.movie.booking.repositories.ShowRepository;
import com.movie.booking.utils.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final ShowRepository showRepository;

    UserService(AppUserRepository appUserRepository, ModelMapper modelMapper, ShowRepository showRepository) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.showRepository = showRepository;
    }

    public UserResponse getUsers(Integer pageNumber, Integer pageSize , String sortBy, String sortOrder) {

        PaginatedResponse<UserDTO> paginatedResponse = PaginationUtils.buildPaginatedResponse(
                appUserRepository,
                pageNumber,
                pageSize,
                sortBy,
                sortOrder,
                modelMapper,
                AppUser.class,
                UserDTO.class
        );

        return modelMapper.map(paginatedResponse, UserResponse.class);
    }

    //Admin
    public UserDTO getUser(Long userId) {

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        return modelMapper.map(appUser, UserDTO.class);
    }

    //Admin
    public UserDTO updateUser(Long userId, UserUpdateDTO userDTO) {

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        appUserRepository.findByEmail(userDTO.getEmail())
                .filter(u->!u.getUserId().equals(userId))
                .ifPresent(u -> {
                    throw new APIException("Email already in Use");
                });


        modelMapper.map(userDTO, appUser);

        AppUser updatedUser = appUserRepository.save(appUser);

        return modelMapper.map(updatedUser, UserDTO.class);

    }

    //Admin
    public APIResponse deleteUser(Long userId) {

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        appUserRepository.delete(appUser);

        return new APIResponse("User Deleted Successfully", true);

        //Delete their tickets from upcoming shows , add number of seats available to the respective show , implemented later
    }


    //Public
    public UserDTO createUser(UserCreateDTO userCreateDTO) {

        if (userCreateDTO.getEmail() != null){
            appUserRepository.findByEmail(userCreateDTO.getEmail())
                    .ifPresent(appUser -> {
                        throw new APIException("Email already in Use");
                    });
        }


        AppUser appUser = modelMapper.map(userCreateDTO, AppUser.class);

        AppUser savedUser = appUserRepository.save(appUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO getLoggedInUserProfile() {
        // TEMP: Mock user ID for testing
        Long mockUserId = 1L;

        AppUser user = appUserRepository.findById(mockUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", mockUserId));

        return modelMapper.map(user, UserDTO.class);
    }


    public UserDTO updateMyProfile(UserUpdateDTO userInput) {
        Long mockUserId = 1L;

        AppUser existingUser = appUserRepository.findById(mockUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", mockUserId));

        if (userInput.getEmail() != null) {

            appUserRepository.findByEmail(userInput.getEmail())
                    .filter(u -> !u.getUserId().equals(mockUserId))
                    .ifPresent(u -> {
                        throw new APIException("Email already in use");
                    });

        }

        modelMapper.map(userInput, existingUser);

        AppUser updatedUser = appUserRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public APIResponse deleteMyProfile() {

        Long userId = 1L; // mock

        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        appUserRepository.delete(appUser);

        return new APIResponse("User Deleted Successfully", true);
    }

    public APIResponse changeMyPassword(ChangePasswordDTO dto) {
        Long mockUserId = 1L;

        AppUser user = appUserRepository.findById(mockUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", mockUserId));

        // Compare current password (plain for now, use encoder later)
        if (!user.getPassword().equals(dto.getCurrentPassword())) {
            throw new APIException("Incorrect current password");
        }


        user.setPassword(dto.getNewPassword());
        appUserRepository.save(user);

        return new APIResponse("Password changed successfully", true);
    }

}
