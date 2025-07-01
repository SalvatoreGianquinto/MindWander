package it.epicode.MindWander.controller;

import it.epicode.MindWander.dto.RoleUpdateDto;
import it.epicode.MindWander.dto.UserDto;
import it.epicode.MindWander.exception.ForbiddenException;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.service.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) throws NotFoundException {
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("Non puoi vedere il profilo di un altro utente");
        }
        return userService.getUser(id);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody @Validated UserDto userDto,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal User authenticatedUser)
            throws NotFoundException, ForbiddenException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (s, e) -> s + e));
        }
        return userService.updateUser(id, userDto, authenticatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id,
                           @AuthenticationPrincipal User authenticatedUser)
            throws NotFoundException, ForbiddenException {
        userService.deleteUser(id, authenticatedUser);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/roles")
    public User updateUserRoles(@PathVariable Long id,
                                @RequestBody RoleUpdateDto roleUpdateDto) throws NotFoundException {
        return userService.updateUserRoles(id, roleUpdateDto.getAddRoles(), roleUpdateDto.getRemoveRoles());
    }
}
