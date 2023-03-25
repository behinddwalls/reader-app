package com.codeforsurvival.manager;

import java.util.List;

import com.codeforsurvival.db.entity.User;

	public interface UserManager {

	public List<User> getAllUser();

	public User addUser(User user);

	public User getUserById(Long id);

	public User getUserByUsername(String username);

	public User deleteUserById(Long id);

	public User deleteUser(User user);

}
