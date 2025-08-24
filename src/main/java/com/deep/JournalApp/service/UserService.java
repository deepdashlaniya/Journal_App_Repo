package com.deep.JournalApp.service;

import com.deep.JournalApp.Entity.User;
import com.deep.JournalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component()
public class UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		
		return userRepository.save(user);
	}

    public User addNewUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add("User");
        return userRepository.save(user);
    }

	public List<User> getAll() {

		return userRepository.findAll();
	}

	public Optional<User> findById(ObjectId myId) {
		return userRepository.findById(myId);
	}

	public boolean removeEntry(ObjectId myId) {
        userRepository.deleteById(myId);
		return true;
	}

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName) {
        userRepository.deleteByuserName(userName);
    }
}
