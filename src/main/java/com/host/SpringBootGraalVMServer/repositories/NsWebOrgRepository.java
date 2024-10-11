package com.host.SpringBootGraalVMServer.repositories;

import com.host.SpringBootGraalVMServer.model.directory.NsWebOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NsWebOrgRepository extends JpaRepository<NsWebOrg, Integer> {
}
