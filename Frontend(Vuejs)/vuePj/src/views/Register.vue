<template>
  <div>
    <v-container fluid>
      <v-row justify="center">
        <v-col cols="12" sm="8" md="6" lg="4">
        <v-card>
          <v-card-text>
            <v-form ref="registerForm" v-model="registerForm">
              <v-text-field
                v-model="name"
                label="Name"
                placeholder="Enter Your Name"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) =>
                    (v && v.length <= 20) || 'Name must less than 20 character',
                ]"
              ></v-text-field>

              <v-text-field
                v-model="age"
                label="Age"
                placeholder="Enter Your Age (e.g 20)"
                type="number"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) => (v && v > 20) || 'Age must be greater than 20',
                  (v) => (v && v < 60) || 'Age must be less than 60',
                ]"
              ></v-text-field>

              <v-select
                v-model="gender"
                label="Gender"
                :items="['male', 'female']"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>

              <v-select
                v-model="role"
                label="Select Role"
                :items="['admin', 'user']"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>

              <v-select
                v-model="status"
                label="status"
                :items="['active', 'deactivated']"
                :rules="[(v) => !!v || 'Required']"
              ></v-select>

              <v-text-field
                v-model="address"
                label="Address"
                placeholder="eg.Myanmar"
                :rules="[(v) => !!v || 'Required']"
              ></v-text-field>

              <v-text-field
                v-model="gmail"
                label="Gmail"
                placeholder="name@gmail.com"
                :rules="[
                  (v) => !!v || 'Required',
                  (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ]"
              ></v-text-field>

              <v-text-field
                v-model="password"
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
                :disabled="!registerForm"
                class="mt-6"
                @click="onClickReg()"
                color="success"
              >
                <span v-if="!loading">Register</span>
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
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "register",

  components: {},

  data() {
    return {
      name: "",
      age: "20",
      gender: "male",
      address: "myanmar",
      role: "",
      status: "",
      gmail: "aungaung@gmail.com",
      password: "1111",
      registerForm: false,
      loading: false,
      errorAlert: false,
    };
  },
  async created() {},

  methods: {
    async onClickReg() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;

        this.loading = true;

        const resp = await utils.http.post("/user/register", {
          name: this.name,
          age: this.age,
          gender: this.gender,
          address: this.address,
          role: this.role,
          status: this.status,
          gmail: this.gmail,
          password: this.password,
        });

        if (resp && resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.$store.commit("setLoginUser", data);
            if (data.role == "admin") {
              this.$router.push({ path: "/admin" });
            } else {
              this.$router.push({ path: "/" });
            }
          }
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.formlink {
  width: 100%;
  color: blueviolet;
}
</style>