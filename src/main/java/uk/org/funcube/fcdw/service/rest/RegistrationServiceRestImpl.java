// FUNcube Data Warehouse
// Copyright 2013 (c) David A.Johnson, G4DPZ, AMSAT-UK
// This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
// To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/ or send a letter
// to Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

package uk.org.funcube.fcdw.service.rest;

import javax.xml.ws.Response;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Service
@RequestMapping("/secure/register")
public class RegistrationServiceRestImpl implements RegistrationServiceRest {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Response get() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Response create(Long satelliteId) {
		// TODO Auto-generated method stub
		return null;
	}

}
