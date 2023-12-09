<template>
  <div>
    <v-card class="mx-auto pa-3" max-width="344" style="background-color: aquamarine;">
      <v-card-text>
        <div class="text-h4" style="color:blueviolet">Your Profile</div>

        <div class="text-h6">
          <div v-if="loginUser.role == 'admin'">Admin</div> <br>
          <div>Name :{{ loginUser.name }}</div><br>
          <div>Address:     {{ loginUser.address }}</div> <br>
          <div>Gmail:   {{ loginUser.gmail }}</div><br>
          <div>Joining Date:     {{ loginUser.startJoinDate }}</div><br>
          <div>Visited times:     {{ loginUser.accessCount }}</div><br>
        </div>
      </v-card-text>

      <v-btn text color="teal accent-4" @click="onClickUpdateProfile()">
        Update Your Profile
      </v-btn>
    </v-card>

    <v-dialog v-model="updateProfileDialog" width="500">
      <v-card>
        <v-toolbar color="primary mb-3" dark>
          <div>Update Profile</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateProfileDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <v-card-text>
          <v-form ref="profileForm" v-model="profileForm">
            <v-text-field
              v-model="loginUser.id"
              :rules="[(v) => !!v || 'Required']"
              label="Your id"
              required
            ></v-text-field>

            <v-text-field
              v-model="toUpdateProfile.name"
              :counter="20"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 20) ||
                  'Your Name must be less than 20 characters',
              ]"
              label="User Name"
              required
            ></v-text-field>

            <v-text-field
              v-model="toUpdateProfile.address"
              label="User Address"
              placeholder="eg.Myanmar"
              :rules="[(v) => !!v || 'Required']"
            >
            </v-text-field>

            <v-text-field
              v-model="toUpdateProfile.gmail"
              label="Gmail"
              placeholder="name@gmail.com"
              :rules="[
                (v) => !!v || 'Required',
                (v) => /.+@.+\..+/.test(v) || 'G-mail must be valid',
              ]"
            ></v-text-field>

            <v-text-field
              v-model="toUpdateProfile.password"
              label="Password"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Password must be less than 10 characters',
              ]"
              required
            ></v-text-field>

            <v-btn
              :disabled="!profileForm"
              class="mt-6"
              @click="updateProfile()"
              color="success"
            >
              <span v-if="!loading">Update Profile</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Register Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "@/utils/utils";

export default {
  name: "profile",

  data() {
    return {
        localDomain: utils.constant.localDomain,

      loginUser: {},

      updateProfileDialog: false,
      profileForm: false,
      errorAlert: false,
      loading: false,

      toUpdateProfile: {
        id:"",
        name:"",
        address:"",
        gmail:"name@gmail.com",
        password:"",
      },
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

    await this.fetchUser();
  },

  methods: {
    async fetchUser(){
        const resp = await utils.http.get("/user/profile?id=" + this.loginUser.id)
        if(resp && resp.status === 200){
            const data = await resp.json();
            if(data){
                this.loginUser = data;
            }
        }
    },


    onClickUpdateProfile() {
      this.updateProfileDialog = true;
    },

    async updateProfile() {
      if (this.$refs.profileForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        const respUpdateUser = await utils.http.put("/user/profile/update" ,
        {
          id: this.loginUser.id,
          name: this.toUpdateProfile.name,
          address: this.toUpdateProfile.address,
          gmail: this.toUpdateProfile.gmail,
          password: this.toUpdateProfile.password,
        });
        if (respUpdateUser && respUpdateUser.status === 200) {
          this.updateProfileDialog = false;
          await this.fetchUser();
        }else{
            this.errorAlert = true;
        }
        this.loading = false;
      }
    },
  },
};
</script>