package com.antazri.service.impl;

import com.antazri.generated.author.*;
import org.springframework.stereotype.Service;

@Service
public class AuthorManagementClientService extends AuthorManagementService implements AuthorPort {

    @Override
    public AddAuthorResponse addAuthor(AddAuthorRequest parameters) throws ConvertException {
        return getAuthorManagementPort().addAuthor(parameters);
    }

    @Override
    public UpdateAuthorResponse updateAuthor(UpdateAuthorRequest parameters) throws ConvertException {
        return getAuthorManagementPort().updateAuthor(parameters);
    }

    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        return getAuthorManagementPort().findById(parameters);
    }

    @Override
    public DeleteAuthorResponse deleteAuthor(DeleteAuthorRequest parameters) throws ConvertException {
        return getAuthorManagementPort().deleteAuthor(parameters);
    }

    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        return getAuthorManagementPort().findAll(parameters);
    }
}
