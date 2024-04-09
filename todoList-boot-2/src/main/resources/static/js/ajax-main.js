const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");
const todoTitle = document.querySelector("#TodoTitle");
const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoNo");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.querySelector("#popupClose");
const deleteBtn = document.querySelector("#deleteBtn");
const updateView = document.querySelector("#updateView");
const changeComplete = document.querySelector("#changeComplete");

// 수정 레이어 버튼
const updateLayer = document.querySelector("#updateLayer");
const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");
const updateBtn = document.querySelector("#updateBtn");
const updateCancel = document.querySelector("#updateCancel");
// 전체 todo 개수 조회 및 출력하는 함수 정의
function getTotalCount() {

    // 비동기로 서버(db)에서 전체 todo 개수 조회하는
    // fetch() API 코드 작성
    // (fetch : 가지고 오다)

    fetch("/ajax/totalCount")
    .then( response => {
        console.log("response : ", response)

        return respose;
    })
    .then(result => {
        console.log("result : ", result);
        
    })
}
public int formgetTotalCount(){


}
reloadBtn.addEventListener("click", () => {
    getTotalCount();
    getTotalCount();
})

addBtj.addEventListener(
){
    rd
}

const selectTodo = (url) => {
    fetch(url)
    .then(resp => resp.text())
    .then(result => {
        const todo = JSON.parse(result);
		
        console.log(todo);
        popupTodoNo.innerText = todo.todoNo;
        popupTodoTitle.innerText = todo.todoTitle;
        popupComplete.innerText = todo.complete;
        popupRegDate.innerText = todo.regDate;
        popupTodoContent.innerText = todo.todoContent;
        popupLayer.classList.remove("popup-hidden");
        
        popupLayer.classList.remove("popup-hidden");
        //update Layer가 혹시라도 열려있으면 숨기기
        updateLayer.classList.add("popup-hidden");
    });
};
/// popup layer의 x버튼
popupClose.addEventListener("click", () =>{
    popupLayer.classList.add("popup-hidden")
});

// 삭제 버튼 클릭시
deleteBtn.addEventListener("click", () => {
    //취소 클릭시 아무것도 안함
    if(!confirm("정말 삭제 하시겠습니까?")){return;}
    // 삭제할 할일 번호 얻어오기
    const todoNo = popupTodoNo.innerText; // #popupTodoNo 내용 얻어오기
    //비등기 delete방식 요청
    fetch("/ajax/delete", {
        method : "delete",
        // 데이터 하나를 전달해도 application/json 작성
        headers : {"Content-type" : "application/json"},
        body : todoNo // todoNo값을 body에 담아서 전달
                    // -> @RequestBody로 꺼냄
    })
})


changeComplete.addEventListener("click", () => {
	//변경할 할일 번호, 완료 여부
	const todoNo = popupTodoNo.innerText;
	const complete = popupComplete.innerText === 'Y' ? 'N' : 'Y';
	
	// SQL 수행에 필요한 값을 객체로 묶음
	const obj = {"todoNo" : todoNo, "complete" : complete};
	
	// 비동기로 완료 여부 변경
	fetch("/ajax/changeComplete", {
		method: "PUT",
		headers: {"Content-Type" : "application"},
		body : JSON.stringify(obj)
	})
	.then(resp => resp.text())
	.then(result => {
		if(result > 0){
			// update된 DB데이터를 다시 조회해서 화면에 출력
			
			popupComplete.innerText = complete;
			
			// 서버 부하를 줄이기 위해 완료된 Todo 개수 +-1
			
			const count = Number(completeCount.innerText);
			if(complete === 'Y') completeCount.innerText = count + 1;
			else                 completeCount.innerText = count - 1;
			// 서버 부하 줄이기 가능 -> 코드 조금 복잡
			selectTodo();
		}else {
			alert("완료 여부 변경 실패!!");
		}
	});
});



// 상세 조회에서 수정 버튼 클릭 시
updateView.addEventListener("click", () => {
	
	//기존 팝업 레이어는 숨기고
	popupLayer.classList.add("popup-hidden");
	
	//수정 레이어 보이게
	updateLayer.classList.remove("popup-hidden");
	
	//수정 레이어 보일때
	// 팝업 레이어에 작성된 제목, 내용을 얻어와 세팅
	updateTitle.value = popupTodoTitle.innerText;
	
	updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n");
	//html 화면에서 줄바꿈이 <br>로 인식되고 있는데
	// textarea에서는 /n으로 바꿔줘야 줄바꿈으로 인식된다.
	
	
	// 수정 레이어 -> 수정 버튼에 data-todo-no 속성 추가
	updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText);

	
});


// 수정 레이어에서 취소 버튼(#updateCancel)이 클릭되었을 때
updateCancel.addEventListener("click", () => {
	//수정 레이어 숨기기
	updateLayer.classList.add("popup-hidden");
});


updateBtn.addEventListener("click", e => {
	// 서버로 전달해야되는 값을 객체로 묶어둠
	const obj = {
		"todoNo" : e.target.dataset.todoNo,
		"todoTitle":updateTitle.value,
		"todoContent" : updateContent.value
	};
	//비동기 요청
	fetch("/ajax/update", {
		method : "PUT",
		headers : {"Content-Type" : "application/json"},
		body : JSON.stringify(obj)
	})	
	.then(resp => resp.text())
	.then(result => {
		if(result > 0){
			alert("수정 성공!");
			
			//수정레이어 숨기기
			updateLayer.classList.add("popup-hidden");
			selectTodoList();
			
		}else{
			alert("수정 실패...");
		}
	})
});

































selectTodoList();
getTotalCount();
getTotalCount();