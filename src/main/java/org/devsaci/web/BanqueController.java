package org.devsaci.web;

import org.devsaci.entities.Compte;
import org.devsaci.entities.Operation;
import org.devsaci.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueController {
	@Autowired
	private IBanqueMetier iBanqueMetier;
	
	@RequestMapping("/operations") 
	public String index() {   
		 return "comptes"; 
	 }

	@RequestMapping("/consultercompte")
	public String consulterCompte(Model model,String codeCompte,
			@RequestParam(name="page",defaultValue="0") int page,
			@RequestParam(name="size",defaultValue="5")int size) { 
		model.addAttribute("codeCompte",codeCompte);
		try {
			Compte compte=iBanqueMetier.getCompte(codeCompte);
			model.addAttribute("compte",compte);
			Page<Operation> pageOperations = 
					iBanqueMetier.listOperationsCompte(codeCompte, page, size);
			model.addAttribute("listOperations", pageOperations.getContent());
			int[] pages=new int[pageOperations.getTotalPages()];
			model.addAttribute("pages", pages);
		} catch (Exception e) {
			model.addAttribute("exception",e);
		}	
		 return "comptes"; 
	 }
	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)
	public String saveOperation(Model model,
			String typeOperation,
			String codeCompte,
			double montant,
			String codeCompte2) {
		
		
		try {

			if (typeOperation.contentEquals("VERS")) {
				iBanqueMetier.versement(codeCompte,montant);
			}
			else if (typeOperation.equals("RETR")) {
				iBanqueMetier.retrait(codeCompte,montant);
			} 
			if (typeOperation.equals("VIR")){
				iBanqueMetier.virement(codeCompte,codeCompte2,montant);
			}	
			
		} catch (Exception e) {
			model.addAttribute("error",e);
			return "redirect:/consultercompte?codeCompte="+codeCompte+"&error="
					+e.getMessage();
		}
			
		return "redirect:/consultercompte?codeCompte="+codeCompte;
		
	}
	
}
