package br.ufpb.dcx.dsc.apiYuGiOh.controller;

import br.ufpb.dcx.dsc.apiYuGiOh.dto.UserDTO;
import br.ufpb.dcx.dsc.apiYuGiOh.dto.UserDTOResponse;
import br.ufpb.dcx.dsc.apiYuGiOh.model.User;
import br.ufpb.dcx.dsc.apiYuGiOh.service.DeckService;
import br.ufpb.dcx.dsc.apiYuGiOh.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@Validated
public class UserController {
    private ModelMapper modelMapper;
    private final UserService userService;
    private final DeckService deckService;

    public UserController(ModelMapper modelMapper, UserService userService, DeckService deckService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.deckService = deckService;
    }

    @GetMapping("/user")
    public List<UserDTOResponse> listUsers() {
        return userService.listUsers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public UserDTOResponse getUser(@PathVariable Long userId) {
        User u = userService.getUser(userId);
        return convertToDTO(u);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTOResponse createUser(@Valid @RequestBody UserDTO userDTO) {
        User u = convertToEntity(userDTO);
        User saved = userService.createUser(u);
        return convertToDTO(saved);
    }

    @PutMapping("/user/{userId}")
    public UserDTOResponse updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        User u = convertToEntity(userDTO);
        User userUpdated = userService.updateUser(userId, u);
        return convertToDTO(userUpdated);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    private UserDTOResponse convertToDTO(User u) {
        return modelMapper.map(u, UserDTOResponse.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
