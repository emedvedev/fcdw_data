// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

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
