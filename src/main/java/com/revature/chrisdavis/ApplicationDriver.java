package com.revature.chrisdavis;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.chrisdavis.controller.ReimbursementController;
import com.revature.chrisdavis.controller.UserController;
import com.revature.chrisdavis.dao.ReimbursementDao;
import com.revature.chrisdavis.dao.ReimbursementStatusDao;
import com.revature.chrisdavis.dao.ReimbursementTypeDao;
import com.revature.chrisdavis.dao.UserDao;
import com.revature.chrisdavis.dao.UserRoleDao;
import com.revature.chrisdavis.model.Reimbursement;
import com.revature.chrisdavis.model.ReimbursementStatus;
import com.revature.chrisdavis.model.ReimbursementType;
import com.revature.chrisdavis.model.User;
import com.revature.chrisdavis.model.UserRole;
import com.revature.chrisdavis.service.ReimbursementService;
import com.revature.chrisdavis.service.UserService;
import com.revature.chrisdavis.util.HibernateUtil;

import io.javalin.Javalin;


public class ApplicationDriver {
	//public static final String UNAUTHORIZED = "Unauthorized";
	//public static String sessionUsername = UNAUTHORIZED;
	//public static String sessionClearance = UNAUTHORIZED;
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM-DD-YYYY HH:mm:ss");
	private static HibernateUtil hiUtil = new HibernateUtil();
	
	private static ReimbursementDao rDao = new ReimbursementDao(hiUtil);
	private static ReimbursementTypeDao reimbTypeDao = new ReimbursementTypeDao(hiUtil);
	private static ReimbursementStatusDao reimbStatusDao = new ReimbursementStatusDao(hiUtil);
	
	private static UserDao userDao = new UserDao(hiUtil);
	private static UserRoleDao userRoleDao = new UserRoleDao(hiUtil);
	
	private static UserController uController = new UserController(new UserService(userDao, userRoleDao));
	private static ReimbursementController rController = new ReimbursementController(new ReimbursementService(rDao, userDao, reimbStatusDao));
	
	public static Logger logActivity = Logger.getLogger(ApplicationDriver.class);
	
	public static void main(String[] args) {
		Javalin app = Javalin.create(config -> {
			config.addStaticFiles("/frontend");
		});
		app.start(9009);
		logActivity.setLevel(Level.INFO);
		
		app.post("/mi6/users/login", uController.postLogin);
		app.post("/mi6/users/requestreimbursement", rController.requestReimbursement);
		app.patch("/mi6/users/resolvereimursement", rController.updateReimbursement);
		app.get("/mi6/users/reimbursements", rController.getAuthorReimb);
		app.get("/mi6/users/processreimbursements", rController.getProcessReimb);
		app.get("/mi6/users", uController.getAllUsers);
		app.get("/mi6/users/clearance", uController.getUserClearance);
		app.get("/mi6/users/sessionuser", uController.getSessionUser);
		app.get("/mi6/users/logout", uController.getLogout);

//		initializeDatabase();
	}
	
	public static void initializeDatabase() {
		List<UserRole> userRoles = new ArrayList<>();
		UserRole operative = new UserRole("Operative");
		UserRole secretary = new UserRole("Secretary");
		UserRole chief = new UserRole("Chief");
		UserRole head = new UserRole("Head");
		userRoles.add(operative);
		userRoles.add(secretary);
		userRoles.add(chief);
		userRoles.add(head);
		for(UserRole userRole : userRoles) {
			userRoleDao.insertUserRole(userRole);
		}
		
		List<ReimbursementType> reimbTypeList = new ArrayList<>();
		ReimbursementType typeTravel = new ReimbursementType("Travel");
		ReimbursementType typeFood = new ReimbursementType("Food");
		ReimbursementType typeLodging = new ReimbursementType("Lodging");
		ReimbursementType typeEquipment = new ReimbursementType("Equipment");
		ReimbursementType typeLegal = new ReimbursementType("Legal");
		ReimbursementType typeOther = new ReimbursementType("Other");
		reimbTypeList.add(typeTravel);
		reimbTypeList.add(typeFood);
		reimbTypeList.add(typeLodging);
		reimbTypeList.add(typeEquipment);
		reimbTypeList.add(typeLegal);
		reimbTypeList.add(typeOther);
		for(ReimbursementType reimbType : reimbTypeList) {
			reimbTypeDao.insertReimbursementType(reimbType);
		}
		
		List<ReimbursementStatus> reimbStatusList = new ArrayList<>();
		ReimbursementStatus statusPending = new ReimbursementStatus("Pending");
		ReimbursementStatus statusDenied = new ReimbursementStatus("Denied");
		ReimbursementStatus statusApproved = new ReimbursementStatus("Approved");
		reimbStatusList.add(statusPending);
		reimbStatusList.add(statusDenied);
		reimbStatusList.add(statusApproved);
		for(ReimbursementStatus reimbStatus : reimbStatusList) {
			reimbStatusDao.insertReimbursementStatus(reimbStatus);
		}
		
		List<User> userList = new ArrayList<>();
		User userBond = new User("007", "shakennotstirred62", "James", "Bond", "bondjamesbond@mi6.gov", operative);
		User userM = new User("M", "2strive2seek2findANDnot2yield", "M", "REDACTED", "m@mi6.gov", head);
		User userQ = new User("Q", "S=k.logW", "Major", "Boothroyd", "q@mi6.gov", chief);
		User userBill = new User("Bill", "familystateloyalty", "William", "Tanner", "bill.tanner@mi6.gov", chief);
		User userPenny = new User("$.01", "apennyaday", "Money", "Penny", "ms.moneypenny@mi6.gov", secretary);
		User userLoelia = new User("Loelia", "letthegoodtimesloel", "Loelia", "Ponsonby", "loelia.ponsonby@mi6.gov", secretary);
		User userSean = new User("001", "suckitTrebek", "Sean", "Connery", "seancon@mi6.gov", operative);
		User userDavid = new User("002", "casinoroyale", "David", "Niven", "david.niven@mi6.gov", operative);
		User userGeorge = new User("003", "hermajestyssecretservice", "George", "Lazenby", "george.lazenby@mi6.gov", operative);
		User userRoger = new User("004", "live&letdie", "Roger", "Moore", "roger.moore@mi6.gov", operative);
		User userTim = new User("005", "thelivingdaylights", "Timothy", "Dalton", "tim.dalton@mi6.gov", operative);
		User userPierce = new User("006", "goldeneye", "Pierce", "Brosnan", "pierce.brosnan@mi6.gov", operative);
		User userDan = new User("008", "casinoroyale", "Daniel", "Craig", "daniel.craig@mi6.gov", operative);
		userList.add(userBond);
		userList.add(userM);
		userList.add(userQ);
		userList.add(userBill);
		userList.add(userPenny);
		userList.add(userLoelia);
		userList.add(userSean);
		userList.add(userDavid);
		userList.add(userGeorge);
		userList.add(userRoger);
		userList.add(userTim);
		userList.add(userPierce);
		userList.add(userDan);
		for(User user : userList) {
			userDao.insertUser(user);
		}
		
		List<Reimbursement> reimbList = new ArrayList<>();
		Reimbursement reimbRequest = new Reimbursement(59999.99, parseTimestamp("10-22-2016 10:10:19"), parseTimestamp("10-22-2016 09:18:22"), "Purchased Mercedes pursuant of duty to the crown.",
				null, userBond, userQ, "Bond, this is a gross expendeture of bureau resources.", statusDenied, typeEquipment);
		reimbList.add(reimbRequest);
		reimbRequest = new Reimbursement(374.21, parseTimestamp("10-22-2018 16:05:11"), parseTimestamp("10-23-2018 11:03:55"), "Martini bill incurred while blending in during Operation Spiraltop",
				null, userBond, userPenny, "James dear, I think you may have a drinking problem. In the future, try to keep it under 5 cocktails.", statusApproved, typeFood);
		reimbList.add(reimbRequest);
		reimbRequest = new Reimbursement(209.08, parseTimestamp("10-22-1975 13:22:09"), parseTimestamp("10-25-1975 09:05:37"), "Purchased claymore for training The One",
				null, userSean, userLoelia, "Sorry for the delay Connery, there was some confusion regarding what type of claymore mine was purchased. We don't know who this highlander fellow is or why you need a sword, but it appears to be a personal project.", statusDenied, typeEquipment);
		reimbList.add(reimbRequest);
		reimbRequest = new Reimbursement(555.55, parseTimestamp("12-29-2020 13:22:09"), null, "Luncheon with head of state.",
				null, userM, null, null, statusPending, typeFood);
		reimbList.add(reimbRequest);
		reimbRequest = new Reimbursement(2483.88, parseTimestamp("10-22-1996 20:42:36"), null, "Longterm stay at the Marriot wanting on Agent Spectre",
				null, userPierce, userM, "Glad you finally got the bastard, 006!", statusApproved, typeLodging);
		reimbList.add(reimbRequest);
		for(Reimbursement reimb : reimbList) {
			rDao.insertReimbursement(reimb);
		}
	}
	
	public static Timestamp parseTimestamp(String timestamp) {
		 try {
		        return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
		    } catch (ParseException e) {
		        throw new IllegalArgumentException(e);
		    }
	}
}
