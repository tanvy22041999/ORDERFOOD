package com.spring.food.repositories.UserRepository;
import com.spring.food.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User, String> {
    User findByPhoneNumber(String phoneNumber);

    @Query(value = "{ 'nick_name' : { $regex: ?0, $options: 'i' }}", sort = "{'nick_name': -1}")
    Page<User> findUserByNickNameNear(String nickname, Pageable pageable);
}
