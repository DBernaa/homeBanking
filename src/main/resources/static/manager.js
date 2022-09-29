const app = Vue.

createApp({
    data() {
        return {
            clients: [],
            firstName:"",
            lastName:"",
            email:"",
            loans:[],
            name:"",
            maxAmount:"",
            payments:"",
        }
    },

    created(){
        axios.get("/api/client")
            .then(response => {
                this.clients = response.data;
            })
            .catch((error) =>{
                console.log(error);
            });

        axios.get("/api/loans")
        .then(response =>{
            this.loans = response.data;
        })
        .catch((error) =>{
            console.log(error);
        });
    },
    methods: {
        addClient(){
            if(this.firstName != "" && this.lastName != "" && this.email != ""){
                axios.post("/rest/clients", {
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                })
                .then(() => window.location.reload())
                .catch(function (error) {
                    console.log(error);
                })
            }
        },

        addLoan(){
            axios.post("/api/admin/loans",`name=${this.name}&maxAmount=${this.maxAmount}&payments=${this.payments}`,
            {headers: {'content-type': 'application/x-www-form-urlencoded'}}) 
            .then(() => window.location.reload())
            .catch(function (error) {
                console.log(error);
            })
        }
        
        
    },
}).mount('#app');