package org.example.privateclinicwebsitespringboot.Controller;

import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class MyUserController {
    @Autowired
    private MyUserService myUserService;
    @GetMapping("/dashboard")
    public ModelAndView userDashboard(){
        ModelAndView mav = new ModelAndView();
        try{
            //get current logged user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.setViewName("dashboard");
            mav.addObject("header","header");
            mav.addObject("sidebar","sidebar");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return mav;
    }
}
