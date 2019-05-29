package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.loan.*;
import com.antazri.service.impl.LoanManagementClientService;
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
 * La classe LoanController gère l'accès et l'affichage des pages liées à la rubrique "./loans"
 */
@RequestMapping("/loans")
@Scope("session")
@Controller
public class LoanController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(LoanController.class);

    @Autowired
    private LoanManagementClientService loanManagementClientService;

    /**
     * La méthode getLoansDefault récupère la requête et la redirige vers la page principale de la rubrique
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loans.jsp"
     */
    @RequestMapping
    public ModelAndView getLoansDefault(Model pModel) {
        return new ModelAndView("redirect:/loans/all");
    }

    /**
     * La méthode getLoans récupère la requête et la redirige vers la page principale de la rubrique
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loans.jsp"
     */
    @RequestMapping("/")
    public ModelAndView getLoans(Model pModel) {
        return new ModelAndView("redirect:/loans/all");
    }

    /**
     * La méthode getAllLoans renvoi la requête vers une page affichant l'ensemble des instances de Loan récupérées
     * dans la base de données via le web service
     * @param pHttpRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loans.jsp"
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllLoans(HttpServletRequest pHttpRequest, Model pModel) {
        if (!checkSession(pHttpRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindByMemberRequest vRequest = new FindByMemberRequest();
        vRequest.setMember(getSessionMemberToLoanManagement(pHttpRequest));
        FindByMemberResponse vResponse;

        try {
            vResponse = loanManagementClientService.findByMember(vRequest);
        } catch (ConvertException pE) {
            logger.error("getAllLoans: Erreur dans la récupération des objets Loan");

            return new ModelAndView("redirect:/error", "message", pE.getFaultInfo().getFault().getFaultString());
        }

            return new ModelAndView("loan/loans", "loans", vResponse.getLoans());
    }

    /**
     * La méthode getLoanDetails récupère l'identifiant passé en paramètre de l'URL pour renvoyé vers la page
     * "loans.jsp" contenant les propriétés de l'objet Loan récupéré avec l'ID via le service web
     * @param pId est un Integer définissant l'identifiant unique de l'instance de Loan
     * @param pHttpRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loan.jsp" avec un objet Loan en attribut
     */
    @RequestMapping("/details/{id}")
    public ModelAndView getLoanDetails(@PathVariable("id") int pId, HttpServletRequest pHttpRequest, Model pModel) {
        if (!checkSession(pHttpRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        Loan vLoan;

        try {
            vLoan = getLoanById(pId);
        } catch (NullPointerException pE) {
            logger.error("getLoanDetails: Erreur dans la récupération de Loan via l'ID : " + pId);
            return returnError(Message.getText().getString("message.error.null"));
        }


        if (getSessionMemberToLoanManagement(pHttpRequest).getId() != vLoan.getMember().getId()) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        return new ModelAndView("loan/loan", "loan", vLoan);
    }

    /**
     * La méthode getExtendLoan est utilisée pour appeler l'opération "ExtendLoan" du service LoanManagement
     * @param pId est un Integer définissant l'identifiant unique de l'instance de Loan
     * @param pHttpRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loan.jsp" avec un objet Loan en attribut
     */
    @RequestMapping("/extend/{id}")
    public ModelAndView getExtendLoan(@PathVariable("id") int pId, HttpServletRequest pHttpRequest, Model pModel) {
        if (!checkSession(pHttpRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        ExtendLoanRequest vRequest = new ExtendLoanRequest();
        ExtendLoanResponse vResponse;

        Loan vLoan;

        try {
            vLoan = getLoanById(pId);
        } catch (NullPointerException pE) {
            logger.error("getLoanDetails: Erreur dans la récupération de Loan via l'ID : " + pId);
            return returnError(Message.getText().getString("message.error.null"));
        }

        if (getSessionMemberToLoanManagement(pHttpRequest).getId() != vLoan.getMember().getId()) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        if (vLoan.isExtended()) {
            return returnError(Message.getText().getString("message.error.already.extended"));
        }

        vRequest.setLoan(vLoan);

        try {
            vResponse = loanManagementClientService.extendLoan(vRequest);
        } catch (ConvertException pConvertEx) {
            logger.error("postExtendLoan/ConvertException: Erreur dans l'extension du prêt");
            return returnError(Message.getText().getString("message.error.default"));
        } catch (ExtendException pExtendEx) {
            logger.error("postExtendLoan/ExtendException: Erreur dans l'extension du prêt");
            return returnError(Message.getText().getString("message.error.default"));
        }

        return new ModelAndView("redirect:/loans/details/" + vLoan.getId(), "loan", vResponse.getLoan());
    }

    /**
     * La méthode getExtendLoan est utilisée pour appeler l'opération "ReturnLoan" du service LoanManagement
     * @param pId est un Integer définissant l'identifiant unique de l'instance de Loan
     * @param pHttpRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "loan.jsp" avec un objet Loan en attribut
     */
    @RequestMapping("/return/{id}")
    public ModelAndView getReturnLoan(@PathVariable("id") int pId, HttpServletRequest pHttpRequest, Model pModel) {
        if (!checkSession(pHttpRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        ReturnLoanRequest vReturnLoanRequest = new ReturnLoanRequest();
        ReturnLoanResponse vReturnLoanResponse;

        Loan vLoan;

        try {
            vLoan = getLoanById(pId);
        } catch (NullPointerException pE) {
            logger.error("getLoanDetails: Erreur dans la récupération de Loan via l'ID : " + pId);
            return returnError(Message.getText().getString("message.error.null"));
        }

        if (getSessionMemberToLoanManagement(pHttpRequest).getId() != vLoan.getMember().getId()) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        if (vLoan.isReturned()) {
            return returnError(Message.getText().getString("message.error.already.extended"));
        }

        vReturnLoanRequest.setLoan(vLoan);

        try {
            vReturnLoanResponse = loanManagementClientService.returnLoan(vReturnLoanRequest);
        } catch (ConvertException pConvertEx) {
            logger.error("postReturnLoan/ConvertException: Erreur dans le retour du prêt");
            return returnError(Message.getText().getString("message.error.default"));
        } catch (ReturnException pReturnEx) {
            logger.error("postReturnLoan/ConvertException: Erreur dans le retour du prêt");
            return returnError(Message.getText().getString("message.error.default"));
        }

        return new ModelAndView("redirect:/loans/details/" + vLoan.getId(), "loan", vReturnLoanResponse.getLoan());
    }

    /**
     * La méthode getLoanById utilise l'integer passé en paramètre pour retourner un objet Loan selon son identifiant
     * unique via le service web LoanManagement
     * @param pId est un Integer correspondant à l'identifiant unique de l'instance de Loan à récupérer
     * @return un objet Loan récupéré via le web service contenant l'ensemble des informations à afficher sur la page
     * @throws NullPointerException
     */
    private Loan getLoanById(int pId) throws NullPointerException {
        FindByIdRequest vRequest = new FindByIdRequest();
        FindByIdResponse vResponse;

        if (pId < 0) {
            throw new NullPointerException();
        }

        vRequest.setId(pId);

        try {
            vResponse = loanManagementClientService.findById(vRequest);
        } catch (ConvertException pE) {
            logger.error("getLoanById: Erreur dans la récupération de Loan via l'ID : " + pId);
            throw new NullPointerException("getLoanDetails: Erreur dans la récupération de Loan via l'ID : " + pId);
        }

        return vResponse.getLoan();
    }

}
