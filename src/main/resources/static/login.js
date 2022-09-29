const app = Vue.

    createApp({
        data() {
            return {
                email1: "",
                pwd1: "",
                // email2: "",
                // pwd2: "",
                firstName: "",
                lastName: "",
            }
        },

        created() {




        },
        methods: {

            logIn() {
                axios.post("/api/login", `email=${this.email1}&pwd=${this.pwd1}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => location.href = "/web/accounts.html")
                    .catch(error =>
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data,
                        }),
                    )
            },
            register() {
                axios.post('/api/client', `first=${this.firstName}&lastName=${this.lastName}&email=${this.email1}&password=${this.pwd1}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => this.logIn())
                    .catch(error =>
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data,
                        }),
                    )
                    
            },
            loginButton(){
                container.classList.remove("right-panel-active");
            },
            registerButton(){
                container.classList.add("right-panel-active");
            
            }


        },
    }).mount('#app');