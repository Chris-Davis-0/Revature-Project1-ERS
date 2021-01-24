package com.revature.chrisdavis.controller;

import com.revature.chrisdavis.service.UserService;

import io.javalin.http.Handler;

public class UserController {
	private UserService uService;
	
	public UserController() { }
	public UserController(UserService uService) { 
		this.uService = uService; 
	}
	
	
	public Handler postLogin = (ctx) ->{
		String username = ctx.formParam("inputUserName");
		String password = ctx.formParam("inputUserPassword");
		if(uService.verifyLogin(username, password)) {
			ctx.status(200);
			ctx.sessionAttribute("username", username);
			ctx.sessionAttribute("clearance", uService.getUserRoleByUsername(username));
		} else{
			ctx.status(401);
		}
		if(ctx.status() == 200) ctx.redirect("/html/welcome.html");
		if(ctx.status() == 401) ctx.redirect("/html/loginretry.html"); 
	};
	
	
	public Handler getAllUsers = (ctx) ->{
		ctx.result(uService.getAllUsers().toString());	
	};
	
	public Handler getUser = (ctx) -> {
		String username = ctx.pathParam("uid");
		try{
			ctx.result(uService.selectUserByUserName(username).toString());
			ctx.status(200);
		}catch(NullPointerException e) {
			ctx.result("User not found");
			ctx.status(404);
		}
	};
	
	public Handler getSessionUser = (ctx) ->{
		String sessionUser = (String)ctx.sessionAttribute("username");
		String sessionClearance = (String)ctx.sessionAttribute("clearance");
		if(sessionClearance == "Unauthorized" || sessionClearance == null) {
			ctx.status(401);
		} else ctx.status(200);
		ctx.result(sessionUser+","+sessionClearance);
	};
	
	public Handler getUserClearance = (ctx) ->{
		ctx.result((String)ctx.sessionAttribute("clearance"));
		if(ctx.sessionAttribute("clearance") == "Unauthorized") ctx.status(401);
		ctx.status(200);
		
	};
	
	public Handler getLogout = (ctx) ->{
		ctx.sessionAttribute("username", "Unauthorized");
		ctx.sessionAttribute("clearance", "Unauthorized");
		ctx.status(200);
	};
	
	
}
