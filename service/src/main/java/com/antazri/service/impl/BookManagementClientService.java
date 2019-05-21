package com.antazri.service.impl;

import com.antazri.generated.book.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookManagementClientService extends BookManagementService implements BookPort {

    Logger logger = LogManager.getLogger(BookManagementClientService.class);

    @Override
    public FindAllResponse findAll(FindAllRequest parameters) throws ConvertException {
        return getBookManagementPort().findAll(parameters);
    }

    @Override
    public FindByAuthorResponse findByAuthor(FindByAuthorRequest parameters) throws ConvertException {
        return getBookManagementPort().findByAuthor(parameters);
    }

    @Override
    public AddBookResponse addBook(AddBookRequest parameters) throws ConvertException {
        return getBookManagementPort().addBook(parameters);
    }

    @Override
    public FindByTitleResponse findByTitle(FindByTitleRequest parameters) throws ConvertException {
        return getBookManagementPort().findByTitle(parameters);
    }

    @Override
    public FindByIdResponse findById(FindByIdRequest parameters) throws ConvertException {
        return getBookManagementPort().findById(parameters);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest parameters) throws ConvertException {
        return getBookManagementPort().updateBook(parameters);
    }

    @Override
    public FindByCategoryResponse findByCategory(FindByCategoryRequest parameters) throws ConvertException {
        return getBookManagementPort().findByCategory(parameters);
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest parameters) throws ConvertException {
        return getBookManagementPort().deleteBook(parameters);
    }

    @Override
    public GetAvailableCopiesResponse getAvailableCopies(GetAvailableCopiesRequest parameters) throws BookException {
        return getBookManagementPort().getAvailableCopies(parameters);
    }

    protected Map<Book, Integer> getBookAvailability(List<Book> pBooks) throws BookException{
        Map<Book, Integer> vListBooksWithAvailability = new HashMap<>();

        for (Book book : pBooks) {
            GetAvailableCopiesRequest vRequest = new GetAvailableCopiesRequest();
            GetAvailableCopiesResponse vResponse;

            try {
                vRequest.setBook(book);
                vResponse = getAvailableCopies(vRequest);
                vListBooksWithAvailability.put(book, vResponse.getCopies());
            } catch (BookException pE) {
                logger.error("Erreur de récupération des données BOOK ou INTEGER (getBookWithAvailability)");
                throw new BookException("Erreur de récupération des données BOOK ou INTEGER (getBookWithAvailability)");
            }
        }

        return vListBooksWithAvailability;
    }
}
