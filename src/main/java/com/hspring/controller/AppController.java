package com.hspring.controller;

import com.hspring.data.entity.Employee;
import com.hspring.model.SearchCriteria;
import com.hspring.service.EmployeeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class AppController {

   @Autowired
    EmployeeSearchService employeeDirectoryService;

    @RequestMapping(value={"/login"}, method= RequestMethod.GET)
    public String loginForm(Model model) {
        return "login";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/create"}, method=RequestMethod.GET)
    public ModelAndView createUser() {
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("isUpdate", false);
        modelAndView.setViewName("create_update");
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value={"/create"}, method=RequestMethod.POST)
    public String createUser(@Valid final Employee employee, final BindingResult bindingResult) {
        employeeDirectoryService.save(employee);
        return "redirect:/search";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value={"/delete"}, method=RequestMethod.POST)
    public String deleteUser(@RequestParam Long id) {
        employeeDirectoryService.delete(id);
        return "redirect:/search";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/modify"}, method = RequestMethod.GET)
    public ModelAndView modifyUser(@RequestParam Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean allowInteraction = false;

        Collection<GrantedAuthority> authorities = user.getAuthorities();
        for(GrantedAuthority authority: authorities) {
            String auth = authority.getAuthority();
            if ("ROLE_ADMIN".equals(auth)) {
                allowInteraction = true;
            }
        }
        if(allowInteraction) {
            Employee employee = employeeDirectoryService.findById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("employee", employee);
            modelAndView.addObject("isUpdate", true);
            modelAndView.setViewName("create_update");
            return modelAndView;
        }
        else{
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/search");
            return modelAndView;
        }
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/save"}, method = RequestMethod.POST)
    public ModelAndView save(@Valid final Employee employee, final BindingResult bindingResult){

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:search?error");
            return modelAndView;
        }

        employeeDirectoryService.update(employee);

        SearchCriteria searchCriteria = new SearchCriteria();
        modelAndView.addObject("searchCriteria", searchCriteria);
        modelAndView.setViewName("search");
        return modelAndView;
    }

    @RequestMapping(value={"/","/search"}, method=RequestMethod.GET)
    public ModelAndView loginSubmit(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("searchCriteria", new SearchCriteria());
        addUserAuth(modelAndView);
        modelAndView.setViewName("search");
        return modelAndView;
    }

    private void addUserAuth(ModelAndView mav) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();//get logged in username
        for(GrantedAuthority authority: authorities){
            String auth = authority.getAuthority();
            if("ROLE_ADMIN".equals(auth)){
                mav.addObject("auth", "ROLE_ADMIN");
            }
        }
    }

    @RequestMapping(value={"/search"}, method=RequestMethod.POST)
    public ModelAndView searchSubmit(@Valid final SearchCriteria searchCriteria, final BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            mav.setViewName("redirect:search?error");
            return mav;
        }

        if(!searchCriteria.isValid()){
           mav.setViewName("redirect:search?error");
           return mav;
        }

        List<Employee> employees = employeeDirectoryService.search(searchCriteria);
        mav.setViewName("search");
        if(!employees.isEmpty()) {
            mav.addObject("searchResults", employees);
        }
        addUserAuth(mav);
        return mav;
    }
}

