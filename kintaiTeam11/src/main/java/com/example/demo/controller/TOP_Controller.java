   
    
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
   // @RequiredArgsConstructor
    public class TOP_Controller {
    	
    	//いるかわからん↑g
    	//private final ColorService service;
    
    	
    	// ColorRepositoryはcolorテーブルにアクセスするためのリポジトリ
    	/*--- 最初のリクエスト -------------------------*/
    	@GetMapping("/top")
    	 public String TOP() {
            return "top"; // input.htmlを返す
        }
    	 @GetMapping("/AtAdd")
    	    public String AtAdd() {
    	        return "AtAdd"; // Thymeleafは templates/AtAdd.html を探します
    	 }
    	 @GetMapping("/L")
    	    public String L() {
    	        return "L"; // Thymeleafは templates/AtAdd.html を探します
    	    }
    	 
    	 @GetMapping("/System")
 	    public String System() {
 	        return "System"; // Thymeleafは templates/AtAdd.html を探します
 	    }
    	// @PostMapping("/System")
     	//public String System(@RequestParam String colorSearch, Model model) {
     	   /* List<Color> filteredColors = colorRepository.findByColorNameOrColorCode(colorSearch);
         model.addAttribute("colors", filteredColors);*/
     	//    return "System";
     	//}
    	 
    	 @GetMapping("/AtMenu")
 	    public String AtMenu() {
 	        return "AtMenu"; // Thymeleafは templates/AtAdd.html を探します
 	    }
    	 @GetMapping("/Attendance_edit")
  	    public String Attendance_edit() {
  	        return "Attendance_edit"; // Thymeleafは templates/AtAdd.html を探します
  	    }
    	// @GetMapping("/Attendance_register")
   	    //public String Attendance_register() {
   	    //    return "Attendance_register"; // Thymeleafは templates/AtAdd.html を探します
   	    //}
    	 @GetMapping("/Attendance_search")
   	    public String Attendance_search() {
   	        return "Attendance_search"; // Thymeleafは templates/AtAdd.html を探します
   	    }
    	@GetMapping("/Admin")
   	    public String Admin() {
   	        return "Admin"; // Thymeleafは templates/AtAdd.html を探します
   	    }
    	 
    	 //@PostMapping("/Register_complate")
    	// public String registerAttendance(
    	//     @RequestParam String dateInput,
    	//     @RequestParam String startTime,
    	//     @RequestParam String endTime,
    	//     @RequestParam String breakTime,
    	//     @RequestParam String workTime,
    	//     @RequestParam String attendanceType,
    	//     Model model) {
    	     // 受け取ったデータを処理する
    	     // 例: model.addAttribute("date", dateInput);
    	//     return "Register_complate"; // 登録完了画面を表示
    	// }
    	
    	 @GetMapping("/pw")
    	 public String pw() {
            return "pw"; // input.htmlを返す
        }

    	 
    	
    	 
    	 @PostMapping("/edit_complate")
    	 public String edit_complate(
    	     @RequestParam String edit_id,
    	     @RequestParam String edit_date,
    	     @RequestParam String edit_startTime,
    	     @RequestParam String edit_endTime,
    	     @RequestParam String edit_workTime,
    	     @RequestParam String edit_breakTime,
    	     @RequestParam String edit_attendanceType,
    	     
    	    
    	     Model model) {
    	     // 受け取ったデータを処理する
    	     // 例: model.addAttribute("date", dateInput);
    	     return "Edit_complate"; // 登録完了画面を表示
    	 }
    	 
    	 
    	 //@PostMapping("/search_complate")
    	 //public String search_complate(
    	//     @RequestParam String search_id,
    	 //    @RequestParam String search_date,
    	 //    @RequestParam String search_range,
    	//     Model model) {
    	//     return "search_complate"; // 登録完了画面を表示
    	// }
    	
    	 @PostMapping("/search_edit")
    	 public String search_edit( Model model) {
    	     return "search_edit"; // 登録完了画面を表示
    	 }
    	 @PostMapping("/search_delate")
    	 public String search_delate( Model model) {
    	     return "search_delate"; // 登録完了画面を表示
    	 }
    	
    	 
    	 
    	 @GetMapping("/admin_add")
  	    public String admin_add() {
  	        return "admin_add"; // Thymeleafは templates/AtAdd.html を探します
  	    }
    	// @GetMapping("/admin_approval")
  	    //public String admin_approval() {
  	    //    return "admin_approval"; // Thymeleafは templates/AtAdd.html を探します
  	    //}
    	 @GetMapping("/admin_delate")
    	  public String admin_delate() {
    	      return "admin_delate"; // Thymeleafは templates/admin_delate.html を探します
    	  }
    	 @GetMapping("/admin_edit")
  	    public String admin_edit() {
  	        return "admin_edit"; // Thymeleafは templates/AtAdd.html を探します
  	    }
    }
    	

    