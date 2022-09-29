const app = Vue.

createApp({
    data() {
        return {
            client:[],
            accounts:[],
            payments:[],
            loans:[],
            payment:"",
            loanId:"",
            loanPays:"",
            amount:0,
            accountDestiny:"",
        }
    },

    created(){
        axios.get("/api/client/current")
            .then(response => {
                this.allData(response)
            })
            .catch((error) =>{
                console.log(error)
            });
        axios.get("/api/loans")
            .then(response => {
                this.getLoans(response)
            })
            .catch((error) =>{
                console.log(error)
            });
            
    },
    methods: {
        allData(response){
                this.client = response.data 
                this.accounts = this.client.accounts
        },
        getLoans(response){
                this.loans = response.data
        },
        newDate(creationDate){ 
            return  new Date(creationDate).toLocaleDateString('es-AR', {month: '2-digit', year: '2-digit'})
        },
        logOut(){
            axios.post('/api/logout')
            .then(location.href = "./index.html")
        },
        loanPetition(){
            axios.post('/api/loans',
                {
                    "id":`${this.loanId}`,
                    "amount":`${this.amount}`,
                    "payments":`${this.loanPays}`,
                    "destinyAccount":`${this.accountDestiny}`
                })
                .then(() =>
                    Swal.fire({
                        position: 'top-end',
                        icon: 'success',
                        title: 'succes',
                        showConfirmButton: false,
                        timer:1500
                    }),
                )
                .catch((error) => {
                    Swal.fire({
                        icon: 'error',
                        showConfirmButton: true,
                        text:error.response.data
                    })
                });
        }
    },
}).mount('#app');