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
const closeCreate = document.querySelector("#createModal .close")

document.querySelector("#create > button").addEventListener("click", (e) => {
    e.preventDefault();
    createModal.showModal();
    createModal.style.display = "flex"
})

closeCreate.addEventListener("click", (e) => {
    console.log(e)
    createModal.close();
})

const collaborateModal = document.querySelector("#collaborateModal");
const closeCollaborate = document.querySelector("#collaborateModal .close")

document.querySelector("#collaborate > button").addEventListener("click", (e) => {
    e.preventDefault();
    collaborateModal.showModal();
    collaborateModal.style.display = "flex"
})

closeCollaborate.addEventListener("click", (e) => {
    console.log(e)
    collaborateModal.close();
})

// CREATING PROJECTS

const create = async (projectName) => {
    await fetch("/frontend/createProject", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            projectName: projectName
        })
    })
        .then(res => {
            return res.json();
        })
        .then(res => {
            if (res !== undefined) {
            //     <button type="submit" class="project" id="project1">
            //     <p>[Project Name]</p>

            //     <i class="ri-article-line" id="projectIcon"></i>
            //     </button>
                const projects = document.querySelector(".projects");

                const project = document.createElement("button");
                project.style.type = "submit";
                project.classList.add("project");
                project.id = projectName;

                const title = document.createElement("p");
                title.append(projectName);
                project.append(title);

                const icon = document.createElement("i");
                icon.classList.add("ri-article-line");
                icon.id = "projectIcon";

                project.append(icon);

                projects.append(project);
            }
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
        })
}

const createForm = document.querySelector("#createForm");

createForm.addEventListener("submit", (e) => {
    e.preventDefault();
    create(document.querySelector("#createForm input").value);

})

// COLLABORATION

const collaborate = async (username, projectName) => {
    await fetch("/frontend/createProject", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            projectName: projectName
        })
    })
        .then(res => {
            return res.json();
        })
        .then(res => {
           console.log(res)
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            // window.location.href = "http://localhost:5069/frontend/loginpage/loginpage.html"
        })
}