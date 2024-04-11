// ** 회원가입 유효성 검사 **

// 필수 입력 항목의 유효성 검사 여부를 체크하기 위한 객체
// - true == 해당 항목은 유효한 형식으로 작성됨
// - false == 해당 항목은 유효하지 않은 형식으로 작성됨
const checkObj = {
    "memberEmail" : false,
    "memberPw"     : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false,
    "authKey" : false
}

// ------------------

// 이메일 유효성 검사

// 1) 이메일 유효성 검사에 사용될 요소 얻어오기
const membedrEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일이 입력될때 마다 유효성 검사 수행
membedrEmail.addEventListener("input", e => {
    // 이메일 인증 후 이메일이 변경된 경우
    checkObj.authKey = false;
    document.querySelector("#authKeyMessage").innerText = "";
    // 나중에 처리
    // 작성된 이메일 값 얻어오기
    const inputEmail = e.target.value;
    
    // 3) 입력된 이메일이 없을 경우
    if(inputEmail.trim().length === 0) {
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";

        // 메시지에 색상을 추가하는 클래스 모두 제거
        emailMessage.classList.remove('confirm', 'error');

        // 이메일 유효성 검사 여부를 false로 변경
        checkObj.memberEmail = false;

        // 잘못입력한 띄어쓰기가 있을 경우 없앰
        membedrEmail.value = "";

        return;
    }

    // 4) 입력된 이메일이 있을 경우 정규식 검사
    // (알맞은 형태로 작성했는지 검사)
    const regExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    // 입력받은 이메일이 정규식과 일치하지 않는경우
    if (!regExp.test(inputEmail)){
        emailMessage.innerText ="알맞은 이메일 형식으로 작성해주세요"
        emailMessage.classList.add('error');
        emailMessage.classList.remove('confirm');
        checkObj.memberEmail = false;
        return;
    }

    // 5) 유효한 이메일 형식은 경우 중복검사 수행
    fetch("/member/checkEmail?memberEmail=" + inputEmail)
    .then(resp => resp.text())
    .then(count => {
        // count : 1이면 중복, 0이면 중복 아님

        if(count == 1) {
            emailMessage.innerText = "이미 사용중인 이메일 입니다"
            emailMessage.classList.add('error');
            emailMessage.classList.remove('confirm');
            checkObj.memberEmail= false;
            return;
        }
        // 중복 X 경우
        emailMessage.innerText = "사용가능한 이메일 입니다"
        emailMessage.classList.add('confirm');
        emailMessage.classList.remove('error');
        checkObj.memberEmail = true;
    })
    .catch(error => {
        // fetch() 수행 중 
        console.log(error); // 발생한 예외 출력
    })


});

//-----------------------------------------------------------------------


// 이메일 인증

// 인증 번호 받기 버튼
const sendAuthKeyBtn = document.querySelector("#sendAuthKeyBtn");

// 인증 번호 입력 input
const authKey = document.querySelector("#authKey");

// 인증번호 입력 후 확인 버튼
const checkAuthKeyBtn = document.querySelector("#checkAuthKeyBtn");

// 인증번호 관련 메시지 출력 span
const authKeyMessage = document.querySelector("#authKeyMessage");

let authTimer; // 타이머 역할을 할 setInterval을 저장할 변수

const initMin =4; //타이머 초기값 (분)
const initSec = 59; // 타이머 초기값 (초)
const initTime = "05:00";

// 실제 줄어드는 시간을 저장할 변수
let min = initMin;
let sec = initSec;

sendAuthKeyBtn.addEventListener("click", () => {
    checkObj.authKey = false;
    authKeyMessage.innerText = "";
    if(!checkObj.memberEmail){
        alert("유효한 이메일 작성 후 클릭해주세요");
        return;
    } else{

    min = initMin;
    sec = initSec;
    clearInterval(authTimer);


    // 비동기로 서버에서 메일 보내기

    fetch("/email/signup", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : memberEmail.value
    })



    //메일은 비동기로 서버에서 보내라고 하고
    //화면에서는 타이머 시작하기








    authKeyMessage.innerText = initTime;
    authKeyMessage.classList.remove('confirm', 'error');
    alert("인증번호가 발송되었습니다.");
    
    authTimer = setInterval(() => {
        authKeyMessage.innerText = `${addZero(min)}:${addZero(sec)}`;
        if(min == 0 && sec == 0) {
            checkObj.authKey = false;
            clearInterval(authTimer);
            authKeyMessage.classList.add('error');
            authKeyMessage.classList.remove('confirm');
            return;
        }

        if(sec == 0) {
            sec = 60;
            min--;
        }
        sec--; // 1초 감소

    } , 1000); // 1초 지연시간
}
});

//전달 받은 숫자가 10 미만인 경우 (한자리) 앞에 0 붙여서 반환
function addZero(number){
    if(number<10) return "0" +number;
    else return number;
}



















































