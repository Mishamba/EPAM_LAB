package com.epam.esm.model.entity.dto;

import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.User;
import org.springframework.hateoas.RepresentationModel;

public class UserDTO extends RepresentationModel<UserDTO> {
    private final int id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Role role;

    public UserDTO(int id, String email, String firstName, String lastName, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }


    public static UserDTO createFromUser(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole());
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }
}
