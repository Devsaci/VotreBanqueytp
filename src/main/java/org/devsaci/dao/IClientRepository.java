package org.devsaci.dao;

import org.devsaci.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<Client, Long>{

}
