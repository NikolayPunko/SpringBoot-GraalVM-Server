package com.savushkin.Edi.repositories;

import com.savushkin.Edi.model.directory.NsWebOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NsWebOrgRepository extends JpaRepository<NsWebOrg, Integer> {
}
