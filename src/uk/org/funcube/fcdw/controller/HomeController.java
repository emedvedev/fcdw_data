package uk.org.funcube.fcdw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class HomeController extends DaoAwarController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
    	model.addAttribute("satelliteId", "2");
        return "home";
    }
    
    @RequestMapping(value = "{satelliteId}", method = RequestMethod.GET)
    public String show(@PathVariable("satelliteId") Long satelliteId, Model model) {
    	model.addAttribute("satelliteId", satelliteId);
        return "home";
    }
    
    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
        
}
