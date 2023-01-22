package com.BScampOTJ.BRG.service;

import java.util.List;

import com.BScampOTJ.BRG.entity.DivisionMState;


public interface DivisionMStateService {

	public List<DivisionMState> getAll();

	public DivisionMState get(int id);

	public DivisionMState create(DivisionMState divMstate);

	public DivisionMState update(int id, DivisionMState divMstate);

	public boolean delete(int id);
	
}
