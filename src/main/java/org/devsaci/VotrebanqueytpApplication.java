package org.devsaci;

import java.util.Date;

import org.devsaci.dao.IClientRepository;
import org.devsaci.dao.ICompteRepository;
import org.devsaci.dao.IOperationRepository;
import org.devsaci.entities.Client;
import org.devsaci.entities.Compte;
import org.devsaci.entities.CompteCourant;
import org.devsaci.entities.CompteEpargne;
import org.devsaci.entities.Retrait;
import org.devsaci.entities.Versement;
import org.devsaci.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotrebanqueytpApplication implements CommandLineRunner {
	@Autowired
	private IClientRepository iClientRepository;
	@Autowired
	private ICompteRepository iCompteRepository;
	@Autowired
	private IOperationRepository iOperationRepository;
	@Autowired
	private IBanqueMetier iBanqueMetier;

	public static void main(String[] args) {
		SpringApplication.run(VotrebanqueytpApplication.class, args);
		/*
		 * ApplicationContext ctx =
		 * SpringApplication.run(VotrebanqueytpApplication.class, args);
		 * IClientRepository iClientRepository = ctx.getBean(IClientRepository.class);
		 * iClientRepository.save(new Client("hassan","adresseHassane"));
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		

		Client client1 = iClientRepository.save(new Client("Hassan", "hassan@gmail.com"));
		Client client2 = iClientRepository.save(new Client("nassima", "nassima@gmail.com"));

		Compte compte1 = iCompteRepository.save(new CompteCourant
				("compte1", new Date(), 90000.0, client1, 6000.0));
		Compte compte2 = iCompteRepository.save(new CompteEpargne
				("compte2", new Date(), 6000.0, client2, 5.5));

		// operations de compte1
		iOperationRepository.save(new Versement(new Date(), 9000.0, compte1));
		iOperationRepository.save(new Versement(new Date(), 6000.0, compte1));
		iOperationRepository.save(new Versement(new Date(), 2300.0, compte1));
		iOperationRepository.save(new Retrait(new Date(), 9000.0, compte1));

		// operations de compte2
		iOperationRepository.save(new Versement(new Date(), 9000.0, compte1));
		iOperationRepository.save(new Versement(new Date(), 6000.0, compte1));
		iOperationRepository.save(new Versement(new Date(), 2300.0, compte1));
		iOperationRepository.save(new Retrait(new Date(), 9000.0, compte1));
		
		
		//Tester la couche Metier
		iBanqueMetier.versement("compte1", 111111.0);
		
		
	

	}

}
