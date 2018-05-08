package com.sandeep.backend.repository;

import com.sandeep.backend.model.code.Code;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends MongoRepository<Code,String> {
}
