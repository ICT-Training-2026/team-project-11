package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.service.AdminApprovalService;
@Controller
public class AdminApprovalController {
	private final AdminApprovalService service;
	
	

    public AdminApprovalController(AdminApprovalService service) {
        this.service = service;
    }
    
    @GetMapping("/admin_approval")
    public String viewPending(Model model) {
        List<AttendanceEntity> unapprovedData = service.getUnapprovedAttendance();
        model.addAttribute("unapprovedData", unapprovedData);
        return "admin_approval";
    }
    
    @PostMapping("/admin/attendance/approve")
    public String approveAttendance(@RequestParam("empId") Long empId,
                                    @RequestParam("workDate") LocalDate workDate,
                                    RedirectAttributes redirectAttributes) {
        try {
            service.approveAttendance(empId, workDate);
            redirectAttributes.addFlashAttribute("message", "申請が承認されました。");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "承認処理中にエラーが発生しました。");
        }
        return "redirect:/admin_approval";
    }
    
    
    @PostMapping("/admin/attendance/reject")
    public String rejectAttendance(@RequestParam("empId") Long empId,
                                   @RequestParam("workDate") LocalDate workDate,
                                   RedirectAttributes redirectAttributes) {
        try {
            service.deleteAttendance(empId, workDate);
            redirectAttributes.addFlashAttribute("message", "該当の申請が却下されました。");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "削除中にエラーが発生しました。");
        }
        return "redirect:/admin_approval";
    }
}
   // @PostMapping("/approve")
   // public String approve(@RequestParam("id") Long id) {
    //    service.approve(id);
    //    return "redirect:/admin/approval";
    //}

   // @PostMapping("/reject")
   // public String reject(@RequestParam("id") Long id) {
   //     service.reject(id);
   //     return "redirect:/admin/approval";
   // }
//}