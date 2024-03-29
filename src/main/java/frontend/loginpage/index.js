const form = document.querySelector("form");

const login = async (data) => {
    await fetch("/frontend/verify", {
        method: "POST",
        mode: 'cors',
        headers: {
            "Content-Type": "application/json",
            "Access-Control-Origin": "*",
        },
        body: JSON.stringify({
            username: data.username,
            password: data.password
        })
    })
        .then(res => {
            return res.json();
        })
        .then(res => {
            if (res.valid) {
                localStorage.setItem("username", data.username)
                window.location.href = "/frontend/homepage/homepage.html"
            } else {
                invalidInfo();
            }
        })
        .catch(err => {
        })
    
}

const invalidInfo = () => {
    form.reset();
    document.querySelector("#incorrect").style.display = "flex";
}

form.addEventListener("submit", (e) => {
    e.preventDefault();
    let data = {
        username: document.querySelector("#username").value,
        password: document.querySelector("#password").value
    }

    login(data)
})