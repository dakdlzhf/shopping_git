function addCart(param){
	return fetch('/cart/create',{
		method:'post',
		body :JSON.stringify(param),
		headers:{'Content-Type':"application/json; charset=utf-8"}
	})
	.then(response => response.text()) //문자열로 받는다는거다
	.catch(console.log)
}

