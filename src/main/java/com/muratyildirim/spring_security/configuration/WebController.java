package com.muratyildirim.spring_security.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping("/{path:[^\\.]*}")
	public String redirectToIndex() {
		return "forward:/index.html";
	}

}
