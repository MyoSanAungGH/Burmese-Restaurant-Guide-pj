<template>
  <div style="background-color: beige;">
    <v-row justify="center">
      <v-col cols="9">
        <v-card width="100%">
          <v-card-text>
            <div class="pa-auto" 
            style="font-family:cursive; font-weight: 700; font-size: large; text-decoration: wavy; color: goldenrod;">
            {{ restaurant.restaurantName }} Restaurantမှ လှိုက်လဲစွာကြိုဆိုပါတယ်....
            </div>
            
            <v-row class="py-4">
              <v-col cols="6">
                <div style="font-weight: bold; font-style: oblique"
                >
                  <v-icon class="mr-1 ml-10"> mdi-map-marker</v-icon>
                  {{ restaurant.address }} 
                </div>
              </v-col>

              <v-col cols="6">
                <div style="font-weight: bold; font-style: oblique"
                >
                  <v-icon class="mr-2">mdi-phone-classic</v-icon>
                  {{ restaurant.phone }}
                </div>
              </v-col>
            </v-row>

            <v-img
              class="ma-0"
              :src="localDomain + restaurant.picPath"
              contain
            ></v-img>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="">
        <div class="overview">{{ restaurant.overview }}</div>
      </v-col>
    </v-row>

    <v-row class="mt-10">
      <v-divider light></v-divider>
    </v-row>
    

    <v-row justify="center">
      <div class="wrapper">
        <p class="animation text-h3">Avaliable Menu List</p>
      </div>
    </v-row>

    <v-row class="mt-10">
    <v-container class="">
    <v-row>
      <v-col cols="3"
      v-for="(menu, index) in menuList"
      :key="index"
      ><v-card hover >
        <v-card-text>
          <v-img
          :src="localDomain + menu.menuPath"
          max-height="200"
          cover
          ></v-img>
          <div class="text-h5 pt-5" style="font-family: cursive; color: saddlebrown;">
                  {{ menu.name }}
          </div>
          <div class="text-h5 pt-5" style="font-family: cursive; color: saddlebrown;">
                  {{ menu.price }}kyat</div>
        </v-card-text>
      </v-card>
      </v-col>
    </v-row>
  </v-container>
  </v-row>

    </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "restaurant_details",

  data() {
    return {
      localDomain: utils.constant.localDomain,

      restaurant_id: this.$route.params.id,
      loginUser: {},
      restaurant: {},
      menuList:[],
    };
  },

  async created() {
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

    await this.fetchRestaurant();
    await this.fetchMenuList();
  },

  methods: {
    async fetchRestaurant() {
      const resp = await utils.http.get("/restaurant/" + this.restaurant_id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        //console.log(data);
        if (data) {
          this.restaurant = data;
        }
      }
    },

    async fetchMenuList(){
      const resp = await utils.http.get("/api/menu/restaurant/" + this.restaurant_id);
      if(resp && resp.status === 200){
        const data = await resp.json();
        if(data){
          this.menuList = data;
        }
        
      }
    }
  },
};
</script>

<style scoped>

.overview{
  word-spacing: 1em;
  text-align: left;
  font-size: medium;
  font-display: initial;
  font-family: cursive;
}


body{
  background: #222222;
  margin: auto;
  overflow: hidden;
}

.wrapper{
  width: 800px;
  margin: auto;
  border: px;
  overflow: hidden;
}

.animation{
  font-size: larger;
  font-weight: bolder;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color:darkkhaki;
  animation-name: rightToLeft;
  animation-duration: 4.5s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  /* animation: rightToLeft 4.5s linear infinite; */
}

@keyframes rightToLeft {
  0% {
    transform: translateX(200px);
  }
  100% {
    transform: translateX(-160px);
  }
}
</style>