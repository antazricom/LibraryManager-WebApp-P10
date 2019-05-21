package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.book.ConvertException;
import com.antazri.generated.book.FindByTitleRequest;
import com.antazri.generated.book.FindByTitleResponse;
import com.antazri.service.impl.BookManagementClientService;
import com.antazri.utils.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * La classe SearchController permet de gérer les requêtes sur l'URL "./search"
 */
@Scope("session")
@RequestMapping("/search")
@Controller
public class SearchController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(SearchController.class);

    @Autowired
    private BookManagementClientService bookManagementClientService;

    /**
     * La méthode getSearchDefault renvoie vers la page par défaut à la racine de la section "/search"
     * @param pModel
     * @return un objet ModelAndView correspondant à la page par défaut
     */
    @RequestMapping
    public ModelAndView getSearchDefault(Model pModel) {
        return new ModelAndView("redirect:/search/");
    }

    /**
     * La méthode getSearch permet le renvoie vers la page par défaut quand l'utilisateur fait une requête vers la
     * racine de la section
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale
     */
    @GetMapping("/")
    public ModelAndView getSearch(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        return new ModelAndView("search/search", "request", new FindByTitleRequest());
    }

    /**
     * La méthode searchRequest récupère la requête de l'utilisateur et utilise la chaîne de caractères récupérée pour
     * effectuer une demande au web service et retourner le résultat à l'utilisateur sur la page
     * @param pSearchRequest est un objet FindByTitle utilisé pour la requête du web service
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page de résultat
     */
    @GetMapping("/results")
    public ModelAndView searchRequest(@ModelAttribute("request") FindByTitleRequest pSearchRequest, HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindByTitleResponse vResponse;

        try {
            vResponse = bookManagementClientService.findByTitle(pSearchRequest);
        } catch (ConvertException pE) {
            logger.error("searchRequest: Erreur dans la récupération de l'objet via ID");
            return returnError(Message.getText().getString("message.error.notvalid.element"));
        }

        ModelAndView mv = new ModelAndView("search/results");
        mv.addObject("request", pSearchRequest);
        mv.addObject("results", vResponse.getBooks());

        return mv;
    }
}
