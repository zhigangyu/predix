package com.pactera.predix.seed.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.predix.seed.data.dao.LatheDao;
import com.pactera.predix.seed.data.domain.Lathe;
import com.pactera.predix.seed.data.domain.LatheEvent;
import com.pactera.predix.seed.data.domain.LatheEventValue;
import com.pactera.predix.seed.data.domain.Machine;

@RestController
public class SeedService {
	
	@Autowired
	private LatheDao latheDao;
	
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
	/**
	 * query lathe
	 * @return
	 */
	@RequestMapping(value = "/api/machines", method = RequestMethod.GET, produces = { "application/json" })
	public List<Machine> queryMachines() {
		return latheDao.queryMachines();
	}
	/**
	 * add a new lathe
	 * @param lathe
	 * @return
	 */
	@RequestMapping(value = "/api/machines", method = RequestMethod.POST, produces = { "application/json" })
	public String saveMachines(@RequestBody Machine lathe){
		latheDao.insertMachine(lathe);
		return "OK";
	}
	/**
	 * save lateh status
	 * @param latheEvent
	 * @return
	 */
	@RequestMapping(value = "/api/latheevent", method = RequestMethod.POST, produces = { "application/json" })
	public String saveLatheEvent(@RequestBody LatheEvent latheEvent){
		latheDao.insertLatheEvent(latheEvent);
		return "OK";
	}
	/**
	 * query current machine status
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/api/lathe/{code}", method = RequestMethod.GET, produces = { "application/json" })
	public List<LatheEventValue> queryLatheValue(@PathVariable("code") String code){
		return latheDao.queryLatheEventValue(code);
	}
	
}
