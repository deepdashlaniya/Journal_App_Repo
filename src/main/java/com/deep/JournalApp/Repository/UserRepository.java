package com.deep.JournalApp.Repository;

import com.deep.JournalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,ObjectId>{

    public User findByUserName(String userName);

    public void deleteByuserName(String userName);
}
