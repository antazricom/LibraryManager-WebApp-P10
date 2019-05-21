package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.book.*;
import com.antazri.service.impl.BookManagementClientService;
import com.antazri.utils.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe BookController gère les requeêtes et accès aux pages de la section "./books"
 */
@RequestMapping("/books")
@Scope("session")
@Controller
public class BookController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    private BookManagementClientService bookManagementClientService;

    /**
     * La méthode getDefaultBooks gère la redirection vers la page principale de la rubrique
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "books.jsp"
     */
    @RequestMapping
    public ModelAndView getDefaultBooks(Model pModel) {
        return new ModelAndView("redirect:/books/all");
    }

    /**
     * La méthode getBooks gère la redirection depuis la racine de la rubrique vers la page principale de la rubrique
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "books.jsp"
     */
    @RequestMapping("/")
    public ModelAndView getBooks(Model pModel) {
        return new ModelAndView("redirect:/books/all");
    }

    /**
     * La méthode getAllBooks renvoie la requête de l'utilisateur vers la page "books.jsp"
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "books.jsp"
     */
    @RequestMapping("/all")
    public ModelAndView getAllBooks(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindAllResponse vResponse;

        try {
            vResponse = bookManagementClientService.findAll(new FindAllRequest());
        } catch (ConvertException pE) {
            logger.error("getAllBooks: Erreur dans la récupération des objets Book");
            return new ModelAndView("redirect:/error", "message", pE.getFaultInfo());
        }

        return new ModelAndView("book/books", "books", vResponse.getBooks());
    }

    /**
     * La méthode getBookDetails permet de gérer l'accès et l'affichage des informations d'un Book selon son identifiant
     * renseigné dans l'URL en tant que paramètre
     * @param pId est l'identifiant de l'objet Book concerné
     * @param pRequest est un objet HttpServletRequest contient la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page "book.jsp"
     */
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ModelAndView getBookDetails(@PathVariable("id") int pId, HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindByIdRequest vResquest = new FindByIdRequest();

        if (pId < 0) {
            return new ModelAndView("redirect:/error", "message", Message.getText().getString("message.error.id"));
        }

        vResquest.setId(pId);
        FindByIdResponse vResponse;

        try {
            vResponse = bookManagementClientService.findById(vResquest);
        } catch (ConvertException pE) {
            logger.error("getBookDetails: Erreur dans l'objet la requête FindById, " + pE.getFaultInfo().getFault().getFaultString());
            return new ModelAndView("redirect:/error", "message", pE.getFaultInfo());
        }

        if (vResponse.getBook() == null) {
            return new ModelAndView("redirect:/error", "message", "Aucun livre n'a été trouvé");
        }

        ModelAndView vView = new ModelAndView("book/book");
        vView.addObject("book", vResponse.getBook());
        vView.addObject("copiesAvailable", getAvailableCopies(vResponse.getBook()));

        return vView;
    }

    /*public ModelAndView getAddBook(Model pModel) {
        ModelAndView mv = new ModelAndView();

        try {
            mv.addObject("authors");
        } catch (Exception pE) {

        }
        return new ModelAndView("book/add", "book", new Book());
    }

    public ModelAndView postAddBook(@ModelAttribute Book pBook, BindingResult pBindingResult, Model pModel) {
        AddBookRequest vRequest = new AddBookRequest();
        AddBookResponse vResponse;

        if (pBindingResult.hasErrors()) {
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        if (StringUtils.isAnyBlank(pBook.getTitle())) {
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        try {
            vRequest.setBook(pBook);
            vResponse = bookManagementClientService.addBook(vRequest);
        } catch (ConvertException pE) {
            logger.error("postAddBook: Erreur dans l'objet Book envoyé dans la requête, " + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.null"));
        }
        return new ModelAndView("book/details/" + vResponse.getBook().getId(), "book", vResponse.getBook());
    } */

    /**
     * La méthode getAvailableCopies permet de récupérer le nombre de copies disponibles par livre
     * @param pBook est un objet Book
     * @return un Integer correspondant au nombre de copies disponibles
     */
    private int getAvailableCopies(Book pBook) {
        GetAvailableCopiesRequest vRequest = new GetAvailableCopiesRequest();
        GetAvailableCopiesResponse vResponse = new GetAvailableCopiesResponse();

        try {
            vRequest.setBook(pBook);
            vResponse = bookManagementClientService.getAvailableCopies(vRequest);
        } catch (BookException pE) {
            logger.error("getAvailableCopies: Erreur dans la requête de récupération des copies disponibles" +
                    "du livre avec l'ID : " + pBook.getId() );
        }

        return vResponse.getCopies();
    }

}
