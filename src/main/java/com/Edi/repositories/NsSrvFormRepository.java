package com.Edi.repositories;

import com.Edi.model.directory.NsSrvForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NsSrvFormRepository extends JpaRepository<NsSrvForm, Integer> {
}
