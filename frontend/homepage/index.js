// LOAD PROJECTS IN
const modal = document.querySelector("#dialog");


document.querySelector("#create > button").addEventListener("click", (e) => {
    e.preventDefault();
    modal.showModal();
})