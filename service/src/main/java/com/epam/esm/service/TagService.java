package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.util.entity.PaginationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<Tag> findAllTags(PaginationData paginationData) throws ServiceException;
    Tag findTagById(int id) throws ServiceException;
    boolean createTag(Tag tag) throws ServiceException;
    boolean deleteTag(int id) throws ServiceException;
}
