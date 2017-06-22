package edu.mum.coffee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.repository.OrderLineRepository;

@Service
@Transactional
public class OrderLineService {
	
	@Autowired
	private OrderLineRepository personRepository;

	public Orderline save(Orderline person) {
		return personRepository.save(person);
	}

}
