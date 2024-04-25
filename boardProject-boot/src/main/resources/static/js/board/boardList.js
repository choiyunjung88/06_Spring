/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");
// id 라서 #
// 글쓰기 버튼이 존재할 때 (로그인 상태인 경우)
if(insertBtn != null) {
    insertBtn.addEventListener('click', () => {
        // get 방식 요청
        // /editBoard/1(boardCode -> html에서 정의해놔야함)/insert
        //  location.href = "요청주소";
        location.href = `/editBoard/${boardCode}/insert`;
    })
}