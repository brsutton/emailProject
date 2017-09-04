package com.sutton.email.rowmapper;


import java.sql.ResultSet;  
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.User;

/**
 * RowMapper for the User Class, Use this with the jdbc template to get data from the database
 * @author Brian Sutton
 *
 */
public final class UserRowMapper implements RowMapper<User> {
	

	/**
	 * Sets the fields of the User Objects for each row that is returned from the database
	 */
	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		User user=new User();
		user.setId(resultSet.getInt("id"));
		user.setName(resultSet.getString("name"));
		user.setContact(resultSet.getString("contact"));
		user.setUserName(resultSet.getString("user_name"));
		user.setPassword(resultSet.getString("password"));
		return user;
	}
}