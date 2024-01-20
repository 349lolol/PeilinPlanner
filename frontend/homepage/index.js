// LOADING IN PROJECTS

const load = async (username) => {
    await fetch("/frontend/loadProjects", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
        })
    })
        .then(res => {
            res.json();
        })
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            window.location.href = "http://localhost:5069/frontend/loginpage/loginpage.html"
        })
}

window.addEventListener("DOMContentLoaded", (e) => {
    load(localStorage.getItem("username"))
})

// MODALS
const createModal = document.querySelector("#createModal");

document.querySelector("#create > button").addEventListener("click", (e) => {
    e.preventDefault();
    createModal.showModal();
    createModal.style.display = "flex"
})

const collaborateModal = document.querySelector("#collaborateModal");

document.querySelector("#collaborate > button").addEventListener("click", (e) => {
    e.preventDefault();
    collaborateModal.showModal();
    collaborateModal.style.display = "flex"
})

const createForm = document.querySelector("#createForm");

createForm.addEventListener("submit", (e) => {
    e.preventDefault();

})

const create = async (projectName) => {
    await fetch("/frontend/createProject", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            projectName: projectName,
        })
    })
        .then(res => {
            res.json();
        })
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            // window.location.href = "http://localhost:5069/frontend/loginpage/loginpage.html"
        })
}