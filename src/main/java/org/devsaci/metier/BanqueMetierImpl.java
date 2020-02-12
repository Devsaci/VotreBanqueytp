package org.devsaci.metier;

import java.util.Date;

import org.devsaci.dao.ICompteRepository;
import org.devsaci.dao.IOperationRepository;
import org.devsaci.entities.Compte;
import org.devsaci.entities.CompteCourant;
import org.devsaci.entities.Operation;
import org.devsaci.entities.Retrait;
import org.devsaci.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	@Autowired
	private ICompteRepository compteRepository;
	@Autowired
	private IOperationRepository operationRepository;

	@Override
	public Compte getCompte(String codeCompte) {
		Compte compte = compteRepository.findById(codeCompte).orElse(null);
		if (compte == null)
			throw new RuntimeException("Compte introuvable");
		return compte;
	}

	@Override
	public void versement(String codeCompte, double montant) {
		Compte compte = getCompte(codeCompte);
		Versement versement = new Versement(new Date(), montant, compte);
		operationRepository.save(versement);
		compte.setSolde(compte.getSolde() + montant);
		compteRepository.save(compte);
	}

	@Override
	public void retrait(String codeCompte, double montant) {
		Compte compte = getCompte(codeCompte);
		double facilitesCaisse = 0;
		if (compte instanceof CompteCourant) {

			facilitesCaisse = ((CompteCourant) compte).getDecouvert();

			if (compte.getSolde() + facilitesCaisse < montant)
				throw new RuntimeException("Slode insuffisant");
		}

		Retrait retrait = new Retrait(new Date(), montant, compte);
		operationRepository.save(retrait);
		compte.setSolde(compte.getSolde() - montant);
		compteRepository.save(compte);

	}

	@Override
	public void virement(String codeCompte1, String codeCompte2, double montant) {
		if(codeCompte1.equals(codeCompte2))
	throw new RuntimeException
	("Impossible : On ne peut pas effectuer un virement sur le meme compte");
		retrait(codeCompte1, montant);
		versement(codeCompte2, montant);

	}

	@Override
	public Page<Operation> listOperationsCompte(String codeCompte, int page, int sizePage) {

		return operationRepository.listOperation(codeCompte, new PageRequest(page, sizePage));
	}

}
