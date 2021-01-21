
window.onload = function(){
	document.getElementById("logoutSession").addEventListener("click", logoutSession);
	requestNavUser();
}


$('#createrequestform').submit(function(e){
    e.preventDefault();
	let formdata = $(this).serialize();
	$('#requestReimbursement').modal('toggle');
	$.post('/mi6/users/requestreimbursement', formdata);
});

$('#requestReimbursement').on('hidden.bs.modal', function () {
	document.getElementById('createrequestform').reset();
})


function logoutSession(){
	let response = new XMLHttpRequest();
	response.onreadystatechange = function(){
		if(response.readyState == 4 && response.status == 200){
			window.location.replace('/html/login.html');
		}
	}
	
	response.open('GET', '/mi6/users/logout');
	response.send();
}


function requestNavUser(){
	let response = new XMLHttpRequest();
	response.onreadystatechange = function(){
		let userCredentials = response.responseText.split(",", 2);
		if(response.readyState == 4 && response.status == 200){
			console.log("response = 200");
			document.getElementById('profileDropdown').innerHTML = `${userCredentials[0]}`;
			document.getElementById('clearancelevel').innerHTML = `Clearance Level: ${userCredentials[1]}`;
		}else if(response.readyState == 4 && response.status == 401){
			console.log("response = 401");
			window.location.replace('/html/loginretry.html');
		}
	}
	response.open('GET', '/mi6/users/sessionuser');
	response.send();
}
