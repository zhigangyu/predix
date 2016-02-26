package com.pactera.predix.seed.data.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.predix.seed.data.dao.LatheDao;
import com.pactera.predix.seed.data.domain.Lathe;
import com.pactera.predix.seed.data.domain.Machine;

@RestController
public class SeedService {
	
	@Autowired
	private LatheDao latheDao;
	
	@RequestMapping(value = "/api/q", method = RequestMethod.GET, produces = { "application/json" })
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
	
	@RequestMapping(value = "/api/lathes", method = RequestMethod.POST, produces = { "application/json" })
	public int saveLathe(@RequestBody Lathe lathe){
		latheDao.saveLathe(lathe);
		return lathe.getId();
	}
	
	@RequestMapping(value = "/api/lathes", method = RequestMethod.GET, produces = { "application/json" })
	public List<Lathe> queryLathes(){
		return latheDao.queryLathes();
	}
	
	@RequestMapping(value = "/api/lathes", method = RequestMethod.PUT, produces = { "application/json" })
	public void updateLathes(@RequestBody Lathe lathe){
		latheDao.updateLathe(lathe);
	}
	
	@RequestMapping(value = "/api/lathes/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
	public void removeLathes(@PathVariable int id){
		latheDao.deleteLatheById(id);
	}
}
