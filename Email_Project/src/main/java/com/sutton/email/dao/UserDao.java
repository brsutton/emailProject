package com.sutton.email.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sutton.email.rowmapper.UserRowMapper;

import model.User;

@Repository
public class UserDao {

	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate=new JdbcTemplate(this.dataSource);
	}
	
	public int insertUser(User user) {
		
		String sql="insert into users "
				+ "(name, user_name, contact, password)"
				+ "values(?,?,?,?)";
		
		return jdbcTemplate.update(sql, new Object[] {user.getName(), user.getUserName(), 
				user.getContact(), user.getPassword()});
	}
	
	public List <User> getUsers(){
		try {
		return jdbcTemplate.query("select * from users", new UserRowMapper());
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
