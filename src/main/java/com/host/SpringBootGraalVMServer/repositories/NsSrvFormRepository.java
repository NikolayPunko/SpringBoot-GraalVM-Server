package com.host.SpringBootGraalVMServer.repositories;

import com.host.SpringBootGraalVMServer.model.directory.NsSrvForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NsSrvFormRepository extends JpaRepository<NsSrvForm, Integer> {
}
