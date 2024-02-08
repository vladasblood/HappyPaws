package com.rijai.adoptionBackend.repository;

import com.rijai.adoptionBackend.model.Dog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends CrudRepository<Dog, Integer> {
}
