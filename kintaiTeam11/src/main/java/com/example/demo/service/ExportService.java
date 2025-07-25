package com.example.demo.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.repository.ExportRepository;

@Service
public class ExportService {
    private final ExportRepository repo;

    public ExportService(ExportRepository repo) {
        this.repo = repo;
    }

    public List<AttendanceEntity> findByMonth(YearMonth ym) {
        return repo.findByDateRange(ym.atDay(1), ym.atEndOfMonth());
    }

    public List<AttendanceEntity> findByEmpAndMonth(Long empId, YearMonth ym) {
        return repo.findByEmpAndDateRange(empId, ym.atDay(1), ym.atEndOfMonth());
    }
    
    
    public List<AttendanceEntity> getAttendanceByMonth(int year, int month) {
        return repo.findByMonth(year, month);
    }

    public void writeCsv(HttpServletResponse response,
                         List<AttendanceEntity> data,
                         String filename) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + filename + "\"");

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
             CSVPrinter csv = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader(
                         "社員ID","日付","出勤区分","出勤","退勤","実労働時間","休憩時間","残業時間"))) {

            for (AttendanceEntity a : data) {
                csv.printRecord(
                    a.getEmpId(), a.getWorkDate(), a.getLeaveType(),
                    a.getCheckInTime(), a.getCheckOutTime(),
                    a.getWorkTimeHours(), a.getBreakTime(), a.getOvertimeHours()
                );
            }
            csv.flush();
        }
    }
}