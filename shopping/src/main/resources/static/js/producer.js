console.log("*****Reply Module........");
function getList(param) {
	let contentsno = param.contentsno;
	let sno = param.sno;
	let eno = param.eno;
	return fetch(`/contents/reply/list/${contentsno}/${sno}/${eno}`, { method: 'get' })
		.then(response => response.json())
		.catch(console.log)
}
//param 에는 nPage=&nowPage=&contentsno=&colx=&wordx= 
function getPage(param) {
	let url = `/contents/reply/page?${param}`;
	return fetch(url, { method: 'get' })
		.then(response => response.text())
		.catch(console.log)

}
function add(reply) {
	return fetch('/contents/reply/create', {
		method: 'post',
		body: JSON.stringify(reply), //json 객체를 문자열로 변경해서 보내줌.
		headers: { 'Content-Type': "application/json; charset=utf-8" }
	})
		.then(response => response.json())
		.catch(console.log);
}

function get(rnum) {
	return fetch(`/reply/${rnum}`, { method: 'get' })
		.then(response => response.json())
		.catch(console.log);
}

function sessionCheck(data){
	return fetch('/sessionCheck',{
		method :'post',
		body: JSON.stringify(data),
		headers: { 'Content-Type': "application/json; charset=utf-8" }
		})
		.then(response=>response.json())
		.catch(console.log);
}

function update(reply) {
	return fetch(`/reply/`, {
		method: 'put',
		body: JSON.stringify(reply),
		headers: { 'Content-Type': "application/json; charset=utf-8" }
	})
		.then(response => response.text())
		.catch(console.log);
}
function remove(rnum) {
	return fetch(`/reply/${rnum}`, { method: 'delete' })
		.then(response => response.text())
		.catch(console.log);
}