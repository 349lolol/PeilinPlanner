const form = document.querySelector("form");

const login = async (data) => {
    await fetch("http://localhost:5069/frontend/verify", {
        method: "POST",
        mode: 'cors',
        headers: {
            // "Accept": "application/json",
            "Content-Type": "application/json",
            "Access-Control-Origin": "*",
        },
        // body: JSON.stringify({
        //     username: data.username,
        //     password: data.password
        // })
        body: JSON.stringify({cheese: "Parmesan"})
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
    e.preventDefault();
    let data = {
        username: document.querySelector("#username").value,
        password: document.querySelector("#password").value
    }

    login(data)
})