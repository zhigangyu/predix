package com.pactera.predix.seed.data.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.predix.seed.data.domain.Machine;

@RestController
public class SeedService {
	
	@RequestMapping(value = "/q", method = RequestMethod.GET, produces = { "application/json" })
	public List<Machine> queryMachines() {
		Machine m = new Machine();
		m.setId("M1");
		m.setDuration(456);
		m.setEfficiency(50);
		m.setName("MH98U");
		m.setStart(new Date());
		m.setType("M");
		List<Machine> list = new Vector<Machine>();
		list.add(m);
		return list;
	}
}
