package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String indexGet(HttpSession session) {
		return "index";
	}
	
	@GetMapping("/index")
	public String indexBisGet(HttpSession session) {
		return "redirect:";
	}
}
