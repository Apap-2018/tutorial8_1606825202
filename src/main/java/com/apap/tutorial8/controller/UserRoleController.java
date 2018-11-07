package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}

	@RequestMapping(value = "/updatePassword")
	private String updatePasswordSubmit(Model model, @ModelAttribute UserRoleModel tempUser, String newPassword, String confirmationPassword) {
		UserRoleModel realUser = userService.getUserByUsername(tempUser.getUsername());
		
		if (userService.isMatch(tempUser.getPassword(), realUser.getPassword())) {
			if (newPassword.equals(confirmationPassword)) {
				realUser.setPassword(newPassword);
				userService.addUser(realUser);
				model.addAttribute("msg", "Password berhasil diubah");
			}
			else {
				model.addAttribute("msg", "Password Baru harus sama dengan Konfirmasi Password Baru!");
			}
		}
		else {
			model.addAttribute("msg", "Password Lama salah!");
		}
		
		return "home";
	}
}

