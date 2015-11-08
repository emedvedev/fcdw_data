// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
@RequestMapping("/wod")
public class WodController extends DaoAwarController {
	
	@RequestMapping("")
    public ModelAndView getFuncube() {
    	ModelAndView model = new ModelAndView("wod");
    	model.addObject("satelliteId", "2");
        return model;
    }
    
    @RequestMapping("/{satelliteId}")
    public ModelAndView getSatellite(@PathVariable("satelliteId") Long satelliteId) {
    	ModelAndView model = new ModelAndView("wod");
    	model.addObject("satelliteId", satelliteId.toString());
        return model;
    }
        
}
