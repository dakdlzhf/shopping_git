$(function() {
	let mname;
	let email;
	$("#idFindButton").click(function() {
		mname = document.getElementById("mname").value;
		email = document.getElementById("email").value;
		resultId =document.getElementById("resultId");
		
		if (mname != "" || email != "") {
			alert(mname + "," + email);
			requestFetch(mname, email)
			.then(response=>{
				resultId.innerHTML ="고객님의 ID 는 : "+ response.id+" 입니다";
				$('#mname').val('');
				$('#email').val('');
			})
			.catch(error =>{
				alert("error :" +error);
			})
			

		} else {
			return;
		}
	});
	$('#pwFindButton').click(function(){
		mname = document.getElementById("mname").value;
		id = document.getElementById("id").value;
		resultPw =document.getElementById("resultPw");
		
		if (mname != "" || id != "") {
			alert(mname + "," + id);
			requestFetch2(mname, id)
			.then(response=>{
				resultPw.innerHTML ="고객님의 Password 는 : "+ response.passwd+" 입니다";
				$('#mname').val('');
				$('#id').val('');
			})
			.catch(error =>{
				alert("error :" +error);
			})
			

		} else {
			return;
		}
		
	})

});

async function requestFetch2(mname, id) {
	return await fetch(`/async/pwFind/?mname=${mname}&id=${id}`, { method: 'get' })
		.then(response => response.json())
		.catch(console.log)
}

async function requestFetch(mname, email) {
	return await fetch(`/async/idFind/?mname=${mname}&email=${email}`, { method: 'get' })
		.then(response => response.json())
		.catch(console.log)
}