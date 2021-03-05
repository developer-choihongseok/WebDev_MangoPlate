const frm = document.querySelector('#frm')

async function clkGetLatLng () {
	const addr = frm.addrElem.value	// 지역변수 : 호출할 때만 사용하기 때문에 const로 하면 된다.
	
	if(!addr) {	// false가 되어야 true가 된다. 즉, addr = null이거나 addr = undefined, addr = 빈칸이면 false를 가져온다.
		alert('주소를 입력해 주세요')
		return	// 함수 종료되면 addr 값 소멸.
	}
	
	/*getAddrLatLng(addr).then(function(result){
		frm.lat.value = result.lat
		frm.lng.value = result.lng
	})*/
	
	const result = await getAddrLatLng(addr)	// 오래 걸리는 작업. await를 했다는 것은 100% Promise.
	frm.lat.value = result.lat	// .을 썻다는 것은 객체를 의미한다.
	frm.lng.value = result.lng	// 두번 접근 했다는 것은 자식에 접근 한 것이다.
}

function getAddrLatLng (addr) {
	return new Promise((resolve, reject) => {
		fetch(`/api/getAddrLatLng?addr=${addr}`)	// APIController의 19번째 줄에 @RequestParam 이름과 같아야 한다.
		.then(res => res.json())
		.then(myJson => {
			if(myJson) {
				resolve(myJson)	// myJson이 APIService의 return result이다. 즉, "객체가 넘어왔다"는 말.
			} else {
				reject()	// 객체가 넘어오지 X.
			}
		})
	})
}