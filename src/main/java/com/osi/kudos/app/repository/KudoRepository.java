package com.osi.kudos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osi.kudos.app.model.Employee;
import com.osi.kudos.app.model.Kudo;

@Repository
public interface KudoRepository extends JpaRepository<Kudo, Long>{

}
