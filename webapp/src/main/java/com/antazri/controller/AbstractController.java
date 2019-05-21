package com.antazri.controller;

import com.antazri.generated.loan.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * La classe AbstractController fournit des méthodes utilisables par l'ensemble des Controller
 */
public class AbstractController {

    private static final Logger logger = LogManager.getLogger(AbstractController.class);

    /**
     * La méthode returnError permet de centraliser la redirection vers une page d'erreur
     * @param pMessage est un String définissant le message à passer à l'utilisateur
     * @return un objet ModelAndView correspondant à la page
     */
    protected ModelAndView returnError(String pMessage) {
        ModelAndView mv = new ModelAndView("redirect:/error");
        mv.addObject("message", pMessage);
        return mv;
    }

    /**
     * La méthode checkSession permet de vérifier si la session de l'utilisateur contient bien un Token et donc
     * que l'utilisateur est bien connecté
     * @param pRequest est la requête HTTP envoyé au servlet par l'utilisateur
     * @return un boolean spécifiant si la condition est remplie
     */
    protected boolean checkSession(HttpServletRequest pRequest) {
        if (pRequest.getSession().getAttribute("token") != null) {
            return true;
        }

        return false;
    }

    /**
     * La méthode getSessionMemberToLoanManagement permet de convertir l'objet Member en session en objet Member
     * utilisable par le service web dédié aux prêts de livre
     * @param pRequest est la requête HTTP envoyé au servlet par l'utilisateur
     * @return un objet Member utilisable par LoanManagementService
     */
    protected Member getSessionMemberToLoanManagement(HttpServletRequest pRequest) {
        Member vMember = new Member();
        BeanUtils.copyProperties(pRequest.getSession().getAttribute("member"), vMember);
        return vMember;
    }
}
