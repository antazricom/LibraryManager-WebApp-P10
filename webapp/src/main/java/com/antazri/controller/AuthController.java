package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.auth.DoLoginResponse;
import com.antazri.service.impl.AuthenticationClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * La classe AuthController agrège la gestion des requêtes sur le chemin "/auth"
 */
@RequestMapping("/auth")
@Scope("session")
@Controller
public class AuthController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthenticationClientService authenticationClientService;

    /**
     * La méthode authDefault permet de gérer le renvoie vers une page par défaut
     * @param pModel
     * @return un objet ModelAndView correspondant à la page par défaut
     */
    @RequestMapping
    public ModelAndView authDefault(ModelMap pModel) {
        return getAuthPage();
    }

    /**
     * La méthode getAuthSection gère la requête à la racine de la section Auth
     * @param pModel
     * @return un objet ModelAndView correspondant à la page racine de Auth
     */
    @RequestMapping("/")
    public ModelAndView getAuthSection(ModelMap pModel) {
        return getAuthPage();
    }

    /**
     * La méthode toAuthenticate gère l'action d'envoi du formulaire d'authentification
     * @param pLogin est l'objet DoLoginRequest qui sera encore au web service
     * @param pBindingResult gère les erreurs qui peuvent être levées par l'utilisateur dans l'action du formulaire
     * @param pRequest contient la requête HTTP
     * @param pModel
     * @return un objet ModelAndView correspondant à la page HOME
     */
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ModelAndView toAuthenticate(@ModelAttribute("identifiants") DoLoginRequest pLogin, BindingResult pBindingResult, HttpServletRequest pRequest, ModelMap pModel) {
        DoLoginResponse vResponse;

        try {
            vResponse = authenticationClientService.doLogin(pLogin);
        } catch (Exception pE) {
            return new ModelAndView("redirect:/auth", "message", "Erreur de login");
        }

        pRequest.getSession().setAttribute("token", vResponse.getToken());
        pRequest.getSession().setAttribute("member", vResponse.getMember());

        return new ModelAndView("redirect:/home");
    }

    /**
     * La méthode getAuthPage gère le renvoie vers la page principale de la section
     * @return un objet ModelAndView correspondant à la page principale
     */
    private ModelAndView getAuthPage() {
        return new ModelAndView("index", "identifiants", new DoLoginRequest());
    }

    /**
     * La méthode getLogout gère la requête vers l'url /logout pour effectuer la déconnexion de l'utilisateur en
     * supprimant sa session
     * @param pRequest contient la requête HTTP
     * @param pStatus est un objet SessionStatus fourni par Spring
     * @param pModel
     * @return un objet ModelAndView correspondant à la page par défaut
     */
    @RequestMapping("/logout")
    public ModelAndView getLogout(HttpServletRequest pRequest, SessionStatus pStatus, Model pModel) {
        pStatus.setComplete();
        pRequest.getSession().removeAttribute("member");
        pRequest.getSession().removeAttribute("token");

        return new ModelAndView("redirect:/");
    }
}
