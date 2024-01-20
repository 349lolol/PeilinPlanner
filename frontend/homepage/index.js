// LOAD PROJECTS IN
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

const login = async (data) => {
    await fetch("http://127.0.0.1:5500/server/HttpHandler.java", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: data.username,
            password: data.password
        })
    })
        .then(res => {
            res.json();
        })
        .then(res => {
            if (res.valid) {
                window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
            } else {
                invalidInfo();
            }
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
        form.reset();
        })
    
}

const invalidInfo = async () => {
    form.reset();
    document.querySelector("#incorrect").setAttribute("display", "flex");

}

form.addEventListener("submit", (e) => {
    login(data)
})