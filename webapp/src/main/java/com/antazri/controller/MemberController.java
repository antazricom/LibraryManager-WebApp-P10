package com.antazri.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/member/")
@Scope("session")
@Controller
public class MemberController extends AbstractController {

    private static final Logger logger = LogManager.getLogger(MemberController.class);

    @RequestMapping
    public ModelAndView getMembers(Model pModel) {
        return new ModelAndView("members");
    }
}
