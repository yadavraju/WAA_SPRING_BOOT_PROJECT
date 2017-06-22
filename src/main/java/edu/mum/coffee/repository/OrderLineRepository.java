package edu.mum.coffee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mum.coffee.domain.Orderline;


public interface OrderLineRepository  extends JpaRepository<Orderline, Integer> {

}
