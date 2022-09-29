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
            all:[],
            transaction:[],
        }
    },

    created(){
               
        axios.get("/api/client/current")
            .then(response => {
                this.clients = response.data;
                this.accounts = this.clients.accounts
                this.all = this.accounts.find(element => element.id==urlName)
                this.transaction = this.all.transactions
                console.log(this.transaction);
                this.transaction = this.transaction.sort(function(a, b){return b.id-a.id})
            })
            .catch((error) =>{
                console.log(error);
            });
        
        
    },
    methods: {
        
    },
}).mount('#app');