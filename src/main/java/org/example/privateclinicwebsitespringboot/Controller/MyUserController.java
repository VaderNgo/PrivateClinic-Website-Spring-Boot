package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.DTO.BillDetailDTO;
import org.example.privateclinicwebsitespringboot.DTO.BillDisplayDTO;
import org.example.privateclinicwebsitespringboot.DTO.DisplayAppointmentDTO;
import org.example.privateclinicwebsitespringboot.Handler.MessageHandler;
import org.example.privateclinicwebsitespringboot.Model.*;
import org.example.privateclinicwebsitespringboot.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class MyUserController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private MedicineService medicineService;
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

            int totalAppointments = appointmentService.countAppointmentByPatientId(myUser.getPatient().getId());
            int totalBills = billService.countBillByPatientId(myUser.getPatient().getId());
            List<DisplayAppointmentDTO> myAppointments = appointmentService.getMyAppointments(myUser);
            List<Bill> bills  = billService.getAllBillsByPatientId(myUser.getPatient().getId());
            List<BillDisplayDTO> billDisplayDTOS = new ArrayList<>();
            for(Bill bill : bills){
                Set<BillDetailDTO> billDetailDTOS = new HashSet<>();
                Set<BillDetail> billDetails = billDetailService.getBillDetailByBillId(bill.getId());
                for(BillDetail billDetail : billDetails){
                    billDetailDTOS.add(new BillDetailDTO(billDetail,billDetail.getMedicine()));
                }
                String patientEmail = myUserService.findEmailByPatientId(bill.getPatient().getId()).isPresent() ? myUserService.findEmailByPatientId(bill.getPatient().getId()).get() : "";
                String doctorEmail = myUserService.findEmailByDoctorId(bill.getDoctor().getId()).isPresent() ? myUserService.findEmailByDoctorId(bill.getDoctor().getId()).get() : "";
                billDisplayDTOS.add(new BillDisplayDTO(bill, patientEmail, doctorEmail, billDetailDTOS));
            }

            mav.addObject("billDisplayDTOS",billDisplayDTOS);
            mav.addObject("totalAppointments",totalAppointments);
            mav.addObject("totalBills",totalBills);
            mav.addObject("myAppointments",myAppointments);
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @GetMapping("/appointments")
    public ModelAndView appointment(@ModelAttribute Appointment appointment,HttpSession session, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointments");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");
            mav.addObject("appointmentDTO",new AppointmentDTO());
            List<DisplayAppointmentDTO> myAppointments = appointmentService.getMyAppointments(myUser);
            session.setAttribute("myAppointments",myAppointments);
        }catch (Exception e){
            System.out.println(e);
        }
        mav.setViewName("user-appointment");
        return mav;
    }

    @PostMapping("/appointments/add")
    public ModelAndView addAppointment(@ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO, HttpSession session, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user",myUser);
            mav.addObject("active","appointment");
            mav.addObject("header","header.html");
            mav.addObject("sidebar","sidebar.html");

            if(!appointmentService.isDateNotPicked(appointmentDTO)){
                messageHandler = new MessageHandler("danger","This date already picked!");
                redirectAttributes.addFlashAttribute("message",messageHandler);
                mav.setViewName("redirect:/user/appointments");
                return mav;
            }
            Appointment appointment = appointmentService.createAppointment(appointmentDTO,myUser);
            messageHandler = new MessageHandler("success","Appointment added successfully");

        }catch (Exception e){
            System.out.println(e);
            messageHandler = new MessageHandler("danger","Failed to add appointment");
        }
        redirectAttributes.addFlashAttribute("message",messageHandler);
        mav.setViewName("redirect:/user/appointments");
        return mav;
    }
    @GetMapping("/bills")
    public ModelAndView userBills() {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            List<Bill> bills  = billService.getAllBillsByPatientId(myUser.getPatient().getId());
            List<BillDisplayDTO> billDisplayDTOS = new ArrayList<>();
            for(Bill bill : bills){
                Set<BillDetailDTO> billDetailDTOS = new HashSet<>();
                Set<BillDetail> billDetails = billDetailService.getBillDetailByBillId(bill.getId());
                for(BillDetail billDetail : billDetails){
                    billDetailDTOS.add(new BillDetailDTO(billDetail,billDetail.getMedicine()));
                }
                String patientEmail = myUserService.findEmailByPatientId(bill.getPatient().getId()).isPresent() ? myUserService.findEmailByPatientId(bill.getPatient().getId()).get() : "";
                String doctorEmail = myUserService.findEmailByDoctorId(bill.getDoctor().getId()).isPresent() ? myUserService.findEmailByDoctorId(bill.getDoctor().getId()).get() : "";
                billDisplayDTOS.add(new BillDisplayDTO(bill, patientEmail, doctorEmail, billDetailDTOS));
            }

            mav.addObject("billDisplayDTOS",billDisplayDTOS);

        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("user-bill");
        return mav;
    }
}
