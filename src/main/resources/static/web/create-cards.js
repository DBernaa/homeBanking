
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
            totalTransaction:[],
            totalBalance:[],
            cardType:[],
            cardColor:[]

        }
    },

    created(){
               
        axios.get("/api/client/current")
            .then(response => {
                this.clients = response.data;
                this.accounts = this.clients.accounts
                this.cards = this.clients.cards
                console.log(this.cards);                             
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

        newCard(){
            axios.post('/api/client/current/cards', `cardType=${this.cardType}&cardColor=${this.cardColor}`,
                    {headers: {'content-type': 'application/x-www-form-urlencoded'}})
                    .then(() => {                       
                            Swal.fire({
                                position: 'top-end',
                                icon: 'success',
                                title: 'Your card was created successfully',
                                showConfirmButton: false,
                                timer:1500
                            }),
                           setTimeout(() => { location.href = "/web/cards.html"}, 1500)                       
                    })
                    .catch((error) => {
                        Swal.fire({
                            icon: 'error',
                            timer:2000,
                            showConfirmButton: false,
                            text:error.response.data
                        }),
                        setTimeout(() => { location.href = "/web/create-cards.html"}, 2000)
                    });
        },
    },
}).mount('#app');