package com.example.demo.controller;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.service.ExportService;
@Controller
public class ExportController {

	 private final ExportService service;

	    public ExportController(ExportService service) {
	        this.service = service;
	    }

	    @GetMapping("/export")
 	    public String Export() {
 	        return "export"; // Thymeleafは templates/AtAdd.html を探します
 	    }
	    @GetMapping("/admin/export-view")
	    public String viewExportPage(
	        @RequestParam(required = false) Integer year,
	        @RequestParam(required = false) Integer month,
	        Model model) {

	        YearMonth ym = (year != null && month != null) ? YearMonth.of(year, month)
	                                                       : YearMonth.now();
	        model.addAttribute("year", ym.getYear());
	        model.addAttribute("month", ym.getMonthValue());
	        List<AttendanceEntity> data = service.findByMonth(ym);
	        model.addAttribute("MonthlyData", data);
	        return "export";
	    }

	    @PostMapping("/admin/export")
	    public void exportCsv(
	        @RequestParam Long empId,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate workDate,
	        HttpServletResponse response) throws IOException {

	        YearMonth ym = YearMonth.from(workDate);
	        List<AttendanceEntity> list = service.findByEmpAndMonth(empId, ym);

	        response.setContentType("text/csv; charset=UTF-8");
	        String filename = String.format("attendance-%d-%02d-%d.csv",
	                            empId, ym.getYear(), ym.getMonthValue());
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

	        try (
	            BufferedWriter writer = new BufferedWriter(
	                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
	            CSVPrinter csv = new CSVPrinter(writer, CSVFormat.DEFAULT
	                .withHeader("社員ID","日付","出勤区分","出勤","退勤","実労働時間","休憩時間","残業時間","承認"))
	        ) {
	            for (AttendanceEntity a : list) {
	                csv.printRecord(
	                    a.getEmpId(),
	                    a.getWorkDate(),
	                    a.getLeaveType(),
	                    a.getCheckInTime(),
	                    a.getCheckOutTime(),
	                    a.getWorkTimeHours(),
	                    a.getBreakTime(),
	                    a.getOvertimeHours(),
	                    a.getApproval() == 0 ? "未承認" : "承認済み"
	                );
	            }
	        }
	    }
	    
	    
	    // 同じ内容を POSTでも扱えるように一括出力用
	    
	    @PostMapping("/export-all")
	    public void exportAll(@RequestParam(required=false) Integer year,
	                          @RequestParam(required=false) Integer month,
	                          HttpServletResponse response,
	                          RedirectAttributes redirectAttrs) throws IOException {
	        if (year == null || month == null) {
	            redirectAttrs.addFlashAttribute("alertMessage",
	                "年または月が未指定です。正しい数値を入力してください。");
	            response.sendRedirect("/export");
	            return;
	        }
	        // 正常時：CSV出力
	        List<AttendanceEntity> data = service.getAttendanceByMonth(year, month);
	        service.writeCsv(response, data,
	            String.format("attendance-all-%d-%02d.csv", year, month));
	    }
	    
	    
	}