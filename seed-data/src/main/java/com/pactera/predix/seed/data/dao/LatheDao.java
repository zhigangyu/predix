package com.pactera.predix.seed.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pactera.predix.seed.data.domain.Lathe;
import com.pactera.predix.seed.data.domain.LatheEvent;
import com.pactera.predix.seed.data.domain.LatheEventValue;
import com.pactera.predix.seed.data.domain.Machine;

@Repository
public class LatheDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void saveLathe(Lathe lathe) {
		jdbcTemplate.update("insert into lathe (lathename,lathetype,status) VALUES (?,?,?)",
				new Object[] { lathe.getLatheName(), lathe.getLatheType(), lathe.getStatus() });
	}

	public List<Lathe> queryLathes() {
		return jdbcTemplate.query("select ID,lathename,lathetype,status from lathe", new RowMapper<Lathe>() {

			@Override
			public Lathe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lathe lathe = new Lathe();
				lathe.setId(rs.getInt(1));
				lathe.setLatheName(rs.getString(2));
				lathe.setLatheType(rs.getString(3));
				lathe.setStatus(rs.getInt(4));
				return lathe;
			}

		});
	}

	public void updateLathe(Lathe lathe) {
		jdbcTemplate.update("update lathe set lathename=?,lathetype=?,status=? where id=?",
				new Object[] { lathe.getLatheName(), lathe.getLatheType(), lathe.getStatus(), lathe.getId() });
	}

	public void deleteLatheById(int id) {
		jdbcTemplate.update("delete from lathe where id=?", new Object[] { id });

	}

	public void insertLatheEvent(LatheEvent event) {
		try {
			List<LatheEventValue> latheEvent = queryLatheEventValue(event.getLatheCode());
			Map<String,String> map = new HashMap<String,String>();
			for(LatheEventValue le : latheEvent){
				map.put(le.getLable(), le.getValue());
			}
			if(map.containsKey(event.getLable()) && map.get(event.getLable()).equals(event.getValue())){
				return;
			}
			
			jdbcTemplate.update(
					"insert into lathe_event (d_dateline,c_lathe_code,c_lable,c_value) VALUES (current_timestamp,?,?,?)",
					new Object[] { event.getLatheCode(), event.getLable(), event.getValue() });
			/*
			map.put(event.getLable(), event.getValue());
			int status = 0;
			if(map.get("power").equals("1")){
				status = 1;
				if(map.get("program").equals("0")){
					status = 3;
				}
			}
			if(map.get("alarm").equals("1")){
				status = 2;
			}
			jdbcTemplate.update("update lathe_machine set n_status=? where c_lathe_code=?",
					new Object[] {status, event.getLatheCode() });
			*/
		} catch (Exception e) {

		}

	}

	public void insertMachine(Machine machine) {
		jdbcTemplate.update(
				"insert into lathe_machine (c_lathe_code,d_create_date,n_status) VALUES (?,current_timestamp,?)",
				new Object[] { machine.getLatheCode(), machine.getStatus() });
	}

	public List<Machine> queryMachines() {
		return jdbcTemplate.query("select c_lathe_code,  max(CASE c.c_lable WHEN 'part number' THEN c.c_value ELSE '' END) AS part_number, "
				+ "max(CASE c.c_lable WHEN 'power' THEN c.c_value ELSE '' END) AS power, "
				+ "max(CASE c.c_lable WHEN 'program' THEN c.c_value ELSE '' END) AS program, "
				+ "max(CASE c.c_lable WHEN 'alerm' THEN c.c_value ELSE '' END) AS alerm, "
				+ "max(CASE c.c_lable WHEN 'procedure' THEN c.c_value ELSE '' END) AS procedure "
				+ "from  (select le.c_lable,le.c_value,le.c_lathe_code from lathe_event le, "
				+ "(select max(d_dateline) dl,c_lable,c_lathe_code "
				+ "from lathe_event group by  c_lable,c_lathe_code) b "
				+ "where le.d_dateline=b.dl and le.c_lable=b.c_lable and b.c_lathe_code=le.c_lathe_code) c "
				+ "group by c_lathe_code",
				new RowMapper<Machine>() {

					@Override
					public Machine mapRow(ResultSet rs, int rowNum) throws SQLException {
						Machine m = new Machine();
						m.setLatheCode(rs.getString("c_lathe_code"));
						int status = 0;
						if(rs.getString("power").equals("1")){
							if(rs.getString("program").equals("1")){
								status = 1;
							}else{
								status = 3;
							}
						}
						
						if(rs.getString("alerm").equals("1")) status = 2;
						m.setStatus(status);
						m.setPartNumber(rs.getString("part_number"));
						m.setProcedure(rs.getString("procedure"));
						return m;
					}

				});
	}

	public List<LatheEventValue> queryLatheEventValue(String code) {
		return jdbcTemplate.query(
				"select le.c_lable,le.c_value from lathe_event le,"
						+ " (select max(d_dateline) dl,c_lable from lathe_event where c_lathe_code=? group by  c_lable) b"
						+ " where le.d_dateline=b.dl and le.c_lable=b.c_lable and c_lathe_code=? order by le.c_lable",
				new RowMapper<LatheEventValue>() {

					@Override
					public LatheEventValue mapRow(ResultSet rs, int rowNum) throws SQLException {
						LatheEventValue m = new LatheEventValue();
						m.setLable(rs.getString(1));
						m.setValue(rs.getString(2));
						return m;
					}

				}, new Object[] { code, code });
	}
}
