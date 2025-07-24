package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AttendanceEntity;
import com.example.demo.form.SearchForm;
import com.example.demo.service.SearchService;

@Controller
public class SearchAttendanceController {
	private final SearchService service;
	
	 @Autowired
	    public SearchAttendanceController(SearchService service) {
	        this.service = service;
	    }
	/*検索リクエスト*/

	 @PostMapping("/search_complate")
	 public String searchAttendance(@ModelAttribute SearchForm form, Model model) {
		 String searchRange = form.getSearchRange();
		 LocalDate start =form.getWorkDate();
	     LocalDate end = start;
	     if (searchRange == null) {
	    	    searchRange = "1日"; // デフォルト値を設定
	    	}
	     switch (form.getSearchRange()) {
	         case "1週間":
	             start = start.minusWeeks(1);
	             break;
	         case "1か月":
	             start = start.minusMonths(1);
	             break;
	         case "1日":
	         default:
	             // start = workDate のまま
	     }

	     List<AttendanceEntity> list = service.findByEmpIdAndWorkDateBetween(
	         form.getEmpId(), start, end
	     );
	     model.addAttribute("attList", list);
	     return "search_complate";
	 }
}
	 
