package com.epam.esm.model.entity.dto;

import com.epam.esm.model.entity.Tag;
import org.springframework.hateoas.RepresentationModel;

public class TagDTO extends RepresentationModel<TagDTO> {
    private final int id;
    private final String name;

    protected TagDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagDTO createFromTag(Tag tag) {
        if (tag == null) {
            return null;
        }

        return new TagDTO(tag.getId(), tag.getName());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
