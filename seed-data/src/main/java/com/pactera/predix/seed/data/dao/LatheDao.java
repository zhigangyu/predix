package com.pactera.predix.seed.data.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pactera.predix.seed.data.domain.Lathe;

@Repository
public class LatheDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void saveLathe(Lathe lathe) {
		jdbcTemplate.update("insert into lathe (lathename,lathetype,status) VALUES (?,?,?)",
				new Object[] { lathe.getLatheName(), lathe.getLatheType(),lathe.getStatus() });
	}
	
	public List<Lathe> queryLathes(){
		return jdbcTemplate.query("select ID,lathename,lathetype,status from lathe",new RowMapper<Lathe>(){

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
				new Object[] { lathe.getLatheName(), lathe.getLatheType(),lathe.getStatus(), lathe.getId()});		
	}

	public void deleteLatheById(int id) {
		jdbcTemplate.update("delete from lathe where id=?", new Object[]{id});
		
	}

}
