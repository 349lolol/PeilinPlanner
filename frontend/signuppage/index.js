const form = document.querySelector("form");

const signup = async (data) => {
    
    await axios.post("SIGNUPPAGE", data)
    .then(res => {
        window.location.href = "LOGINPAGEURL"
        // server will add new user
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

    if (!Object.hasOwn(userBaseData.users, username)) {
        const data = {
            username: `${username}`,
            password: `${password}`
        }

        signup(data)
    }
    // invalid username
    invalidInfo();

    
})