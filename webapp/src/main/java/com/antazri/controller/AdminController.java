package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.auth.Member;
import com.antazri.generated.member.*;
import com.antazri.service.impl.MemberManagementClientService;
import com.antazri.utils.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    private final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private MemberManagementClientService memberManagementClientService;

    /**
     * La méthode permet de gérer l'accès au panel d'administration via pour diriger l'utilisateur vers une page
     * d'accueil par défaut
     * @param pRequest est la requête HTTP contenant les éléments de la session
     * @param pModel est le Model de la page/view JSP
     * @return un ModelAndView correspondant à la page/view JSP avec ses attributs
     */
    @GetMapping
    public ModelAndView getAdminDefault(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        return new ModelAndView("admin/admin");
    }

    /**
     * La méthode récupère la requête et la redigrige vers une vue contenant l'ensemble des occurences de Membre
     * persistées dans la base de données
     * @param pRequest est la requête HTTP envoyée
     * @param pModel
     * @return un ModelAndView correspondant à la liste de tous les membres enregistrés
     */
    @RequestMapping(value = "/members")
    public ModelAndView getMembersAdmin(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        List<com.antazri.generated.member.Member> members;

        try {
            members = memberManagementClientService.findAll(new FindAllRequest()).getMembers();
        } catch (ConvertException e) {
            return returnError("Erreur de chargement des membres");
        }

        return new ModelAndView("admin/members/members", "members", members);
    }

    /**
     * La méthode permet de récupérer la requête et la redirige vers la page de formulaire pour ajouter une
     * nouvelle entité dans la base de données
     * @param pRequest est la requête HTTP envoyée
     * @param pModel
     * @return un ModelAndView correspondant au formulaire d'ajout
     */
    @RequestMapping(value = "/members/add")
    public ModelAndView getAddMemberAdmin(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        ModelAndView modelAndView = new ModelAndView("admin/members/add");
        modelAndView.addObject("newmember", new com.antazri.generated.member.Member());
        modelAndView.addObject("confirmedPassword", "");

        return modelAndView;
    }

    /**
     * La méthode récupère les données transmise via le formulaire dans le ModelAttribute afin d'appeler le webservice
     * pour persister les données
     * @param pMember est l'objet Membre à enregistrer dans la base de données
     * @param pModel
     * @return un ModelAndView correspondant à la liste de tous les membres enregistrés
     */
    @RequestMapping(value = "/members/adding", method = RequestMethod.POST)
    public ModelAndView processAddMember(HttpServletRequest pRequest,
                                         @ModelAttribute("newmember")com.antazri.generated.member.Member pMember,
                                         Model pModel,
                                         BindingResult pBindingResult) {
        if (!checkSession(pRequest)) {
            logger.error("Not connected");
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            logger.error("Not authorized");
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        if (pBindingResult.hasErrors()) {
            logger.error("BindingResult has errors" + pBindingResult.getAllErrors().toString());
            return returnError(Message.getText().getString("message.error.default"));
        }

        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setMember(pMember);

        try {
            memberManagementClientService.addMember(addMemberRequest);
        } catch (ConvertException e) {
            logger.error("Erreur dans l'ajout de l'entité membre en paramètre de la requête : " + e.getMessage());
            return returnError(Message.getText().getString("message.error.default"));
        }

        return new ModelAndView("redirect:/admin/members");
    }

    /**
     * La méthode récupère la requête pour la rediriger vers la vue contenant le formulaire de mise à jour de l'entité
     * @param pRequest est la requête HTTP envoyée
     * @param pModel
     * @param pId est l'identifiant unique de l'entité Membre à récupérer dans la base de données
     * @return un ModelAndView correspondant au formulaire de mise à jour
     */
    @RequestMapping(value = "/members/update/{id}")
    public ModelAndView getUpdateMemberAdmin(HttpServletRequest pRequest, Model pModel, @PathVariable("id") int pId) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        FindByIdRequest parameters = new FindByIdRequest();
        parameters.setId(pId);
        FindByIdResponse findByIdResponse = new FindByIdResponse();
        try {
            findByIdResponse = memberManagementClientService.findById(parameters);
        } catch (ConvertException e) {
            e.printStackTrace();
        }

        return new ModelAndView("admin/members/update", "member", findByIdResponse.getMember());
    }

    /**
     * La méthode récupère les données envoyées depuis le formulaire puis appel le webservice afin de mettre à jour
     * l'entité dans la base de données
     * @param pMember est l'objet Membre à mettre à jour dans la base de données
     * @param pModel
     * @return un ModelAndView correspondant à la liste de tous les membres enregistrés
     */
    @RequestMapping(value = "/members/updating", method = RequestMethod.POST)
    public ModelAndView processUpdateMember(HttpServletRequest pRequest,
                                            @ModelAttribute("member")com.antazri.generated.member.Member pMember,
                                            Model pModel,
                                            BindingResult pBindingResult) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        if (pBindingResult.hasErrors()) {
            return returnError(Message.getText().getString("message.error.default"));
        }

        UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest();
        updateMemberRequest.setMember(pMember);

        try {
            memberManagementClientService.updateMember(updateMemberRequest);
        } catch (ConvertException e) {
            logger.error("Erreur dans la mise à jour de l'entité membre en paramètre de la requête : " + e.getMessage());
            return returnError(Message.getText().getString("message.error.default"));
        }

        return new ModelAndView("redirect:/admin/members");
    }

    @RequestMapping(value = "/members/delete/{id}")
    public ModelAndView processDeleteMember(HttpServletRequest pRequest,
                                            Model pModel,
                                            @PathVariable("id") int pId) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth", "identifiants", new DoLoginRequest());
        }

        if (!checkAdmin(pRequest)) {
            return returnError(Message.getText().getString("message.error.unauthorized"));
        }

        FindByIdRequest findByIdRequest = new FindByIdRequest();
        findByIdRequest.setId(pId);

        DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest();

        try {
            deleteMemberRequest.setMember(memberManagementClientService.findById(findByIdRequest).getMember());
        } catch (ConvertException e) {
            logger.error("Erreur dans la récupération de l'entité membre en paramètre de la requête : " + e.getMessage());
            return returnError(Message.getText().getString("message.error.default"));
        }

        try {
            memberManagementClientService.deleteMember(deleteMemberRequest);
        } catch (ConvertException e) {
            logger.error("Erreur dans la suppression de l'entité membre en paramètre de la requête : " + e.getMessage());
            return returnError(Message.getText().getString("message.error.default"));
        }

        return new ModelAndView("redirect:/admin/members");
    }

    /**
     * La méthode vérifie que l'utilisateur est administrateur en comparant le nom du status "admin" à celui indiqué
     * dans l'attribut "member" stocké dans sa session lors de son authentification
     * @param pRequest est la requête HTTP contenant les éléments de la session
     * @return un booléen permettant de confirmer ou non la condition
     */
    private boolean checkAdmin(HttpServletRequest pRequest) {
        Member member = (Member) pRequest.getSession().getAttribute("member");
        if (member.getStatus().equals("admin")) {
            return true;
        }

        return false;
    }
}
