package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.author.*;
import com.antazri.generated.book.FindByAuthorRequest;
import com.antazri.generated.book.FindByAuthorResponse;
import com.antazri.service.impl.AuthorManagementClientService;
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

/**
 * La classe AuthorController gère les requêtes et accès aux pages de la section "./authors"
 */
@RequestMapping("/authors")
@Scope("session")
@Controller
public class AuthorController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(AuthorController.class);

    @Autowired
    private AuthorManagementClientService authorManagementClientService;

    @Autowired
    private BookManagementClientService bookManagementClientService;

    /**
     * La méthode getDefaultAuthors gère les requêtes et le renvoi vers la page principale de la section
     * @param pModel
     * @return un objet ModelAndView correspondant à la page par principale
     */
    @RequestMapping
    public ModelAndView getDefaultAuthors(Model pModel) {
        return new ModelAndView("redirect:/authors/all");
    }

    /**
     * La méthode getAuthors gère les requêtes et le renvoi depuis la racine vers la page principale de la section
     * @param pModel
     * @return un objet ModelAndView correspondant à la page par principale
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAuthors(Model pModel) {
        return new ModelAndView("redirect:/authors/all");
    }

    /**
     * La méthode getAllAuthors gère l'accès vers la page retournant l'ensemble des instances de Author
     * @param pRequest est un objet HttpServletRequest contient la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page "authors.jsp"
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllAuthors(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindAllResponse vResponse;

        try {
            vResponse = authorManagementClientService.findAll(new FindAllRequest());
        } catch (ConvertException pE) {
            logger.error("getAllAuthors: Erreur dans le chargement des auteurs" + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        return new ModelAndView("author/authors", "authors", vResponse.getAuthors());
    }

    /**
     * La méthode getAuthorById permet de gérer l'accès et l'affichage des informations d'un Author selon son identifiant
     * renseigné dans l'URL en tant que paramètre
     * @param pId est l'identifiant de l'Author concerné
     * @param pRequest est un objet HttpServletRequest contient la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page "author.jsp"
     */
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ModelAndView getAuthorById(@PathVariable("id") int pId, HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        ModelAndView mv = new ModelAndView("author/author");
        Author vAuthor;
        FindByAuthorRequest vFindByAuthorRequest = new FindByAuthorRequest();
        FindByAuthorResponse vFindByAuthorResponse = new FindByAuthorResponse();

        if (pId < 0) {
            return returnError(Message.getText().getString("message.error.id"));
        }

        try {
            vAuthor = getAuthorFromId(pId);
        } catch (Exception pE) {
            logger.error("getAuthorById: Erreur dans la récupération de l'objet via ID");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        if (vAuthor == null) {
            return returnError(Message.getText().getString("message.error.null"));
        }

        try {
            vFindByAuthorRequest.setAuthor(toAuthorForBookManagement(vAuthor));
            vFindByAuthorResponse = bookManagementClientService.findByAuthor(vFindByAuthorRequest);
        } catch (Exception pE) {
            logger.error("getAuthorById: Erreur dans la récupération de l'objet via ID");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        mv.addObject("author", vAuthor);
        mv.addObject("books", vFindByAuthorResponse.getBooks());

        return mv;
    }

    /*public ModelAndView getAddAuthor(Model pModel) {
        return new ModelAndView("author/add", "author", new Author());
    }

    public ModelAndView postAddAuthor(@ModelAttribute("author") Author pAuthor, BindingResult pBindingResult, Model pModel) {
        AddAuthorRequest vRequest = new AddAuthorRequest();
        AddAuthorResponse vResponse = new AddAuthorResponse();

        if (pBindingResult.hasErrors()) {
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        if (StringUtils.isAnyBlank(pAuthor.getFirstname(), pAuthor.getLastname())) {
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        try {
            vRequest.setAuthor(pAuthor);
            vResponse = authorManagementClientService.addAuthor(vRequest);
        } catch (ConvertException pE) {
            logger.error("postAddAuthor: Erreur dans l'objet Author envoyé dans la requête, " + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.null"));
        }

        return new ModelAndView("redirect:./details/" + vResponse.getAuthor().getId(), "author", vResponse.getAuthor());
    }

    public ModelAndView getUpdateAuthor(@PathVariable("id") int pId, Model pModel) {
        Author vAuthor;

        try {
            vAuthor = getAuthorFromId(pId);
        } catch (Exception pE) {
            logger.error("postDeleteAuthor: Erreur dans la récupération de l'objet via ID");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        return new ModelAndView("author/update", "author", vAuthor);
    }

    public ModelAndView postUpdateAuthor(@ModelAttribute("author") Author pAuthor, BindingResult pBindingResult, Model pModel) {
        UpdateAuthorRequest vRequest = new UpdateAuthorRequest();
        UpdateAuthorResponse vResponse = new UpdateAuthorResponse();

        if (StringUtils.isAnyBlank(pAuthor.getFirstname(), pAuthor.getLastname())) {
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        try {
            vRequest.setAuthor(pAuthor);
            vResponse = authorManagementClientService.updateAuthor(vRequest);
        } catch (ConvertException pE) {
            logger.error("postUpdateAuthor: Erreur dans l'objet Author envoyé dans la requête, " + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.null"));
        }

        return new ModelAndView("redirect:./details/" + vResponse.getAuthor().getId(), "author", vResponse.getAuthor());
    }

    public ModelAndView putDeleteAuthor(@PathVariable("id") int pId, Model pModel) {
        DeleteAuthorRequest vRequest = new DeleteAuthorRequest();
        DeleteAuthorResponse vResponse = new DeleteAuthorResponse();

        try {
            vRequest.setAuthor(getAuthorFromId(pId));
        } catch (Exception pE) {
            logger.error("postDeleteAuthor: Erreur dans la récupération de l'objet via ID");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        try {
            vResponse = authorManagementClientService.deleteAuthor(vRequest);
        } catch (ConvertException pE) {
            logger.error("postDeleteAuthor: Erreur dans l'objet Author envoyé dans la requête, " + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        if (!vResponse.isDeleted()) {
            logger.error("postDeleteAuthor: Erreur dans la requête de suppression de l'objet Author");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        return new ModelAndView("redirect:/authors/all");
    }*/

    /**
     * La méthode getAuthorFromId gère la requête envoyée au service web pour récupérer un objet Author et ses propriétés
     * @param pId est un Integer définissant l'identifiant unique de l'objet Author
     * @return un objet Author
     * @throws Exception
     */
    private Author getAuthorFromId(int pId) throws Exception {
        FindByIdRequest vFindByIdRequest = new FindByIdRequest();
        FindByIdResponse vFindByIdResponse;

        if (pId < 0) {
            throw new Exception(Message.getText().getString("message.error.id"));
        }

        vFindByIdRequest.setId(pId);

        try {
            vFindByIdResponse = authorManagementClientService.findById(vFindByIdRequest);
        } catch (ConvertException pE) {
            logger.error("getAllAuthors: Erreur dans le chargement des auteurs, " + pE.getFaultInfo().getFault().getFaultString());
            throw new Exception(Message.getText().getString("message.error.notvalid.element"));
        }

        return vFindByIdResponse.getAuthor();
    }

    /**
     * La méthode toAuthorForBookManagement permet de convertir un objet Author généré par le web service
     * AuthorManagement en objet Author pour le service BookManagement
     * @param pAuthor est un objet généré par le service web AuthorManagement
     * @return un objet Author configuré pour fonctionner avec le service BookManagement
     */
    private com.antazri.generated.book.Author toAuthorForBookManagement(Author pAuthor) {
        com.antazri.generated.book.Author vAuthor = new com.antazri.generated.book.Author();
        vAuthor.setId(pAuthor.getId());
        vAuthor.setFirstname(pAuthor.getFirstname());
        vAuthor.setLastname(pAuthor.getLastname());

        return vAuthor;
    }

}
