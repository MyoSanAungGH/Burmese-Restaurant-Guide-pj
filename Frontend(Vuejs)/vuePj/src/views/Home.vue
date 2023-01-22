<template>
  <div style="background-color: antiquewhite">
    <v-row>
      <v-col cols="3">
        <v-card class="my-auto mx-7" height="" width="">
          <v-navigation-drawer
            src="@/assets/burma-myanmar-flag-map_118047-784.jpg"
            width=""
            height=""
            permanent
          >
            <v-row justify="center">
              <div class="wrapper">
                <p class="animation text-h5 mt-4">Division/State</p>
              </div>
            </v-row>

            <v-list style="pointer-events: visibleFill">
              <v-list-item
                v-for="(division, index) in divisionList"
                :key="index"
                link
                @click="onClickDivision(division)"
                style="
                  font-weight: bolder;
                  color: darkslategray;
                  font-size: larger;
                  font-family: Verdana, Geneva, Tahoma, sans-serif;
                "
              >
                <v-list-item-content>
                  <v-list-item-title class="hover-div">
                    {{ division.name }}
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>

      <v-col cols="9">
        <v-row>
          <v-col
            cols="4"
            v-for="(restaurant, index) in restaurantList"
            :key="index"
          >
            <v-card @click="gotoRestaurantDetail(restaurant)" height="400">
              <v-card-text>
                <v-img
                  :src="localDomain + restaurant.picPath"
                  max-height="250"
                  cover
                ></v-img>
                <div
                  class="text-h5 pt-4"
                  style="font-family: cursive; color: saddlebrown"
                >
                  <v-icon class="mr-2">mdi-silverware-fork-knife </v-icon
                  >{{ restaurant.restaurantName }}
                </div>
                <div
                  class="text-h6 pt-3"
                  style="font-family: monospace; font-style: oblique"
                >
                  <v-icon>mdi-map-marker</v-icon> {{ restaurant.address }}
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "home",

  components: {},

  data() {
    return {
      localDomain: utils.constant.localDomain,

      divisionList: [],
      restaurantList: [],
    };
  },

  async created() {
    await this.fetchDivision();
    await this.fetchRestaurant();
  },

  methods: {
    async fetchDivision() {
      const resp = await utils.http.get("/admin/divisionMstate");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.divisionList = data;
        }
      }
    },

    async fetchRestaurant() {
      const resp = await utils.http.get("/restaurant");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.restaurantList = data;
        }
      }
    },

    async gotoRestaurantDetail(restaurant) {
      this.$router.push({
        path: "/restaurant_details/" + restaurant.id,
      });
    },

    async onClickDivision(division) {
      const resp = await utils.http.get(
        "/restaurant/divisionMstate/" + division.id
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.restaurantList = data;
        }
      }
    },
  },
};
</script>

<style scoped>
.animation {
  font-size: larger;
  font-weight: bolder;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  color: midnightblue;
  animation-name: rightToLeft;
  animation-duration: 4.5s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
}

/* .wrapper{
  width: 800px;
  margin: auto;
  border: px;
  overflow: hidden;
}



@keyframes rightToLeft {
  0% {
    transform: translateX(200px);
  }
  100% {
    transform: translateX(-160px);
  }
} */
</style>