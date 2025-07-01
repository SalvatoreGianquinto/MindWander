package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.LoginDto;
import it.epicode.MindWander.dto.UserDto;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.exception.ValidationException;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.service.AuthService;
import it.epicode.MindWander.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }

        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }

        return authService.login(loginDto);
    }
}
