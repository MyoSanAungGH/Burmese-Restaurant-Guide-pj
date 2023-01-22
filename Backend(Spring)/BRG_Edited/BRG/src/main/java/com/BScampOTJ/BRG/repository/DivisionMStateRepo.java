package com.BScampOTJ.BRG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BScampOTJ.BRG.entity.DivisionMState;

@EnableJpaRepositories
public interface DivisionMStateRepo extends JpaRepository<DivisionMState, Integer> {

}
