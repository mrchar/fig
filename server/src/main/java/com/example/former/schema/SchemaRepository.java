package com.example.former.schema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaRepository extends JpaRepository<SchemaEntity, Long> {
}
