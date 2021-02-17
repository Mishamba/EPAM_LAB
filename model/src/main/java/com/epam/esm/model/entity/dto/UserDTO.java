package com.epam.esm.model.entity.dto;

import com.epam.esm.model.entity.User;
import org.springframework.hateoas.RepresentationModel;

public class UserDTO extends RepresentationModel<UserDTO> {
    private final int id;
    private final String username;

    protected UserDTO(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserDTO createFromUser(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getName());
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
