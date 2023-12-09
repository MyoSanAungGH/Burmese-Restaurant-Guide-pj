<template>
  <div>
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <v-col cols="10">
        <v-form class="px-10" ref="restaurantForm" v-model="restaurantForm">
          <v-text-field
            v-model="restaurantName"
            label="Restaurant Name"
            :counter="30"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 30) ||
                'Restaurant Name must be less than 30 characters',
            ]"
            required
          >
          </v-text-field>

          <v-text-field
            v-model="phone"
            label="Phone Number"
            placeholder="eg.9876543210"
            :rules="[
              (v) => !!v || 'Required',
              (v) => (v && v.length <= 11) || 'Phone number must be 11 digits ',
            ]"
            required
          >
          </v-text-field>

          <v-text-field
            v-model="address"
            label="Restauran's Address"
            :counter="50"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Address must be less than 50 characters',
            ]"
            required
          >
          </v-text-field>

          <v-text-field
            v-model="overview"
            label="Overview"
            :counter="5000"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 5000) ||
                'Overview must be less than 5000 characters',
            ]"
            required
          >
          </v-text-field>

          <v-select
            v-model="divisionMstate"
            :items="divisionMStateList"
            item-text="name"
            item-value="id"
            :rules="[(v) => !!v || 'Required']"
            label="Division/State"
            required
          >
          </v-select>

          <v-file-input
            v-model="picture"
            label="Picture"
            show-size
            prepend-icon="mdi-table"
            placeholder="Choose Picture Image"
            accept="image/png, image/jpg"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 10000000 ||
                'Image size should be less than 10 MB!',
            ]"
            @change="onChangePicture"
            ></v-file-input>

          <v-img
            v-if="picturePreviewPath != null"
            :src="picturePreviewPath"
            width="200"
            height="150"
            contain
          ></v-img>

          <v-btn
            :disabled="!restaurantForm"
            color="success"
            class="mt-4 mr-4"
            @click="createRestaurant()"
          >
            <span v-if="!loading">Create Restaurant</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>

          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Create Restaurant Failed!
          </v-alert>
        </v-form>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "@/components/admin_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "admin_create_restaurant",
  components: { admin_sidebar },

  data() {
    return {
      restaurantForm: false,
      errorAlert:false,
      loading:false,
      
      restaurantName: " ",
      phone: 0,
      address: " ",
      overview: " ",
      divisionMstate: " ",
      
      picture: null,
      picturePreviewPath: null,
      
      divisionMStateList: [],
    };
  },

  async created() {
    await this.fetchDivisionList();
  },

  methods: {
    async fetchDivisionList() {
      const resp = await utils.http.get("/admin/divisionMstate");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.divisionMStateList = data;
        }
      }
    },

    async createRestaurant() {
      if (this.$refs.restaurantForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        let respPictureData = null
        const respPicture = await utils.http.postMedia(
          "/admin/file/create",
          this.picture,
          this.picture.type
        );
        if (respPicture && respPicture.status === 200) {
          respPictureData = await respPicture.text();
        } else {
          this.errorAlert = true;
        }

        if (respPictureData) {
          const respRestaurant = await utils.http.post(
            "/admin/restaurant/create",{
              restaurantName: this.restaurantName,
              phone: this.phone,
              address: this.address,
              overview: this.overview,
              divisionMstate: {id:this.divisionMstate},
              picPath: respPictureData,
            });
            if(respRestaurant && respRestaurant.status === 200) {
            this.$router.push({ path: "/admin" });
          } else {
            this.errorAlert = true;
          }
        }
        this.loading = false;
      }
    },

    onChangePicture(picture) {
      this.picturePreviewPath = URL.createObjectURL(picture);
    },
  },
};
</script>