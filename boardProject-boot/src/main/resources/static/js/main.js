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
