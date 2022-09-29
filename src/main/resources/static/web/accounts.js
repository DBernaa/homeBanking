let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get("id");
const app = Vue.

createApp({
    data() {
        return {
            clients: [],
            accounts: [],
            firstName:"",
            lastName:"",
            email:"",
            transactions:[],
            loans:[],
            cards:[],
            number:"",
            accountType:"",
        }
    },

    created(){
               
        axios.get("/api/client/current")
            .then(response => {
                this.clients = response.data;
                this.accounts = this.clients.accounts
                this.accounts = this.accounts.filter(status => status.active == true)
                this.transactions = this.accounts.find(element => element.id==urlName)
                this.loans = this.clients.loans
                this.cards = this.clients.cards
                
                this.accounts = this.accounts.sort(function(a, b){return a.id-b.id})
                this.loans = this.loans.sort(function(a, b){return a.id-b.id})                        
                    
            })
            .catch((error) =>{
                console.log(error);
            });
    },
    methods: {
        newDate(creationDate){ 
            return  new Date(creationDate).toLocaleDateString('es-AR', {month: '2-digit', year: '2-digit'});
        },

        logOut(){
            axios.post('/api/logout')
            .then(location.href = "./index.html")
        },

        newAccount(){
            axios.post("/api/client/current/account",`accountType=${this.accountType}`,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(
                    Swal.fire(
                        'Good job!',
                        'You account has been created!',
                        'success',
                        setTimeout(() => { location.href = "/web/accounts.html" }, 1500)
                    )
                )

                .catch(error =>
                    Swal.fire({
                        icon: 'error',
                        title: 'You cannot create more accounts (MAX-3)',
                    }),
                    setTimeout(() => { location.href = "/web/accounts.html" }, 1000)
                )
        },

        functionRemove(accountId){
            axios.patch("/api/client/current/account/remove",`accountId=${accountId}`)
            .then(() => {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account was deleted',
                    timer: 1000
                  })
            })
            .then(response => window.location.reload())
            .catch(error => 
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: error.response.data,
                  }))
        },

        applyLoan(){
            location.href = "/web/loan-application.html"
        },

        makeTransfer(){
            location.href = "/web/transfer.html"
        },
        toggle(){
            let sidebar = document.querySelector(".sidebar"); 
            sidebar.classList.toggle("active");                         
        }, 

        },
}).mount('#app');