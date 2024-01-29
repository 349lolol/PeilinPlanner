const form = document.querySelector("form");

const login = async (data) => {
    await fetch("http://localhost:5069/frontend/newUser", {
        method: "POST",
        mode: 'cors',
        headers: {
            // "Accept": "application/json",
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
                window.location.href = "http://localhost:5069/frontend/loginpage/loginpage.html"
            } else {
                invalidInfo();
            }
        })
        .catch(err => {
        form.reset();
        })
    
}

const invalidInfo = async () => {
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