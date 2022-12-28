<template>

  <header class="home">
      <section class="header2 content2">
  <div class="LogIn-video">
            
            <video class="video" muted autoplay loop>
                <source src="../assets/fondo.mp4">
            </video>
        </div>
   
        <div class="home-overlay"></div>

            <div class="home-content">
                <h1 class="titulo">Bienvenid@ {{ userDetailById.name }}</h1>
                <h2>Su usuario es: {{ userDetailById.username }}</h2>
                <h2>E-mail registrado: {{ userDetailById.email }}</h2>
                <h2>Su saldo es: ${{ accountByUsername.balance }} COP</h2>
            </div>
      </section>

      <section class="tabla">
      <form v-on:submit.prevent="processCompra">
        <table class="tg">
        <colgroup>
        <col style="width: 196px">
        <col style="width: 164px">
        <col style="width: 238px">
        <col style="width: 234px">
        <col style="width: 262px">
        </colgroup>
        <thead>
          <tr>
            <th class="tg-0lax">Nombre del producto</th>
            <th class="tg-0lax">Valor</th>
            <th class="tg-0lax">Seleccionar</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="tg-dg7a">Gaseosa</td>
            <td class="tg-dg7a">$5000</td>
            <td class="tg-dg7a"><label><input type="radio" v-model="createCompra.nombreProducto" class="selecciona" name="selecciona" value="gaseosa"></label></td>
          </tr>
          <tr>
            <td class="tg-dg7a">Galletas</td>
            <td class="tg-dg7a">$1300</td>
            <td class="tg-dg7a"><label><input type="radio" v-model="createCompra.nombreProducto" class="selecciona" name="selecciona" value="galletas"></label></td>
          </tr>
            <tr>
            <td class="tg-dg7a">Chocoltina</td>
            <td class="tg-dg7a">$2500</td>
            <td class="tg-dg7a"><label><input type="radio" v-model="createCompra.nombreProducto" class="selecciona" name="selecciona" value="Chocolatina"></label></td>
          </tr>
            <tr>
            <td class="tg-dg7a">Papas Naturales</td>
            <td class="tg-dg7a">$1700</td>
            <td class="tg-dg7a"><label><input type="radio" v-model="createCompra.nombreProducto" class="selecciona" name="selecciona" value="Papas naturales"></label></td>
          </tr>
        </tbody>

        </table>
        <group class="botones">
          <td class="tg-dg7a"><input type="number" class="input" v-model="createCompra.cantidad" placeholder="Cantidad"></td>

          <button class="boton" type="submit">Realizar Compra</button>
        </group>
      </form>
                <h2>Último Movimiento:
                  <span>
                    {{ accountByUsername.lastChange.substring(0, 10) }}  
                    {{ accountByUsername.lastChange.substring(11, 19) }}
                  </span>
          </h2>
  </section>


  </header>
</template>


<script>
import gql from "graphql-tag";
import jwt_decode from "jwt-decode";

export default {
  name: "Home",

  data: function () {
    return {
      userId: jwt_decode(localStorage.getItem("token_refresh")).user_id,
      userDetailById: {
        username: "",
        name: "",
        email: "",
      },
      username: localStorage.getItem("username") || "none",
      accountByUsername: {
        balance: "",
        lastChange: "",
      },
      createCompra: {
        username: localStorage.getItem("username"),
        nombreProducto: "",
        cantidad: "",
      }      
    };
  },

  apollo: {
    userDetailById: {
      query: gql`
        query ($userId: Int!) {
          userDetailById(userId: $userId) {
            username
            name
            email
          }
        }
      `,
      variables() {
        return {
          userId: this.userId,
        };
      }
    },
    accountByUsername: {
      query: gql`
        query ($username: String!) {
          accountByUsername(username: $username) {
            balance
            lastChange
          }
        }
      `,
      variables() {
        return {
          username: this.username,
        };
      },
    },
  },
  created: function () {
    this.$apollo.queries.accountByUsername.refetch();
  },
  methods: {
    processCompra: async function() {
      
      if (localStorage.getItem("token_access")  === null ||
          localStorage.getItem("token_refresh") === null ) {
        this.$emit("logOut");
        return;
      }

      localStorage.setItem("token_access", "");

      await this.$apollo
        .mutate({
          mutation: gql`
            mutation ($refresh: String!) {
              refreshToken(refresh: $refresh) {
                access
              }
            }
          `,
          variables: {
            refresh: localStorage.getItem("token_refresh"),
          },
        })
        .then((result) => {
          localStorage.setItem("token_access", result.data.refreshToken.access);
        })
        .catch((error) => {
          this.$emit("logOut");
          return;
        });
      
      await this.$apollo
        .mutate({
          mutation: gql`
            mutation($compra: CompraInput!) {
              createCompra(compra: $compra) {
                username
                nombreProducto
                cantidad
              }
            }
          `,
          variables: {
            compra: this.createCompra,
          },
        })
        .then((result) => {
          alert("Compra exitosa, Su producto será enviado.");
        })
        .catch((error) => {
          alert("Saldo Insuficiente / verifique la cantidad");
        });
    },
  },  
};
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
.home{
  
    width: 100%;
    height: 26px;
    background-size: cover;
    background-position:center ;

}
.header2{

    height: 33vh;
    display: flex;
    align-items: center;
    color:black;
    max-height: 100vh;
}
.content2{
    
 
    margin:auto;
    text-align: center;


}

.titulo{
    font-size: 3vw;
    margin-top: 2vh;
    

}

.home-overlay{
    height: 75.5vh;
    width: 100vw;
    position:absolute;
    top:15.5vh;
    left: 0;
    background: coral;
    z-index: -1;
    opacity:0.20;

}
.home-video{
    position: absolute;
    top:15.5vh;
    left: 0;
    width: 100%;
    height:90vh;
    overflow:hidden;
    z-index: -1;
}
.home-content{
    position: absolute;
 
    width: max-content;
   z-index: 2;
   background: rgb(247, 141, 102);
   border-color: black;
   border-radius: 20%;
   border: 1px solid #000;
   opacity: 0.8;
   margin-left: 30%;

}
.tabla{
  position: absolute;
  width: max-content;
  z-index: 2;
  margin-left: 20%;
  margin-top: 0%;
  text-align: center;
  font-size: 1vw;
}



  .tg  {
      border-collapse:collapse;
      border-color:#aaa;
      border-spacing:0;
      table-layout: fixed;
      width: 1094px
    }
  .tg td{
      background-color:rgb(254, 195, 173);
      border-bottom-width:1px;
      border-color:rgb(160, 216, 228);
      border-style:solid;
      border-top-width:1px;
    border-width:0px;
    color:#333;
   
 
    overflow:hidden;
    padding:10px 5px;
    word-break:normal;
}
  .tg th{
      background-color:coral;
      border-bottom-width:1px;
      border-color:#aaa;
      border-style:solid;
      border-top-width:1px;
    border-width:0px;
    color:#fff;
    font-weight:normal;
    overflow:hidden;
    padding:10px 5px;
    word-break:normal;
}
  .tg .tg-getp{
      border-color:#f7f2f2;
      text-align:center;
      vertical-align:top
    }
  .tg .tg-0lax{
      text-align:center;
      vertical-align:top
    }
  .tg .tg-dg7a{
      background-color:#FCFBE3;
      text-align:center;

    
    }
    .botones{


        z-index: 2;
        margin-left: -45%;
        margin-top: 0%;

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
        z-index: 1;
        position: absolute;
        height: 100%;
        top: 5px;
        left: 5px;
        transition: 0.2s;
        background-color: #ced4da;
        border-radius: 4%;
        opacity:0.60;
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

</style>
