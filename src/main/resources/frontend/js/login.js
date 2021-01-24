
window.onload = function(){
	window.location.reload();	
}

function loginUser(){
	let response = new XMLHttpRequest();
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

