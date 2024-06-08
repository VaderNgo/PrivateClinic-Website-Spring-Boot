package org.example.privateclinicwebsitespringboot.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.privateclinicwebsitespringboot.DTO.*;
import org.example.privateclinicwebsitespringboot.Handler.MessageHandler;
import org.example.privateclinicwebsitespringboot.Model.*;
import org.example.privateclinicwebsitespringboot.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private  BillService billService;
    @Autowired
    private PatientService patientService;

    @GetMapping("/dashboard")
    public ModelAndView adminDashboard() {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "dashboard");
            mav.setViewName("admin-dashboard");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            mav.addObject("totalDoctor", doctorService.countDoctor());
            mav.addObject("totalPatient", patientService.countPatient());
            mav.addObject("totalMedicine", medicineService.countMedicine());
            mav.addObject("totalAppointment", appointmentService.countAppointment());
            mav.addObject("totalBill", billService.countBill());
            mav.addObject("totalMoney", billService.totalMoney());
            mav.addObject("appointments", appointmentService.getAllAppointments());
            mav.addObject("doctors",doctorService.getAllDoctors());
            mav.addObject("patients",patientService.getAllPatients());

        } catch (Exception e) {
            System.out.println(e);
        }
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

            List<DisplayAppointmentDTO> pendingAppointments = appointmentService.getAppointmentsByStatus("Pending");
            List<DisplayAppointmentDTO> acceptedAppointments = appointmentService.getAppointmentsByStatus("Accepted");
            List<DisplayAppointmentDTO> deniedAppointments = appointmentService.getAppointmentsByStatus("Denied");

            session.setAttribute("pendingAppointments", pendingAppointments);
            session.setAttribute("acceptedAppointments", acceptedAppointments);
            session.setAttribute("deniedAppointments", deniedAppointments);

            List<Doctor> doctors = doctorService.getAllDoctors();
            session.setAttribute("doctors", doctors);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("admin-appointment");
        return mav;
    }

    @PostMapping("/appointments/accept")
    public ModelAndView acceptAppointment(@RequestParam("appointmentId") Long appointmentId, @RequestParam("doctorId") Long doctorId, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            Doctor doctor = doctorService.getDoctorById(doctorId);
            appointmentService.acceptAppointment(appointmentId, doctor);
            messageHandler = new MessageHandler("success", "Appointment accepted successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to accept appointment");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/appointments");
        return mav;
    }

    @PostMapping("/appointments/deny")
    public ModelAndView rejectAppointment(@RequestParam("appointmentId") Long appointmentId, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "appointments");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            appointmentService.denyAppointment(appointmentId);
            messageHandler = new MessageHandler("success", "Appointment denied successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to deny appointment");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/appointments");
        return mav;
    }

    @GetMapping("/doctors")
    public ModelAndView doctor(@ModelAttribute("DoctorAccountDTO") DoctorAccountDTO doctorAccountDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "doctors");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            mav.addObject("doctorAccountDTO", new DoctorAccountDTO());
            List<DoctorDisplayDTO> doctorDisplayDTOS = new ArrayList<>();
            List<Doctor> doctors = doctorService.getAllDoctors();
            for (Doctor doctor : doctors) {
                String email = myUserService.findEmailByDoctorId(doctor.getId()).get();
                doctorDisplayDTOS.add(new DoctorDisplayDTO(doctor, email));
            }
            mav.addObject("doctorDisplayDTOS", doctorDisplayDTOS);
            mav.addObject("updateDoctorDTO", new UpdateDoctorDTO());
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("admin-doctor");
        return mav;
    }

    @PostMapping("/doctors/add")
    public ModelAndView addDoctor(@ModelAttribute("DoctorAccountDTO") DoctorAccountDTO doctorAccountDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "doctors");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            Doctor doctor = doctorService.createDoctor(doctorAccountDTO);
            myUserService.signUpDoctor(doctorAccountDTO, passwordEncoder, doctor);
            messageHandler = new MessageHandler("success", "Doctor added successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to add doctor");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/doctors?done");
        return mav;
    }

    @GetMapping("/medicines")
    public ModelAndView doctorMedicines() {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "medicines");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            List<Medicine> medicines = medicineService.getAllMedicines();

            mav.addObject("medicines", medicines);
            mav.addObject("medicine", new Medicine());
            mav.addObject("updateMedicineDTO", new UpdateMedicineDTO());
        } catch (Exception e) {

        }
        mav.setViewName("admin-medicine");
        return mav;
    }

    @PostMapping("/medicines/add")
    public ModelAndView addMedicine(@ModelAttribute("Medicine") Medicine medicine,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "medicines");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            medicineService.addMedicine(medicine);
            messageHandler = new MessageHandler("success", "Medicine added successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to add medicine");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/medicines");
        return mav;
    }

    @PostMapping("/medicines/delete")
    public ModelAndView deleteMedicine(@RequestParam("medicineId") Long medicineId,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "medicines");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            medicineService.deleteMedicine(medicineId);
            messageHandler = new MessageHandler("success", "Medicine deleted successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to delete medicine");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/medicines");
        return mav;
    }

    @PostMapping("/medicines/update")
    public ModelAndView updateMedicine(@ModelAttribute("UpdateMedicineDTO") UpdateMedicineDTO updateMedicineDTO,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            medicineService.updateMedicine(updateMedicineDTO);
            messageHandler = new MessageHandler("success", "Medicine updated successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to update medicine");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/medicines");
        return mav;
    }

    @GetMapping("/bills")
    public ModelAndView adminBills() {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");

            List<Bill> bills  = billService.getAllBills();
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
        mav.setViewName("admin-bill");
        return mav;
    }

    @PostMapping("/bills/delete")
    public ModelAndView adminBillsDelete(@RequestParam("billId") Long billId,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            billService.deleteBillById(billId);
            messageHandler = new MessageHandler("success", "Bill deleted successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to delete bill");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/bills");
        return mav;
    }

    @PostMapping("/bills/send")
    public ModelAndView adminBillsSend(@RequestParam("billId") Long billId,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "bills");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            billService.sendBillToUser(billId);
            messageHandler = new MessageHandler("success", "Bill sent successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to send bill");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/bills");
        return mav;
    }

    @GetMapping("/patients")
    public ModelAndView adminPatients(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "patients");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
            List<PatientDisplayDTO> patientDisplayDTOS = new ArrayList<>();
            List<Patient> patients = patientService.getAllPatients();
            for(Patient patient : patients){
                String email = myUserService.findEmailByPatientId(patient.getId()).get();
                patientDisplayDTOS.add(new PatientDisplayDTO(email,patient));
            }
            mav.addObject("patientDisplayDTOS",patientDisplayDTOS);
            mav.addObject("updatePatientDTO", new UpdatePatientDTO());
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("admin-patient");
        return mav;
    }
    @PostMapping("/patients/delete")
    public ModelAndView adminPatientsDelete(@RequestParam("patientId") Long patientId) {
        ModelAndView mav = new ModelAndView();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUser myUser = myUserService.loadMyUserByUsername(auth.getName());
            mav.addObject("user", myUser);
            mav.addObject("active", "patients");
            mav.addObject("header", "header.html");
            mav.addObject("sidebar", "sidebar.html");
//            billService.deleteAllByPatientId(patientId);
//            appointmentService.deleteAllByPatientId(patientId);
//            patientService.deletePatient(patientId);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("redirect:/admin/patients");
        return mav;
    }

    @PostMapping("/patients/update")
    public ModelAndView adminPatientsUpdate(@ModelAttribute("UpdatePatientDTO") UpdatePatientDTO updatePatientDTO,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            patientService.updatePatient(updatePatientDTO);
            myUserService.updateEmailForPatient(updatePatientDTO.getEmail(), updatePatientDTO.getId());
            messageHandler = new MessageHandler("success", "Patient updated successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to update patient");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/patients");
        return mav;
    }

    @PostMapping("/doctors/update")
    public ModelAndView adminDoctorsUpdate(@ModelAttribute("UpdateDoctorDTO") UpdateDoctorDTO updateDoctorDTO,HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        MessageHandler messageHandler = null;
        try {
            doctorService.updateDoctor(updateDoctorDTO);
            myUserService.updateEmailForDoctor(updateDoctorDTO.getEmail(), updateDoctorDTO.getId());
            messageHandler = new MessageHandler("success", "Doctor updated successfully");
        } catch (Exception e) {
            System.out.println(e);
            messageHandler = new MessageHandler("danger", "Failed to update doctor");
        }
        redirectAttributes.addFlashAttribute("message", messageHandler);
        mav.setViewName("redirect:/admin/doctors");
        return mav;
    }

    @PostMapping("/doctors/delete")
    public ModelAndView adminDoctorsDelete(@RequestParam("doctorId") Long doctorId) {
        ModelAndView mav = new ModelAndView();
        try {
            Doctor doctor = doctorService.findDoctorById(doctorId);
            doctor.setIsDeleted(1);
            doctorService.saveDoctor(doctor);
        } catch (Exception e) {
            System.out.println(e);
        }
        mav.setViewName("redirect:/admin/doctors");
        return mav;
    }
}
