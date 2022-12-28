<template>
    <header class="SignUp content">
        <div class="SignUp-video">
            
            <video class="video" muted autoplay loop>
                <source src="../assets/fondo.mp4">
            </video>
        </div>

        <div class="SignUp-overlay"></div>

        <div class="SignUp-content1">
            <h1 class="titulo">TU TIENDA AMIGA</h1>
            
        </div>
       
        <div class="SignUp-content">
            <h2>Registrarse</h2>
            <form v-on:submit.prevent="processSignUp">
            <label for='NombreUsuario'>Nombre de usuario:</label>
            <input name="NombreUsuario" type="text" v-model="user.username" class="input" placeholder="Nombre de usuario">
            <br>
            <label for='Contraseña'>Contraseña: </label>
            <input for="Contraseña" type="password" v-model="user.password" class="input" placeholder="Contraseña">
            <br>
            <label for="Nombre">Nombre:</label>        
            <input name="Nombre" type="text" v-model="user.name" class="input" placeholder="Nombre">
            <br>
            <label for="Email">Email:</label>
            <input name="Email" type="email" v-model="user.email" class="input" placeholder="Email">
            <br>
            <label for="SaldoInicial">Saldo inicial:</label>  
            <input name="SaldoInicial" type="number" v-model="user.balance" class="input" placeholder="Saldo inicial">
            <br>
            <button type="submit" class="boton">Registrarse</button>
            </form>
            
        </div>

    </header>
</template>


<script>
import gql from "graphql-tag";

export default {
    name: "SignUp",

    data: function() {
        return {
        user: {
            username: "",
            password: "",
            name: "",
            email: "",
            balance: 0,
        },
        };
    },

  methods: {
    processSignUp: async function() {
      await this.$apollo
        .mutate({
          mutation: gql`
            mutation($userInput: SignUpInput!) {
              signUpUser(userInput: $userInput) {
                refresh
                access
              }
            }
          `,
          variables: {
            userInput: this.user,
          },
        })
        .then((result) => {
          let dataLogIn = {
            username: this.user.username,
            token_access: result.data.signUpUser.access,
            token_refresh: result.data.signUpUser.refresh,
          };

          this.$emit("completedSignUp", dataLogIn);
        })
        .catch((error) => {
          alert("ERROR: Fallo en el registro.");
        });
    },

  },
}
</script>


<style>

  *{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Lobster', cursive;
  content: center;
  height: max-content;
      
  }
  body{
  margin: 0;
  font-size: 1rem;
  line-height: 1.5;
  color:black;
  overflow-x: hidden;
  }
  .SignUp{
    height: 75vh;
    display: flex;
    align-items: center;
    color:black;
    max-height: 100vh;
  }
  .SignUp{
      
    padding-left: 1rem;
    padding-right: 1rem;
    margin:auto;
    text-align: center;


  }
  .SignUp-video{
    position: absolute;
    top:15.5vh;
    left: 0;
    width: 100%;
    height: 75.5vh;
    overflow:hidden;

  }
  .titulo{
    font-size: 6vw;
    margin-top: 2vh;
  }
  video{
    width: 100%;
  }

  .SignUp-overlay{
    height: 75.5vh;
    width: 100vw;
    position:absolute;
    top:15.5vh;
    left: 0;
    background: coral;
    z-index: 1;
    opacity:0.20;
  }

  .SignUp-content1{
    position: relative;
    right: 1.5vw;
    z-index: 2;
    background:#ced4da;
    border-color: black;
    padding-left: 3vw;
    border-radius: 4%;
    width: max-content;

  }
  .SignUp-content{
    padding-right: 1vw;
    width: 45%;
    z-index: 2;
    background: rgb(247, 141, 102);
    border-color: black;
    border-radius: 4%;
    border: 1px solid #000;
  }

  .boton {
    margin-top: 10px;
    margin-bottom: 10px;
    min-width: 130px;
    height: 40px;
    color: #fff;
    padding: 5px 10px;
    font-weight: bold;
    cursor: pointer;
    transition: all 0.3s ease;
    position: relative;
    display: inline-block;
    outline: none;
    border: 1px solid #000;
    color: #000;
    background: transparent;
    border-radius: 10%;
  }
  .boton:hover:after {
    top: 0;
    left: 0;
  }

  .boton:after {
    content: "";
    width: 100%;
    z-index: -1;
    position: absolute;
    height: 100%;
    top: 5px;
    left: 5px;
    transition: 0.2s;
    background-color: #ced4da;
    border-radius: 4%;
  }

  .input{
    padding: 1px;
    block-size: 40px;
    color: black;
    border: 1px solid #000;
    box-sizing: content-box;
    font-size: large;
    display: inline-block;
    width: 50%;
    cursor: pointer;
    height: 42px;
    outline: 0; 
    border: 0;
    border-radius: 0;
    background: #ced4da;
    font-size: large;
    border:2px solid rgba(0,0,0,0.2);
    border-radius: 12px;
    position: relative;
    transition: all 0.25s ease;
    text-align: center;
  }

  .espacio{
    height: 30px;
  }

  label{
    display: inline-block;
    width: 300px;
  }

</style>