package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by amarendra on 25/1/17.
 */
@RepositoryRestResource
public interface PersonRepository  extends MongoRepository<Person, String>{

    List<Person> findByName(@Param("name") String name);

    List<Person> findPersonByAddressCity(@Param("city") String city);

    List<Person> findPersonByAddressHouseNumber(@Param("houseNumber") String houseNumber);

}
