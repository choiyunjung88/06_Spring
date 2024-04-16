//console.log("main.loaded");

// 쿠키에서 key가 일치하는 value 얻어오기 함수
// 쿠키는 k=v; 
const getCookie =() => {
    const cookie = document.cookie;

    const cookieList = Cookies.split("; ").map
    ( el => el.split("="));
    const obj = {};
    for(let i=0; i<cookieList.length; i++){
        const k = "cookieList"
    }
}
getCookie();

const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");
if(loginEmail != null) {
    const saveId = getCookie("saveId");
    if(saveId != undefined){
        loginEmail.value = saveId;
        document.querySelector("input[name='saveId']").ariaChecked = true;

    }
}
const loginPw = document.querySelector("#loginForm input[name='memberPw']");
if(loginForm != null) {
    loginForm.addEventListener("submit", e => {
        if(loginEmail.value.trim().length === 0) {
            alert("이메일을 작성해주세요");
            e.preventDefault();
            loginEmail.focus();
            return;
        }
        if(loginPw.value.trim().length === 0){
            alert("비밀번호를 작성해주세요");
            e.preventDefault();
            loginPw.focus();
            return;
        }
    })
}


//--------------------------------------------------------

/* 빠른 로그인 */

const quickLoginBtns = document.querySelectorAll(".quick-login");

quickLoginBtns.forEach((item, index)=> {
    // item : 현재 반복 시 꺼내온 객체
    // index : 현재 반복중인 인덱스

    // quickLoginBtns 요소인 button 태그 하나씩 꺼내서 이벤트 리스너 추가
    item.addEventListener("click", () => {
        const email = item.innerText; //  버튼에 작성된 이메일 얻어오기
        location.href = "/member/quickLogin?memberEmail=" + email;
    });
});

//------------------------------------------------------
// 회원 목록 조회
// 조회 버튼
const selectMemberList = document.querySelector("#selectMemberList");
// tbody
const memberList = document.querySelector("#memberList");

// td 요소를 만들고 text 추가 후 반환
const createTd = (text) =>{
    const td = document.createElement("td");
}
// 조회 버튼 클릭시
selectMemberList.addEventListener("click", () => {

    // 1) 비동기로 회원 목록 조회
    // 포함될 회원 정보 : 회원 번호, 이메일, 닉네임, 탈퇴여부
    fetch("/member/selectMemberList")
    .then(response => response.json()) //JSON.parse(response)
    .then(list => {
        // list 바로 이용 -> js 객체 배열
        console.log(list);
        // 이전 내용 삭제
        memberList.innerHTML = "";
        // tbody에 들어갈 요소를 만들고 값 세팅 후 추가
        list.forEach((member, index) => {
            // member : 현재 반복 접근 중인 요소
            // index : 현재 접근 중인 인덱스

            // tr 만들어서 그안에 td 만들고, append 후
            // tr을 tbody에 append
            const keyList = ['memberNo', 'memberEmail', 'memberNickname', 'memberDelfl'];
            const tr = document.createElement("tr");
            keyList.forEach( key => tr.append(createTd(member[key])   ));
            memberList.append(tr);
        });
    })
});

// ---------------------------------------------------------------------
// 특정 회원 비밀번호 초기화
const resetMemberNo = document.querySelector("#resetMemberNo");
const resetPw = document.querySelector("#restPw");

resetPw.addEventListener("click", () => {
    const inputNo = resetMemberNo.value;
    if(inputNo.trim().length == 0){
        alert ("회원 번호 입력해주세요");
        return;
    }
    fetch("/member/restPw", {
        method : "PUT",
        headers : {"Content-Type" : "application/json"},
        body : inputNo
    })
    .then(resp => resp. text())
    .then(result => {
        if(result > 0){
             alert("초기화 성공");
        }else{
            alert("해당 회원이 존재하지 않습니다");
        }
    });
});