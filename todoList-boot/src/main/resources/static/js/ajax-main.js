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


selectTodoList();
getTotalCount();
getTotalCount();