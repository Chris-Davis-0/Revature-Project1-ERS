/**
 * 
 */

window.onload = function(){
	window.location.reload();	
	//document.getElementById('login-form').addEventListener('submit', loginUser);
	//document.getElementById('submitLogin').addEventListener('click', loginUser);
	
	
}

function loginUser(){
	//event.preventDefault();
	let response = new XMLHttpRequest();
	//alert("Attempting login");
	response.onreadystatechange = function(){
		if(response.readyState == 4 && response.status == 200){
			document.getElementById('loginfailalert').setAttribute('class', '');
			document.getElementById('loginfailalert').innerText('');
		}
		if(response.readyState == 4 && response.status == 401){
			document.getElementById('loginfailalert').setAttribute('class', 'alert alert-danger text-center m-3');
			document.getElementById('loginfailalert').innerHTML('Incorrect login information given');
		}
	}
	
	
	
	response.open('POST', 'http://localhost:9009/mi6/users/login');
	response.send();
}

