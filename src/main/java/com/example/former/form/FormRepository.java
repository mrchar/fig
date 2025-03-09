package com.example.former.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Long> {}
