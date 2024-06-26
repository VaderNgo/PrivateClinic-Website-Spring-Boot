package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.BillDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            int totalAppointments = appointmentService.countAppointmentByDoctorId(myUser.getDoctor().getId());
            int totalBills = billService.countBillByDoctorId(myUser.getDoctor().getId());
            int totalAcceptedAppointments = appointmentService.countDoctorAcceptedAppointment(myUser.getDoctor().getId());

            List<DisplayAppointmentDTO> myAppointments = appointmentService.getDoctorTodayAppointments(myUser.getDoctor().getId());
            List<Bill> bills = billService.getPendingBillsByDoctorId(myUser.getDoctor().getId());
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

            mav.addObject("totalAppointments", totalAppointments);
            mav.addObject("totalBills", totalBills);
            mav.addObject("totalAcceptedAppointments", totalAcceptedAppointments);
            mav.addObject("myAppointments", myAppointments);
            mav.addObject("billDisplayDTOS", billDisplayDTOS);

        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-dashboard");
        return mav;
    }

    @GetMapping("/bills-list")
    public ModelAndView billList() {
        ModelAndView mav = new ModelAndView();
        try {
            //get current logged user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            List<Bill> bills = billService.getAllBillsByDoctorId(myUser.getDoctor().getId());
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

            mav.addObject("billDisplayDTOS", billDisplayDTOS);

        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("doctor-bills");
        return mav;
    }

    @GetMapping("/appointments")
    public ModelAndView appointment(HttpSession session, RedirectAttributes redirectAttributes) {
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
    public ModelAndView viewAppointmentBill(@RequestParam("appointmentId") Long appointmentId, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            var bill = billService.getBillByAppointmentId(appointmentId);
            messageHandler = new MessageHandler("success", "Redirect to bill successfully");
            mav.setViewName("redirect:/doctor/bills?billId=" + bill.getId());
        } catch (Exception e) {
            messageHandler = new MessageHandler("danger", "Redirect to bill failed");
            mav.setViewName("redirect:/doctor/dashboard");
        }
        return mav;
    }

    @GetMapping("/bills")
    public ModelAndView bill(@RequestParam("billId") Long billId, HttpSession session, RedirectAttributes redirectAttributes) {
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
    public ModelAndView addBill(@RequestParam("appointmentId") Long appointmentId, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
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

            messageHandler = new MessageHandler("success", "Bill added successfully");
            mav.setViewName("redirect:/doctor/bills?billId=" + billId);
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to add bill");
            mav.setViewName("redirect:/doctor/dashboard");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        return mav;
    }

    @PostMapping("/bills/updateDetails")
    public ModelAndView updateBillDetails(@RequestParam("billId") Long billId,
                                          @RequestParam("medicine") List<String> medicines,
                                          @RequestParam("quantity") List<Integer> quantities,
                                          HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
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
            messageHandler = new MessageHandler("success", "Bill details updated successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to update bill details");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/doctor/bills?billId=" + billId);
        return mav;
    }

}
