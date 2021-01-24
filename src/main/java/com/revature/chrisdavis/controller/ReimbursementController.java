package com.revature.chrisdavis.controller;

import java.util.List;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.chrisdavis.ApplicationDriver;
import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.service.ReimbursementService;

import io.javalin.http.Handler;

public class ReimbursementController {
	private ReimbursementService rService;
	
	public ReimbursementController() { }
	public ReimbursementController(ReimbursementService rService) { this.rService = rService; }
	
	
	public Handler getAuthorReimb = (ctx) -> {
		String filter = ctx.header("filter").substring(8);
		List<Reimbursement> reimb;
		if(filter.equals("All")) {
			reimb = rService.selectAllReimbByUserName(ctx.sessionAttribute("username"));
		}else {
			reimb = rService.selectAllReimbByUserName(ctx.sessionAttribute("username"), filter);
		}
		ObjectMapper mapper = new ObjectMapper();
		TreeNode json = mapper.readTree(reimb.toString());
		ctx.json(json);
	};
	
	public Handler getProcessReimb = (ctx) ->{
		String filter = ctx.header("filter").substring(8);
		List<Reimbursement> reimb;
		if(filter.equals("All")) {
			reimb = rService.selectReimbToResolveBySessionUser(ctx.sessionAttribute("username"));
		} else {
			reimb = rService.selectReimbToResolveBySessionUser(ctx.sessionAttribute("username"), filter);
		}
		ObjectMapper mapper = new ObjectMapper();
		TreeNode json = mapper.readTree(reimb.toString());
		ctx.json(json);
	};
	
	public Handler requestReimbursement = (ctx) ->{
		String expenseType = ctx.formParam("requestType");
		String expenseAmount = ctx.formParam("requestAmount");
		String expenseDescription = ctx.formParam("requestDescription");
		if(rService.createRequest(ctx.sessionAttribute("username"), expenseType, expenseAmount, expenseDescription)) {
			ctx.status(201);
			ApplicationDriver.logActivity.info("User <"+ctx.sessionAttribute("username")+"> has submitted an expense request");
		} else ctx.status(400);
		ctx.redirect("/html/welcome.html");
	};
	
	public Handler updateReimbursement = (ctx) ->{
		String requestId = ctx.formParam("requestId");
		String requestStatus = ctx.formParam("requestType");
		String requestComments = ctx.formParam("resolverComments");
		if(rService.updateRequest(ctx.sessionAttribute("username"), requestId, requestStatus, requestComments)) {
			ctx.status(200);
			ApplicationDriver.logActivity.info("User <"+ctx.sessionAttribute("username")+"> has submitted an expense request");
		} else ctx.status(400);
	};

}
