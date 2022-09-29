let urlParams = new URLSearchParams(window.location.search);
let urlName = urlParams.get("id");
const app = Vue.

createApp({
    data() {
        return {
            clients: [],
            cards:[],
            debitCard:[],
            creditCard:[],
            date:"",
            cardId:"",

        }
    },

    created(){
        axios.get("/api/client/current")
            .then(response => {
                this.allData(response)
            })
            .catch((error) =>{
                console.log(error);
            });

    },
    methods: {
        allData(response){
                this.clients = response.data
                this.cards = this.clients.cards
                this.cards = this.cards.filter(status => status.active == true)
                this.date = new Date().toISOString()
                this.typeCards()
                
        },
        newDate(creationDate){ 
            return  new Date(creationDate).toLocaleDateString('es-AR', {month: '2-digit', year: '2-digit'})
        },
        logOut(){
            axios.post('/api/logout')
            .then(location.href = "../index.html")
        },
        typeCards(){
            this.cards.forEach(type =>{
                if(type.cardType == "CREDIT"){
                    this.creditCard.push(type)
                }
                if(type.cardType == "DEBIT"){
                    this.debitCard.push(type)
                }
            })
        },

        removeCards(cardId){
            axios.patch("/api/client/current/cards/remove",`cardId=${cardId}`)
            .then(() => {
                Swal.fire({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Your card was deleted successfully',
                    showConfirmButton: false,
                    timer:1500
                }),
                setTimeout(() => { location.href = "/web/cards.html"}, 1500)
            })
            
        },

        toggle(){
            let sidebar = document.querySelector(".sidebar"); 
            sidebar.classList.toggle("active");                         
        },

    },
}).mount('#app');