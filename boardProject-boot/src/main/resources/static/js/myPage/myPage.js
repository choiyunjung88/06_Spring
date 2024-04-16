// 회원 정보 수정 페이지
const updateInfo = document.querySelector("#updateInfo");

if(updateInfo != null) {
    updateInfo.addEventListener("submit", e => {
        const memberNickname = document.querySelector("#memberNickname");
        const memberTel = document.querySelector("#memberTel");
        const memberAddress = document.querySelectorAll("[name='memberAddress']");

        if(memberNickname.value.trim().length === 0) {
            alert("닉네임을 입력해 주세요");
            e.preventDefault();
            return;
        }
        const regExp = /^[가-힣\w\d]{2,10}$/;
        if(!regExp.test(memberNickname.value)) {
            alert("닉네임이 유효하지 않습니다.");
            e.preventDefault();
            return;
        }

        // 닉네임 중복 검사는 개별적으로하기

        if (memberTel.value.trim().length === 0) {
            alert("전화번호를 입력해주세요");
            e.preventDefault;
            return;
        }
        regExp = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
        if(!regExp.test(memberTel.value)) {
            alert("전화번호가 유효하지 않습니다.");
            e.preventDefault();
            return;
        }

        const addr0 = memberAddress[0].value.trim().length == 0;
        const addr1 = memberAddress[1].value.trim().length == 0;
        const addr2 = memberAddress[2].value.trim().length == 0;

        const result1 = addr0 && addr1 && addr2;
        const result2 = !(addr0 || addr1 || addr2);

        if (!(result1 || result2)) {
            alert("주소를 모두 작성 또는 미작성 해주세요");
            e.preventDefault();
        }
    })
}
















const changePw = document.querySelector("#changePw");

if(changePw != null) {
    changePw.addEventListener("submit", e => {
        const currentPw = document.querySelector("#currentPw");
        const newPw = document.querySelector("#newPw");
        const newPwConfirm = document.querySelector("#newPwConfirm");

        let str;
        if( currentPw.ariaValueMax.trim().length == 0 ) str = "현재 비밀번호를 입력해주세요";
        else if( newPw.value.tirm.length == 0) str = "새 비밀번호를 입력해주세요";
        else if(newPwConfirm.value.trim().length == 0) str = "새 비밀번호 확인을 입력해주세요";

        if( str!= undefined){
            alert(str);
            e.preventDefault();
            return;
        }
        const regExp = /^[a-zA-Z0-9!@#_-]{6,20}$/;
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
};

//-------------------------------------------------------------
// 탈퇴 유효성 검사
// 탈퇴 form 태그
 const secession = document.querySelector("#secession");
 if(secession != null) {
    secession.addEventListener("submit", e => {
        const memberPw = document.querySelector("#memberPw");
        const agree = document.querySelector("#agree");

        if(memberPw.value.trim().length == 0){
            alert("비밀번호를 입력해주세요");
            e.preventDefault();
            return;
        }
    
        if(!agree.checked){
            alert("약관에 동의해주세요");
            e.preventDefault();
            return;
        }
        if(confirm("정말 탈퇴하시겠습니까?")){
            alert("취소되었습니다");
            e.preventDefault();
            return;
        }
    });
}
