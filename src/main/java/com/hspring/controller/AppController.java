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
    public String createUser(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "create_update";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/create"}, method=RequestMethod.POST)
    public String createUser(@Valid final Employee employee, final BindingResult bindingResult) {
        employeeDirectoryService.save(employee);
        return "redirect:/search";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/modify"}, method = RequestMethod.POST)
    public ModelAndView modifyUser(@RequestParam Long id) {
        Employee employee = employeeDirectoryService.findById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("employee", employee);
        mav.setViewName("create_update");
        return mav;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value={"/save"}, method = RequestMethod.POST)
    public ModelAndView modifyUser(@Valid final Employee employee, final BindingResult bindingResult){

        Employee emp = employeeDirectoryService.findById(employee.getId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setLocation(employee.getLocation());
        emp.setTitle(employee.getTitle());
        emp.setCellPhone(employee.getCellPhone());
        emp.setWorkPhone(employee.getWorkPhone());
        emp.setHomePhone(employee.getHomePhone());
        emp.setRole("ROLE_USER");

        String newPasswd = employee.getPassword();
        if(newPasswd != null && !"".equals(newPasswd)){
           emp.setPassword(newPasswd);
        }
        employeeDirectoryService.save(emp);

        ModelAndView mav = new ModelAndView();
        SearchCriteria searchCriteria = new SearchCriteria();
        mav.addObject("searchCriteria", searchCriteria);
        mav.setViewName("search");
        return mav;
    }

    @RequestMapping(value={"/","/search"}, method=RequestMethod.GET)
    public ModelAndView loginSubmit(Model model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("searchCriteria", new SearchCriteria());

        addUserAuth(mav);

        mav.setViewName("search");
        return mav;
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
            mav.setViewName("search");
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

