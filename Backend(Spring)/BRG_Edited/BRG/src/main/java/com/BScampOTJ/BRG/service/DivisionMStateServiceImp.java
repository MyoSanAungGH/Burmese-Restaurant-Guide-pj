package com.BScampOTJ.BRG.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BScampOTJ.BRG.entity.DivisionMState;
import com.BScampOTJ.BRG.repository.DivisionMStateRepo;

@Service
public class DivisionMStateServiceImp implements DivisionMStateService {
	
	@Autowired
	DivisionMStateRepo dMsRepo;

	@Override
	public List<DivisionMState> getAll() {
		return dMsRepo.findAll();
	}

	@Override
	public DivisionMState get(int id) {
		return dMsRepo.findById(id).orElse(null);
	}

	@Override
	public DivisionMState create(DivisionMState divMstate) {
		return dMsRepo.save(divMstate);
	}

	@Override
	public DivisionMState update(int id, DivisionMState divMstate) {
		DivisionMState updateDivMstate = this.get(id);
		if(updateDivMstate == null) {
			return null;
		}
		updateDivMstate.setCapital(divMstate.getCapital());
		updateDivMstate.setName(divMstate.getName());
		return dMsRepo.save(divMstate);
	}

	@Override
	public boolean delete(int id) {
		DivisionMState  divMstate = this.get(id);
		if(divMstate == null) {
			return false;
		}
		dMsRepo.delete(divMstate);
		return true;
	}
	


}
