package org.devsaci.dao;

import org.devsaci.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompteRepository  extends JpaRepository<Compte, String>{


}
