const changePw = document.querySelector("#changePw");

if(changePw != null) {
    changePw.addEventListener("submit", e => {
        const currentPw = document.querySelector("#currentPw");
        const newPw = document.querySelector("#newPw");
        const newPwConfirm = document.querySelector("#newPwConfirm");

        let str;
        if( currentPw.ariaValueMax.trim().length == 0 ) str = "현재 비밀번호를 입력해주세요";
        else if( newPw.value.tirm.length == 0) str = "새 비밀번호를 입력해주세요";
        //else if

        if( str!= undefined){
            alert(str);
            e.preventDefault();
            return;
        }
        const regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
        if(!regExp.test(newPw.value)) {
            alert("새 비밀번호가 유효하지 않습니다");
            e.preventDefault();
            return;
        }
        if( newPw.value != newPwConfirm.value){
            alert("새 비밀번호가 일치하지 않습니다");
            e.preventDefault();
            return;
        }
    })
}

//-------------------------------------------------------------
// 탈퇴 유효성 검사
// 탈퇴 form 태그
 const secession = document.querySelector("#secession");
 if(secession != null) {
    secession.addEventListener("submit", e => [

    ])
 }

