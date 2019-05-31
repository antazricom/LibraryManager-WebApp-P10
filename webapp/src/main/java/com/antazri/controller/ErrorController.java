package com.antazri.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
@Scope("session")
public class ErrorController extends AbstractController {

    /**
     * Cette méthode récupère l'accès à la page 'error' et redirige l'utilisateur vers la page à la racine
     * @param pModel
     * @param pMessage
     * @return
     */
    @RequestMapping
    public ModelAndView getError(Model pModel, @ModelAttribute("message") String pMessage) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", pMessage);
        return modelAndView;
    }


}
