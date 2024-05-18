const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

const body = document.body;

// window.addEventListener('DOMContentLoaded',
//     ()=>{
//         container.classList.add("sign-up-mode");
//     },
//     false);


sign_in_btn.addEventListener("click", () => {
    container.classList.remove("sign-up-mode");

});

container.addEventListener('transitionend', function(event) {

    if (event.propertyName === 'transform') {

        window.location.href = "/signin";
    }
});