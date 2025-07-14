package it.epicode.MindWander.service;

import it.epicode.MindWander.dto.UserDto;
import it.epicode.MindWander.enumeration.Role;
import it.epicode.MindWander.exception.ForbiddenException;
import it.epicode.MindWander.exception.NotFoundException;
import it.epicode.MindWander.model.User;
import it.epicode.MindWander.repository.StrutturaRepository;
import it.epicode.MindWander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRuoli(Set.of(Role.ADMIN));
        return userRepository.save(user);
    }

    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserDto userDto, User authenticatedUser) throws NotFoundException, ForbiddenException {
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("Non sei autorizzato a modificare questo profilo.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));

        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id, User authenticatedUser) throws NotFoundException, ForbiddenException {
        boolean isAdmin = authenticatedUser.getRuoli().stream()
                .anyMatch(role -> role.equals(Role.ADMIN));

        if (!isAdmin && !authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("Non sei autorizzato a cancellare questo profilo.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));

        userRepository.delete(user);
    }

    public User updateUserRoles(Long id, Set<Role> addRoles, Set<Role> removeRoles) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con id: " + id));

        Set<Role> ruoli = new HashSet<>(user.getRuoli()); // copia mutabile

        if (addRoles != null) {
            ruoli.addAll(addRoles);
        }

        if (removeRoles != null) {
            ruoli.removeAll(removeRoles);
        }

        if (ruoli.isEmpty()) {
            throw new IllegalArgumentException("Un utente deve avere almeno un ruolo.");
        }

        user.setRuoli(ruoli);
        return userRepository.save(user);
    }
}