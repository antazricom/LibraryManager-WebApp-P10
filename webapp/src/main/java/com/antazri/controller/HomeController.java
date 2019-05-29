package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * La classe HomeController gère la section accessible à l'url "./home"
 */
@RequestMapping("/")
@Scope("session")
@Controller
public class HomeController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    /**
     * La méthode getAccess vérifie qu'il y a les éléments nécessaires dans la session pour l'accès aux sections
     * restreintes aux utilisateurs connectés
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page racine de Auth
     */
    @RequestMapping
    public ModelAndView getAccess(HttpServletRequest pRequest, Model pModel) {

        if (checkSession(pRequest)) {
            return new ModelAndView("redirect:/home");
        }

        return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
    }

    /**
     * La méthode getHome gère le renvoi vers la page principale de la section "/home"
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page racine de Home
     */
    @RequestMapping(value = "home")
    public ModelAndView getHome(HttpServletRequest pRequest, Model pModel) {

        if (checkSession(pRequest)) {
            return new ModelAndView("redirect:/loans/");
        }

        return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
    }

    /**
     * La méthode getError gère le renvoi de l'utilisateur vers une page d'erreur globale pour l'application
     * @param pModel
     * @param pMessage
     * @return un objet ModelAndView correspondant à la page d'erreur
     */
    @RequestMapping(value = "error")
    public ModelAndView getError(Model pModel, @ModelAttribute("message") String pMessage) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", pMessage);
        return modelAndView;
    }
}
