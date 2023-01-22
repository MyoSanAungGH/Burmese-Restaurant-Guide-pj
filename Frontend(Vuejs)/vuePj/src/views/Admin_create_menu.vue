<template>
  <div>
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <v-col cols="10">
        <v-form ref="menuForm" v-model="menuForm">
          <v-text-field
            v-model="name"
            label="Menu Name"
            :counter="20"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Menu Name must be less than 50 characters',
            ]"
            required
          ></v-text-field>

          <v-text-field
            v-model="price"
            label="Price"
            type="number"
            suffix="MMK"
            max="99999"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v > 0 && v <= 99999) ||
                'Price must be between 1 to 99999 MMK',
            ]"
            required
          ></v-text-field>

          <v-select
            v-model="restaurant"
            :items="restaurantList"
            item-text="restaurantName"
            item-value="id"
            :rules="[(v) => !!v || 'Required']"
            label="Restaurant"
            required
          ></v-select>

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
            :disabled="!menuForm"
            color="success"
            class="mt-4 mr-4"
            @click="createMenu()"
          >
            <span v-if="!loading">Create Menu</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>

          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Create Menu Failed!
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
  name: "admin_create_menu",
  components: { admin_sidebar },

  data() {
    return {
      menuForm: false,
      errorAlert: false,
      loading: false,

      name: " ",
      price: "0000",
      restaurant: " ",

      picture: null,
      picturePreviewPath: null,
      restaurantList: [],
    };
  },

  async created() {
    await this.fetchRestaurantList();
  },

  methods: {
    async fetchRestaurantList() {
      const resp = await utils.http.get("/admin/restaurant");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.restaurantList = data;
        }
      }
    },

    async createMenu() {
      if (this.$refs.menuForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        let respPictureData = null;
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
          const respMenu = await utils.http.post(
            "/admin/menu/create", {
            name:this.name,
            price: this.price,
            restaurant: { id: this.restaurant },
            menuPath: respPictureData,
          });
          if (respMenu && respMenu.status === 200) {
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

<style>
</style>