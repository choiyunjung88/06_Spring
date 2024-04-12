/* 다음 주소 API 활용*/ 

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

// 주소 검색 버튼 클릭시
document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);









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
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일이 입력될때 마다 유효성 검사 수행
memberEmail.addEventListener("input", e => {
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
        memberEmail.value = "";

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
    .then(resp => resp.text())
    .then(result => {
        if(result == 1) {
            console.log("인증 번호 발송 성공");
        } else {
            console.log("인증번호 발송 실패!!")
        }
    });


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

//--------------------------------------
// 인증하기 버튼 클릭시
// 입력한 인증번호를 비동기로 서버에 전달
// -> 입력된 인증번호와 발급된 인증번호가 같은지 비교
// 같으면 1 아니면 0'
// 단 타이머가 00분00초가 아닐 경우에만 수행
checkAuthKeyBtn.addEventListener("click", () => {
    if(min === 0 && sec === 0 ) {
        alert("인증번호 입력 제한 시간을 초과했습니다.");
        return;
    }
    if(authKey.value.length < 6) {
        alert("인증번호를 정확히 입력해 주세요");
        return;
    }
    // 입력받은 이메일
    const obj = {
        "email" : memberEmail.value,
        "authKey" : authKey.value
    };
    fetch("/email/checkAuthKey", {
        
    })
}
);



const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("memberPWConfirm");
const pwMessage = document.querySelector("#pwMessage");

const checkPw = () => {
    if(memberPw.value === memberPwConfirm.value){
        pwMessage.innerText = "Password Confirmed";
        pwMessage.classList.remove('error');
        pwMessage.classList.add('confirm');
        checkObj.memberPwConfirm = true;
        return;
    }

    pwMessage.innerText = "password not a Match"
    pwMessage.classList.remove('confirm');
    pwMessage.classList.add('error');
    checkObj.memberPwConfirm = false;

};


memberPw.addEventListener("input", e => {
    const inputPw = e.target.value;
    if(inputPw.trim().length === 0){
        pwMessage.innerText = "영어,숫자,특수문자(!,@,#,-,_) 6~20글자 사이로 입력해주세요.";
        pwMessage.classList.remove("confirm", "error");
        checkObj.memberPw = false;
        memberPw.value = "";
        return;
    }

    // 4) 입력 받은 비밀번호 정규식 검사
    const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
    if(!regExp.test(inputPw)){
        pwMessage.innerText = "비밀번호가 유효하지 않습니다";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
        return;
    }

    pwMessage.innerText = "Password Confirmed";
    pwMessage.classList.remove('error');
    pwMessage.classList.add('confirm');
    checkObj.memberPw = true;

    if(memberPwConfirm.value.length > 0){
        checkPw();
    }

});

memberPwConfirm.addEventListener("input", e => {
    if(checkObj.memberPw){
        checkPw();
        return;
    }
    checkObj.memberPwConfirm = false;
});


// 닉네임 입력할 때마다
// 1)입력안한 경우
// 2)정규식 검사
// 3)중복검사
const nickMessage = document.querySelector("#nickMessage")
const memberNickname = document.querySelector("#memberNickname");

memberNickname.addEventListener("input", e => {
    checkObj.memberNickname = false;
    const inputNickname = e.target.value;
    if (inputNickname.trim().length === 0) {
        checkObj.memberNickname = false;
        nickMessage.innerText = "only letters &numbers 2~10";
        nickMessage.classList.remove('error', 'confirm');
        memberNickname.value = "";
        return;
    }

    const regExp = /^[가-힣\w\d]{2,10}$/;
    if(!regExp.test(inputNickname)) {
        nickMessage.innerText = " incorrect nickname form";
        nickMessage.classList.add('error');
        nickMessage.classList.remove('confirm');
        checkObj.memberNickname = false;
        return;
    }
    fetch("/member/checkNickname?memberNickname=" + inputNickname).then(resp.text()).then(result => {
        if(result == 1) {
            checkObj.memberNickname = false;
            nickMessage.innerText = "Nickname already in use";
            nickMessage.classList.add('error');
            nickMessage.classList.remove('confirm');
            return;
        }
        nickMessage.innerText = "Nickname confirmed";
        nickMessage.classList.add("confirm");
        nickMessage.classList.remove('error');
        checkObj.memberNickname = true;
    }).catch(err => console.log(err));
});


//전화 번호 유효성 검사
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

memberTel.addEventListener("input", e => {
    const inputTel = e.target.value;
    if(inputTel.trim().length === 0) {
        telMessage.innerText = "전화번호를 입력해주세요.(-제외)";
        telMessage.classList.remove("confirm", "error");
        checkObj.memberTel = false;
        memberTel.value = "";
        return;
    }
    const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;

    if (!regExp.test(inputTel)){
        telMessage.innerText = "유효하지 않은 전화번호 형식입니다.";
        telMessage.classList.add("error");
        telMessage.classList.remove("confirm");
        checkObj.memberTel = false;
        return;
    }

    telMessage.innerText = "전화번호가 확인되었습니다.";
    telMessage.classList.add(`confirm`);
    telMessage.classList.remove('error');
    checkObj.memberTel = true;
    console.log(checkObj);
})

const signupForm = document.querySelector("#signUpForm");

signupForm.addEventListener("submit", e => {
    // checkObj의 저장된 값 중
    // 하나라도 false가 있으면 제출 X

    // for ~ in (객체 전용 향상된 for 문)
    for(let key in checkObj) {
        if(!checkObj[key]) { // false인 경우 (유효하지 않음)
            let str; // 출력할 메시지를 저장할 변수
            switch(key) {
                case "memberEmail" :
                    str = "이메일이 유효하지 않습니다"; break;

                case "authKey" :
                    str = "이메일이 인증되지 않았습니다"; break;
                case "memberPw":
                    str = "비밀번호가 유효하지 않습니다"; break;
                case "memberPwConfirm" :
                    str = "비밀번호가 일치하지 않습니다"; break;
                case "memberNickname":
                    str = "닉네임이 유효하지 않습니다"; break;
                case "memberTel" :
                    str = "전화번호가 유효하지 않습니다"; break;
            }
            alert(str);
            document.getElementById(key).focus(); //초점 이동

            e.preventDefault(); // form 태그 기본 이벤트 (제출) 막기
            return;
        }
    }
})




























