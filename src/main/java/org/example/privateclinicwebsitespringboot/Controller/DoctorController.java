package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.BillDTO;
import org.example.privateclinicwebsitespringboot.DTO.DisplayAppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.*;
import org.example.privateclinicwebsitespringboot.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private MedicineService medicineService;

    @GetMapping("/dashboard")
    public ModelAndView doctorDashboard() {
        ModelAndView mav = new ModelAndView();
        try {
            //get current logged user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "dashboard");
            mav.setViewName("user-dashboard");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            mav.addObject("content", "content.html");
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @GetMapping("/appointments")
    public ModelAndView appointment(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            List<DisplayAppointmentDTO> appointments = appointmentService.getDoctorTodayAppointments(myUser.getDoctor().getId());
            List<DisplayAppointmentDTO> finishedAppointments = appointmentService.getAppointmentsByStatusForDoctor("Finished",myUser.getDoctor().getId());
            session.setAttribute("appointments", appointments);
            session.setAttribute("finishedAppointments", finishedAppointments);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-appointment");
        return mav;
    }

    @PostMapping("/bills/redirect")
    public ModelAndView viewAppointmentBill(@RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            var bill = billService.getBillByAppointmentId(appointmentId);
            mav.setViewName("redirect:/doctor/bills?billId=" + bill.getId());
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @GetMapping("/bills")
    public ModelAndView bill(@RequestParam("billId") Long billId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            var bill = billService.getBillById(billId);
            bill.setBillDetailSet(billDetailService.getBillDetailByBillId(billId));
            BillDTO billDTO = new BillDTO(billService.getBillById(billId));
            mav.addObject("billDTO", billDTO);

        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-bill");
        return mav;
    }

    @PostMapping("/bills/add")
    public ModelAndView addBill(@RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            Appointment appointment = appointmentService.setFinishAppointment(appointmentId);
            Bill bill = billService.createBillForAppointment(appointment);
            Long billId = bill.getId();
            mav.setViewName("redirect:/doctor/bills?billId=" + billId);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mav;
    }

    @PostMapping("/bills/updateDetails")
    public ModelAndView updateBillDetails(@RequestParam("billId") Long billId,
                                          @RequestParam("medicine") List<String> medicines,
                                          @RequestParam("quantity") List<Integer> quantities,
                                          HttpSession session) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            if(medicines.size() != quantities.size()){
                throw new Exception("Medicines and quantities size not match");
            }

            billDetailService.clearAllDetailsByBillId(billId);

            Bill bill = billService.getBillById(billId);
            if (bill.getBillDetailSet() == null) {
                bill.setBillDetailSet(new HashSet<>());
            }
            else{
                bill.getBillDetailSet().clear();
            }
            Set<BillDetail> billDetailSet = new HashSet<>();
            for(int i = 0; i < medicines.size(); i++){
                Medicine medicine = medicineService.getMedicineByName(medicines.get(i));
                if(medicine == null){
                    throw new Exception("Medicine not found");
                }
                billDetailSet.add(billDetailService.createBillDetail(medicines.get(i), quantities.get(i),medicine,bill));
            }
            bill.setBillDetailSet(billDetailSet);
            System.out.println("\nTotal money: " + bill.calculateTotalMoney());
            bill.setTotalMoney(bill.calculateTotalMoney());
            billService.updateTotalMoney(bill.calculateTotalMoney(),billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.setViewName("redirect:/doctor/bills?billId=" + billId);
        return mav;
    }

}
