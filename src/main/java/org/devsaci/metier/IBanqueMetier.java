package org.devsaci.metier;

import org.devsaci.entities.Compte;
import org.devsaci.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBanqueMetier {
	public Compte getCompte(String codeCompte);

	public void versement(String codeCompte, double montant);

	public void retrait(String codeCompte, double montant);

	public void virement(String codeCompteRetrait, String codeCompteVersement, double montant);

	Page<Operation> listOperationsCompte(String codeCompte, int page, int sizePage);
}
