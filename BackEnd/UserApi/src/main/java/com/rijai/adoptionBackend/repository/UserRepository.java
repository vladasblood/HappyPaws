package com.rijai.adoptionBackend.repository;

import com.rijai.adoptionBackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>
{
}