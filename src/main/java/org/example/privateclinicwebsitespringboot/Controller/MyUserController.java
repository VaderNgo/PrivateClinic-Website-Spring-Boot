package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.Handler.MessageHandler;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Service.AppointmentService;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
            mav.setViewName("user-dashboard");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("content","content.html");
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @GetMapping("/appointments")
    public ModelAndView appointment(@ModelAttribute Appointment appointment,HttpSession session){
        ModelAndView mav = new ModelAndView();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointments");
            mav.setViewName("user-appointment");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("appointmentDTO",new AppointmentDTO());
            mav.addObject("appointment",appointment);
            List<Appointment> myAppointments = appointmentService.getMyAppointments(myUser);
            session.setAttribute("myAppointments",myAppointments);
        }catch (Exception e){
            System.out.println(e);
        }
        return mav;
    }

    @PostMapping("/appointments/add")
    public ModelAndView addAppointment(@ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO, HttpSession session){
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointment");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");

            mav.addObject("appointmentDTO",appointmentDTO);
            if(!appointmentService.isDateNotPicked(appointmentDTO)){
                messageHandler = new MessageHandler("danger","This date already picked!");
                session.setAttribute("message",messageHandler);
                mav.setViewName("redirect:/user/appointments");
                return mav;
            }
            Appointment appointment = appointmentService.createAppointment(appointmentDTO,myUser);
            messageHandler = new MessageHandler("success","Appointment added successfully");

        }catch (Exception e){
            System.out.println(e);
            messageHandler = new MessageHandler("danger","Failed to add appointment");
        }
        session.setAttribute("message",messageHandler);
        mav.setViewName("redirect:/user/appointments");
        return mav;
    }
}
