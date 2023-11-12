<template>
  <div style="">
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <v-col cols=" 10">
        <v-data-table
          :headers="headers"
          :items="restaurantList"
          :items-per-page="6"
          style="background-color: burlywood"
        >
          <template v-slot:item.picPath="{ item }">
            <v-img
              :src="localDomain + item.picPath"
              width="100"
              height="100"
              contain
            >
            </v-img>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-btn
              style="color: indianred"
              class="grey"
              elevation="4"
              x-small
              @click="OnClickUpdateRestaurant(item)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>

            <v-btn
              style="background-color: burlywood; color: darkblue"
              x-small
              elevation="20"
              @click="
              toDeleteRestaurant = item;
                deleteDialog = true;
              "
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <v-dialog v-model="deleteDialog" width="400">
      <v-card>
        <v-toolbar color="primary" dark>
          <div>Delete This Restaurant?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">ID</v-col>
            <v-col cols="7">{{ toDeleteRestaurant.id }}</v-col>
            <v-col cols="3" class="font-weight-bold">Name</v-col>
            <v-col cols="7">{{ toDeleteRestaurant.restaurantName }}</v-col>
          </v-row>
        </v-card-text>

        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteRestaurant()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <v-toolbar color="primary" dark>
          <div>Update Restaurant?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <v-card-text class="pa-4">
          <v-form ref="restaurantForm" v-model="restaurantForm">
            <v-text-field
              v-model="toUpdateRestaurant.restaurantName"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'Restaurant Name must be less than 50 characters',
              ]"
              label="Restaurant Name"
              required
            >
            </v-text-field>

            <v-text-field
              v-model="toUpdateRestaurant.phone"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 11) ||
                  'Phone Number must be less than 11 characters',
              ]"
              label="Phone Number"
              required
            >
            </v-text-field>

            <v-text-field
              v-model="toUpdateRestaurant.address"
              label="Address"
              required
            >
            </v-text-field>

            <v-text-field
              v-model="toUpdateRestaurant.overview"
              :counter="5000"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 5000) ||
                  'Overview must be less than 5000 characters',
              ]"
              label="Overview"
              required
            >
            </v-text-field>

            <v-select
              v-model="toUpdateRestaurant.divisionMstate"
              :items="divisionList"
              item-text="name"
              item-value="id"
              :rules="[(v) => !!v || 'Required']"
              label="Division/State"
              required
            >
            </v-select>

            <v-file-input
              v-model="toUpdateRestaurant.picture"
              label="Select Picture"
              show-size
              prepend-icon="mdi-table"
              placeholder="Choose Picture"
              accept="image/png, image/jpeg"
              :rules="[
                (v) =>
                  !v ||
                  v.size < 10000000 ||
                  'Image size should be less than 10 MB!',
              ]"
              @change="onChangePicture"
            ></v-file-input>

            <!--Preview pic -->
            <v-img
              v-if="picturePreviewPath == null"
              :src="localDomain + toUpdateRestaurant.picPath"
              width="200"
              height="150"
              contain
            >
            </v-img>

             <!--selected pic -->
            <v-img
              v-if="picturePreviewPath != null"
              :src="picturePreviewPath"
              width="200"
              height="150"
              contain
            >
            </v-img>

            <v-btn
              :disabled="!restaurantForm"
              color="success"
              class="mr-4 mt-5"
              @click="updateRestaurant"
            >
              <span v-if="!loading">Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Update Restaurant Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "@/utils/utils";
import admin_sidebar from "@/components/admin_sidebar.vue";

export default {
  name: "admin",

  components: { admin_sidebar },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "RestaurantPhoto", value: "picPath", sortable: false },
        { text: "Name", value: "restaurantName", sortable: true },
        { text: "PhoneNumber", value: "phone", sortable: true },
        { text: "Address", value: "address", sortable: true },
        { text: "Overview", value: "overview", sortable: true },
        { text: "Division", value: "divisionMstate.name", sortable: true },
        { text: "RestaurantCreateDate", value: "createdAt", sortable: true },
        { text: "RestaurantUpdateAt", value: "updatedAt", sortable: true },
        { text: "Actions", value: "actions", sortable: false },
      ],
      restaurantList: [],

      deleteDialog: false,
      updateDialog: false,
      restaurantForm: false,

      toDeleteRestaurant: {},

      toUpdateRestaurant: {
        restaurantName: " ",
        phone: " ",
        address: " ",
        overview: " ",
        divisionMstate: " " ,
        picture: " ",
        picPath: " ",
      },

      picturePreviewPath: null,
      errorAlert: false,
      loading: false,
      divisionList: [],
    };
  },

  async created() {
    await this.fetchDivision();
    await this.fetchRestaurant();
  },

  methods: {
    async fetchRestaurant() {
      const resp = await utils.http.get("/restaurant");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.restaurantList = data;
        }
      }
    },

    async fetchDivision() {
      const resp = await utils.http.get("/admin/divisionMstate");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.divisionList = data;
        }
      }
    },

    async deleteRestaurant() {
      const resp = await utils.http.del(
        "/admin/restaurant/delete/" + this.toDeleteRestaurant.id
      );
      if (resp && resp.status === 200) {
        this.deleteDialog = false;

        await this.fetchRestaurant();
      } else {
        this.errorAlert = true;
      }
    },

    OnClickUpdateRestaurant(item) {
      this.updateDialog = true;
      this.toUpdateRestaurant = Object.assign({}, item);
      this.toUpdateRestaurant.picture = null;
    }, 
    
    onChangePicture(picture) {
      this.picturePreviewPath = URL.createObjectURL(picture);
    },

    async updateRestaurant() {
      if(this.$refs.restaurantForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        let picPath= this.toUpdateRestaurant.picPath;

        //Update Picture
        //null> picture is not select
        if (this.toUpdateRestaurant.picture != null) {
          const respPicture = await utils.http.putMedia(
            "/admin/file/update",
            this.toUpdateRestaurant.picture,
            this.toUpdateRestaurant.picture.type,
            this.toUpdateRestaurant.picPath
          );
          if (respPicture && respPicture.status === 200) {
            picPath = await respPicture.text();

          } else {
            console.debug("Picture Update Failed");
          }
          
        }

        //Update Restaurant
        const respRestaurant = await utils.http.put(
          "/admin/restaurant/update/" + this.toUpdateRestaurant.id,
          {
              restaurantName: this.toUpdateRestaurant.restaurantName,
              phone: this.toUpdateRestaurant.phone,
              address: this.toUpdateRestaurant.address,
              overview: this.toUpdateRestaurant.overview,
              divisionMstate: {id:this.toUpdateRestaurant.divisionMstate.id},
              picPath: picPath,
          }
        );
        if(respRestaurant && respRestaurant.status ===200){
          this.toUpdateRestaurant.picture = null;
          this.updateDialog =false;

          await this.fetchRestaurant();
        }else{
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },

   
  },
};
</script>