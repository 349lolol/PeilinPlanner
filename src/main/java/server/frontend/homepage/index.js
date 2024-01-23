// LOADING IN PROJECTS

const projectsList = [];

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
            return res.json();
        })
        .then(res => {
            for (let projectName in res.projectName) {
                const projects = document.querySelector(".projects");

                const project = document.createElement("button");
                project.style.type = "submit";
                project.classList.add("project");
                project.id = res.projectName[projectName];

                const title = document.createElement("p");
                title.append(res.projectName[projectName]);
                project.append(title);

                const icon = document.createElement("i");
                icon.classList.add("ri-article-line");
                icon.id = "projectIcon";

                project.append(icon);

                projects.append(project);

                projectsList.push(res.projectName[projectName])
                window.localStorage.setItem("projectsList", JSON.stringify(projectsList));

                addProjectListeners()
            }
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            console.log(err)
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
    collaborateModal.close();
})

// CREATING PROJECTS

const create = async (username, projectName) => {
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
            if (res.valid) {
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

                projectsList.push(projectName)
                
                window.localStorage.setItem("projectName", projectName);
                window.localStorage.setItem("projectsList", projectsList);
                window.location.href = "http://localhost:5069/frontend/umleditor/umleditor.html"

                addProjectListeners()
            } else {
                createModal.close();
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
    create(localStorage.getItem("username"), document.querySelector("#createForm input").value);
    createModal.close();
    createModal.style.display = "none";
})

// COLLABORATION

const collaborate = async (username, projectName) => {
    await fetch("/frontend/collaborate", {
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
           if (res.valid) {
            window.localStorage.setItem("projectName", projectName)
            window.location.href = "http://localhost:5069/frontend/umleditor/umleditor.html"
           } else {
            collaborateModal.close();
            collaborateModal.style.display = "none";
            
           }
        })
        .catch(err => {
            console.log("ERROR RETRIEVING USER DATA")
            window.location.href = "http://localhost:5069/frontend/homepage/homepage.html"
        })
}

const collaborateForm = document.querySelector("#collaborateForm");

collaborateForm.addEventListener("submit", (e) => {
    e.preventDefault();
    collaborate(localStorage.getItem("username"), document.querySelector("#collaborateForm input").value);
    collaborateModal.close();
})

const addProjectListeners = () => {
    const projects = document.querySelectorAll(".project")
    projects.forEach(project => project.addEventListener("click", (e) => {
                    
        window.localStorage.setItem("projectName", e.target.parentNode.id);
        window.location.href = "http://localhost:5069/frontend/umleditor/umleditor.html"
    }))
}