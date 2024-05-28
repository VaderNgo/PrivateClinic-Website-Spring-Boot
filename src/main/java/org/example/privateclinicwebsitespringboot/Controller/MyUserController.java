package org.example.privateclinicwebsitespringboot.Controller;

import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.AppointmentService;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class MyUserController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/dashboard")
    public ModelAndView userDashboard(){
        ModelAndView mav = new ModelAndView();
        try{
            //get current logged user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","dashboard");
            mav.setViewName("dashboard");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("content","content.html");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @GetMapping("/appointment")
    public ModelAndView appointment(){
        ModelAndView mav = new ModelAndView();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointment");
            mav.setViewName("add-appointment");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("appointmentDTO",new AppointmentDTO());
        }catch (Exception e){
            System.out.println(e);
        }
        return mav;
    }

    @PostMapping("/appointment/add")
    public ModelAndView addAppointment(@ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO){
        ModelAndView mav = new ModelAndView();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointment");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");

            mav.addObject("appointmentDTO",appointmentDTO);

            Appointment appointment = appointmentService.createAppointment(appointmentDTO,myUser);
            mav.setViewName("redirect:/user/appointment");

        }catch (Exception e){
            System.out.println(e);
        }
        return mav;
    }
}
