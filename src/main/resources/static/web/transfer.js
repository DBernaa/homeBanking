
const app = Vue.

    createApp({
        data() {
            return {
                clients: [],
                accounts: [],
                tranferType: [],
                otherAccounts: "",
                amount: "",
                description: "",
                originAccount: [],
                destinyAccount: []
            }
        },

        created() {
            axios.get("/api/client/current")
                .then(response => {
                    this.clients = response.data;
                    this.accounts = this.clients.accounts
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        methods: {
            logOut() {
                axios.post('/api/logout')
                    .then(location.href = "../index.html")
            },

            toggle() {
                let sidebar = document.querySelector(".sidebar");
                sidebar.classList.toggle("active");
            },

            search() {
                let sidebar = document.querySelector(".sidebar");
                sidebar.classList.toogle("active")
            },

            newTranfer() {
                axios.post('/api/client/current/transactions', `amount=${this.amount}&description=${this.description}&originAccount=${this.originAccount}&destinyAccount=${this.destinyAccount}`,
                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(
                        Swal.fire(
                            'Good job!',
                            'You clicked the button!',
                            'success',
                            setTimeout(() => { location.href = "/web/transfer.html" }, 1500)
                        )
                    )
                    .catch(error =>
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: error.response.data,
                        }),
                        setTimeout(() => { location.href = "/web/transfer.html" }, 1000)
                    )
            }
        },


    }).mount('#app');


