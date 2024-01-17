const form = document.querySelector("form");

const login = async (data) => {
    
    await axios.post("LOGINPAGEURL", data)
    .then(res => {
        window.location.href = "HOMEPAGEURL"

        // use res data to load user projects and stuff
        const projects = JSON.parse(res.json());

        // use projects to make HTML stuff
    }).catch(err => {
        console.log("ERROR RETRIEVING USER DATA")
        form.reset();
    })
}

const invalidInfo = async () => {
    form.reset();
    document.querySelector("#incorrect").setAttribute("display", "flex");

}

form.addEventListener("submit", (e) => {
    const userBase = axios.get("LOGINPAGEURL");
    const userBaseData = userBase.data;

    e.preventDefault()
    const username = document.querySelector("#username").value;
    const password = document.querySelector("#password").value;

    for (let userData in userBaseData) {
        if ((username === userData) && (password === userData.user.password)) {
            const data = {
                username: `${username}`,
                password: `${password}`
            };

            login(data);
        }
    }
    // invalid username or password
    invalidInfo();

    
})