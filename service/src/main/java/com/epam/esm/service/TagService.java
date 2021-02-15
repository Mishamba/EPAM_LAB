package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.dto.TagDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<TagDTO> findAllTags(PaginationData paginationData) throws ServiceException;
    TagDTO findTagById(int id);
    void createTag(Tag tag);
    void deleteTag(int id);
}
