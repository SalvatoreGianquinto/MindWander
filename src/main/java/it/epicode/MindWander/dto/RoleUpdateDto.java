package it.epicode.MindWander.dto;

import it.epicode.MindWander.enumeration.Role;
import lombok.Data;

import java.util.Set;

@Data
public class RoleUpdateDto {
    private Set<Role> addRoles;
    private Set<Role> removeRoles;

}
