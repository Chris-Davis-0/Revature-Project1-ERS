
window.onload = function(){
	document.getElementById("logoutSession").addEventListener("click", logoutSession);
	document.getElementById('filterStatus').addEventListener('change', getReimbursements);
	requestNavUser();
	getReimbursements();
}

$('#closeModal').on('click', function () {
	location.getReimbursements();
})

function requestNavUser(){
	let response = new XMLHttpRequest();
	response.onreadystatechange = function(){
		let userCredentials = response.responseText.split(",", 2);
		if(response.readyState == 4 && response.status == 200){
			document.getElementById('profileDropdown').innerHTML = `${userCredentials[0]}`;
			document.getElementById('clearancelevel').innerHTML = `Clearance Level: ${userCredentials[1]}`;
		}else if(response.readyState == 4 && response.status == 401){
			window.location.replace('/html/loginretry.html');
		}
	}
	response.open('GET', '/mi6/users/sessionuser');
	response.send();
}

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

function getReimbursements(){
	let statusFilter = document.getElementById('filterStatus').value;
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState == 4 && xhttp.status==200){
			createTable(JSON.parse(xhttp.responseText));
		}
	}
	xhttp.open('GET', '/mi6/users/reimbursements');
	xhttp.setRequestHeader("filter", statusFilter);
	xhttp.send();
}


function createTable(reimbursements){
	let tableBody = document.getElementById("insertTable");
	tableBody.innerHTML = ""; //clear table before appending (filtered) entries
	
	for(let i in reimbursements){
		let cellIndex = 0;
		let addRow = tableBody.insertRow(-1);
		addRow.setAttribute("data-toggle","collapse");
		addRow.setAttribute("data-target",`#collapsedRow${i}`);
		addRow.setAttribute("class","accordion-toggle");
		for(let element in reimbursements[i]){
			if(element == "Description"){
				let hiddenRow = document.createElement("tr");
				hiddenRow.innerHTML = 
							`<td colspan="8" class="hiddenRow">
					            <div class="collapse" id="collapsedRow${i}">
									<p>Description: ${reimbursements[i]['Description']}</p>
									<p>Resolver Comments: ${reimbursements[i]['Comments']}
								</div> 
				        	</td>`;
				tableBody.appendChild(hiddenRow);
				break;
			}
			let addCell = addRow.insertCell(cellIndex);
			if(element == "Status"){
				let statusText = reimbursements[i]['Status'];
				let statusRequestId = reimbursements[i]['ID'];
				
				if(statusText == 'Pending'){
					addCell.innerHTML  = 
						`<button type="button" class="btn btn-warning" disabled>${statusText}</button>`;
				}else if(statusText == 'Approved'){
					addCell.innerHTML = 
						`<button type="button" class="btn btn-success" disabled>${statusText}</button>`;
				}else if(statusText == 'Denied'){
					addCell.innerHTML = 
						`<button type="button" class="btn btn-danger" disabled>${statusText}</button>`;
				}
			}else{
				let addValue = document.createTextNode(reimbursements[i][element]);
				if(addValue == null) addValue = '-';
				let cellElement = addCell.appendChild(addValue);
			}
			
			cellIndex++;
		}
	}
}
