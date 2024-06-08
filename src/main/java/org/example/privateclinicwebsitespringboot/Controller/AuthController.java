package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Model.Patient;
import org.example.privateclinicwebsitespringboot.Service.MyUserService;
import org.example.privateclinicwebsitespringboot.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/index")
    public ModelAndView homePage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        mav.addObject("content","sidebar");
        return mav;
    }

    @GetMapping("/signin")
    public ModelAndView signInPage(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signin");
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView signUpPage(@ModelAttribute SignUpDTO signUpDTO){
        ModelAndView mav = new ModelAndView();
        mav.addObject("signUpDTO",signUpDTO);
        mav.setViewName("signup");
        return mav;
    }

    @PostMapping("/signup/process")
    public ModelAndView signUpProcess(@ModelAttribute("signUpDTO") SignUpDTO signUpDTO, HttpSession session, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView();
        mav.addObject("signUpDTO",signUpDTO);
        Patient patient = patientService.createPatient(signUpDTO);
        MyUser myUser = myUserService.signUpPatient(signUpDTO,passwordEncoder,patient);
        mav.setViewName("redirect:/signin");
        return mav;
    }
}
