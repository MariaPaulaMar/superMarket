<template>
    <header class="LogIn content">
        <div class="LogIn-video">
            
            <video class="video" muted autoplay loop>
                <source src="../assets/fondo.mp4">
            </video>
        </div>

        <div class="LogIn-overlay"></div>

        <div class="LogIn-content1">
            <h1 class="titulo">TU TIENDA AMIGA</h1>
            
        </div>
       
        <div class="LogIn-content">
            <h2>Iniciar sesión</h2>
            <form v-on:submit.prevent="processLogInUser">
                <label for="NombreUsuario">Nombre de usuario: </label>
                <input name="NombreUsuario" class= "input" type="text" v-model="user.username" placeholder="Nombre de usuario">
                <br class="espacio">
                <label for="Contraseña">Contraseña: </label>
                <input name="Contraseña" class= "input" type="password" v-model="user.password" placeholder="Contraseña">
                <br class="espacio">
                <button type="submit" class="boton">Iniciar Sesion</button>
                <br class="espacio">
              </form>
              <h2>¿Eres nuevo?</h2>
              <button type="text" class="boton" v-if="!is_auth" v-on:click="loadSignUp">Regitrarse</button>
        </div>

    </header>

</template>


<script>
import gql from "graphql-tag";

export default {
  name: "LogIn",

  data: function() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },

  methods: {
    processLogInUser: async function() {
      await this.$apollo
        .mutate({
          mutation: gql`
            mutation($credentials: CredentialsInput!) {
              logIn(credentials: $credentials) {
                refresh
                access
              }
            }
          `,
          variables: {
            credentials: this.user,
          },
        })
        .then((result) => {
          let dataLogIn = {
            username: this.user.username,
            token_access: result.data.logIn.access,
            token_refresh: result.data.logIn.refresh,
          };

          this.$emit("completedLogIn", dataLogIn);
        })
        .catch((error) => {
          alert("ERROR 401: Credenciales Incorrectas.");
        });
    },
        loadSignUp: function(){
      this.$router.push({name: "signUp"})
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
.LogIn{
    height: 80vh;
    display: flex;
    align-items: center;
    color:black;
    max-height: 100vh;
}
.content{
    
    padding-left: 1rem;
    padding-right: 1rem;
    margin:auto;
    text-align: center;


}
.LogIn-video{
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


.LogIn-overlay{
    height: 75.5vh;
    width: 100vw;
    position:absolute;
    top:15.5vh;
    left: 0;
    background: coral;
    z-index: 1;
    opacity:0.20;

}
.LogIn-content1{
    position: relative;
    right: 1.5vw;
    z-index: 2;
    background:#ced4da;
    border-color: black;
    padding-left: 3vw;
    border-radius: 4%;
    width: max-content;
    
 
 }
.LogIn-content{
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