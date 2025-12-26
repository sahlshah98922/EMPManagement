package com.ems.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entity.Clover;
import com.ems.service.ICloverService;



@Controller
public class CloverController {
	

	@Autowired
	private ICloverService cloverService;

	@RequestMapping(value = "/list-clovers", method = RequestMethod.GET)
	public String showClover(ModelMap model) {

		String name = getLoggedInUserName(model);
		model.put("clovers", cloverService.getAllDetailes());
		return "list-clovers";

	}

	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

	@RequestMapping(value = "/add-clover", method = RequestMethod.GET)
	public String showAddCloverPage(ModelMap model) {
		model.addAttribute("clover", new Clover());
		System.out.println("43332");
		return "clover";
	}

	@RequestMapping(value = "/delete-clover", method = RequestMethod.GET)
	public String deleteClover(@RequestParam long id) {

		cloverService.deleteClover(id);
		return "redirect:/list-clovers";
	}

	@RequestMapping(value = "/update-clover", method = RequestMethod.GET)
	public String showUpdateCloverPage(@RequestParam long id, ModelMap model) {
		Clover clover = cloverService.getCloverById(id).get();
		model.put("clover", clover);
		return "clover";
	}
        
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String showUpdatescloverPage(@RequestParam long id, ModelMap model) { //view page call
		Clover clover = cloverService.getCloverById(id).get();
		model.put("clover", clover);
		return "view";
	}
	
	@RequestMapping(value = "/update-clover", method = RequestMethod.POST)
	public String updateclover(ModelMap model, @Validated Clover clover, BindingResult result) {

		if (result.hasErrors()) {
			return "clover";
		}

		clover.setName(getLoggedInUserName(model));
		cloverService.updateClover(clover);
		return "redirect:/list-clovers";
	}

	@RequestMapping(value = "/add-clover", method = RequestMethod.POST)
	public String addClover(ModelMap model, @Validated Clover clover, BindingResult result) {
		if (result.hasErrors()) {
			return "clover";

		}

		clover.setName(getLoggedInUserName(model));
		System.out.println("clover" + clover);
		cloverService.saveClover(clover);
		System.out.println("after saving" + clover);
		System.out.println("save");
		return "redirect:/list-clovers";
	}
}
