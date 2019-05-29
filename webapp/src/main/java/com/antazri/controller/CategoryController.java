package com.antazri.controller;

import com.antazri.generated.auth.DoLoginRequest;
import com.antazri.generated.book.FindByCategoryRequest;
import com.antazri.generated.book.FindByCategoryResponse;
import com.antazri.generated.category.*;
import com.antazri.service.impl.BookManagementClientService;
import com.antazri.service.impl.CategoryManagementClientService;
import com.antazri.utils.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * La classe CategoryController gère les requeêtes et l'accès aux pages de la rubrique "./categories"
 */
@RequestMapping("/categories")
@Scope("session")
@Controller
public class CategoryController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(CategoryController.class);

    @Autowired
    private CategoryManagementClientService categoryManagementClientService;

    @Autowired
    private BookManagementClientService bookManagementClientService;

    /**
     * La méthode getCategoriesDefault récupère et redirige la requête vers la page principale de la requête
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "categories.jsp"
     */
    @RequestMapping
    public ModelAndView getCategoriesDefault(Model pModel) {

        return new ModelAndView("redirect:/categories/all");
    }

    /**
     * La méthode getCategories récupère la requête depuis la racine de la section et la redirige vers ka page
     * principale
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "categories.jsp"
     */
    @RequestMapping("/")
    public ModelAndView getCategories(Model pModel) {

        return new ModelAndView("redirect:/categories/all");
    }

    /**
     * La méthode getAllCategories définit la redirection vers la page principale de la rubrique
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "categories.jsp"
     */
    @RequestMapping("/all")
    public ModelAndView getAllCategories(HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        FindAllResponse vResponse;

        try {
            vResponse = categoryManagementClientService.findAll(new FindAllRequest());
        } catch(ConvertException pE) {
            logger.error("getAllCategories: Erreur dans la récupération des objets Category");
            return new ModelAndView("redirect:/error", "message", pE.getFaultInfo());
        }

        return new ModelAndView("category/categories", "categories", vResponse.getCategories());
    }

    /**
     * La méthode getCategoryDetails retourne une page contenant l'ensemble des informations d'un objet Category
     * récupéré grâce à son identifiant unique via le service web CategoryManagement
     * @param pId est un Integer définissant l'identifiant unique de l'objet Category à récupérer
     * @param pRequest est un objet HttpServletRequest contenant la requête HTTP de l'utilisateur
     * @param pModel
     * @return un objet ModelAndView correspondant à la page principale "category.jsp" avec un objet Category en attribut
     */
    @RequestMapping("/details/{id}")
    public ModelAndView getCategoryDetails(@PathVariable("id") int pId, HttpServletRequest pRequest, Model pModel) {
        if (!checkSession(pRequest)) {
            return new ModelAndView("redirect:/auth","identifiants", new DoLoginRequest());
        }

        ModelAndView mv = new ModelAndView("category/category");
        FindByIdRequest vRequest = new FindByIdRequest();
        FindByIdResponse vResponse;
        FindByCategoryRequest vFindByCategoryRequest = new FindByCategoryRequest();
        FindByCategoryResponse vFindByCategoryResponse;

        if (pId < 0) {
            logger.error("getCategoryDetails: Erreur de récupération de l'objet Category : ID invalide");
            return returnError(Message.getText().getString("message.error.id"));
        }

        vRequest.setId(pId);

        try {
            vResponse = categoryManagementClientService.findById(vRequest);
        } catch(ConvertException pE) {
            logger.error("getCategoryDetails: Erreur de récupération de l'objet Category : " + pE.getFaultInfo().getFault().getFaultString());
            return returnError(Message.getText().getString("message.error.null"));
        }

        if (vResponse.getCategory() == null) {
            return returnError(Message.getText().getString("message.error.null"));
        }

        try {
            vFindByCategoryRequest.setCategory(toCategoryBookManagement(vResponse.getCategory()));
            vFindByCategoryResponse = bookManagementClientService.findByCategory(vFindByCategoryRequest);
        } catch (Exception pE) {
            logger.error("getCategoryDetails/toCategoryBookManagement: Erreur dans la récupération des Book associés");
            return returnError(Message.getText().getString("message.error.null"));
        }

        mv.addObject("category", vResponse.getCategory());
        mv.addObject("books", vFindByCategoryResponse.getBooks());

        return mv;
    }

    /**
     * La méthode toCategoryBookManagement convertit un objet Category généré par le service CategoryManagement en
     * objet Category utilisable par le service BookManagement
     * @param pCategory est un objet Category généré par le service CategoryManagement
     * @return un objet Category utilisable par le service BookManagement
     */
    private com.antazri.generated.book.Category toCategoryBookManagement(Category pCategory) {
        com.antazri.generated.book.Category vCategory = new com.antazri.generated.book.Category();
        vCategory.setId(pCategory.getId());
        vCategory.setTitle(pCategory.getTitle());

        return vCategory;
    }
}
