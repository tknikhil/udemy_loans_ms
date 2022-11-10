package com.ultimate.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ultimate.loan.config.LoansServiceConfig;
import com.ultimate.loan.model.Customer;
import com.ultimate.loan.model.Loans;
import com.ultimate.loan.model.Properties;
import com.ultimate.loan.repo.LoanRepository;



@RestController
public class LoanController {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	LoansServiceConfig loansConfig;
	@PostMapping("/myLoans")
	public List<Loans> getLoanDetails(@RequestBody Customer customer){
		System.out.println("Invoking Loans Microservice");
		List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		
		if(loans!=null)
			return loans;
		else
			return null;
	}
	
	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Properties properties = new Properties(loansConfig.getMsg(), loansConfig.getBuildVersion(),
				loansConfig.getMailDetails(), loansConfig.getActiveBranches());
		String jsonStr = ow.writeValueAsString(properties);
		return jsonStr;
	}
}
