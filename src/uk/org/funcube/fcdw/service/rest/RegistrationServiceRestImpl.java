package uk.org.funcube.fcdw.service.rest;

import java.math.BigInteger;
import java.util.Random;

import javax.xml.ws.Response;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.org.funcube.fcdw.server.util.DiffieHellman;



@Service
@RequestMapping("/secure/register")
public class RegistrationServiceRestImpl implements RegistrationServiceRest {
	
	private static Long random = new Random().nextLong(); 
	private static final BigInteger modulus = BigInteger.valueOf(23);
	private static final BigInteger privateKey = BigInteger.valueOf(random);
	private static final BigInteger publicKey = DiffieHellman.BASE_5.generatePublicKey(privateKey, modulus);        

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
