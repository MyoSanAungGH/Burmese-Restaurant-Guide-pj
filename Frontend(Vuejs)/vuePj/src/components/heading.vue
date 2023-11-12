<template>
  <v-app-bar app style="background-color: slategrey">
    <a class="navtitle" href="/">All Restaurant</a>
    
    <v-spacer></v-spacer>

    <v-row justify="center">
            <div class="wrapper">
              <p class="animation text-h5 mt-2">Burmese Restaurant Guide</p>
            </div>
    </v-row>

    <v-spacer></v-spacer>

    <!-- <router-link class="navlink ml-2" to="/">Home</router-link>  -->

    <!-- <span v-if="!isLogin" class="mx-2 navlink ">|</span> -->
    
    <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
      >Register</router-link
    >

    <!-- <span v-if="!isLogin" class="mx-2 navlink ">|</span> -->
    <router-link v-if="!isLogin" class="mx-5 navlink" to="/login"
      >Login</router-link
    >


    <router-link v-if="isLogin" class="mx-2 navlink" to="/profile"
      >Profile</router-link
    >

    <!-- <span class="navlink ml-2" v-if="isLogin"></span> -->
<!-- 
    <router-link v-if="isLogin" class="mx-2 navlink" to="/about"
      >About</router-link
    > -->

    <a v-if="isLogin" class="mx-2 navlink" @click="logout()">LogOut</a>
  </v-app-bar>
</template>


<script>
export default {
  name: "heading",

  data: () => ({
    loginUser: {},
    isLogin: false,
  }),

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );

    //IsLogIn
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    logout() {
      this.$store.commit("logout");
      if(this.$route.path != "/"){
      this.$router.push({path:"/"});
    }

    },
  },
};
</script>

<style scoped>
.navlink {
  color:darkblue;
  font-size:x-large;
  font-weight:bold;
  text-decoration: wavy;

}

.navlink:hover {
  animation-direction: normal;
  cursor: pointer;
  text-decoration:solid;
}

.navtitle {
  color:rgb(60, 0, 255);
  font-size:x-large;
  text-decoration:none;
  cursor: pointer !important;
  margin-left: 20px;
}

.wrapper{
  width: 800px;
  margin:0ch;
  border: 6px;
  overflow: hidden;
}

.animation{
  font-size:100px;
  font-weight: bolder;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color:chartreuse;
  animation-name: rightToLeft;
  animation-duration: 9.5s;
  animation-timing-function:linear;
  animation-iteration-count: infinite;
  /* animation: rightToLeft 4.5s linear infinite; */
}

@keyframes rightToLeft {
  0% {
    transform: translateX(700px);
  }
  100% {
    transform: translateX(-160px);
  }
}
</style>