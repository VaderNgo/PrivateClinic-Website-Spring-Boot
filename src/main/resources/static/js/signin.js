const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");

sign_up_btn.addEventListener("click", () => {
    container.classList.add("sign-up-mode");
});

container.addEventListener('transitionend', function(event) {

    if (event.propertyName === 'transform') {

        window.location.href = "/signup";
    }
});