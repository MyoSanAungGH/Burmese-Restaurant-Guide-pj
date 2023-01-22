<template>
  <div>
    <v-container fluid>

      <v-row align="center" justify="center" dense>
        <v-img src="@/assets/logo2.jpg" max-width="30%" sizes="60%"></v-img>
      </v-row>

      <v-row justify="center" class="mt-1">
        <v-col cols="12"  sm="5" md="5" lg="5">
          <v-card style="background-color:gray;">
            <v-card-text>
              <v-form ref="loginForm" v-model="loginForm">
                <v-text-field
                  v-model="gmail"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) => /.+@.+\..+/.test(v) || 'G-mail must be valid',
                  ]"
                  class="mt-6"
                  style="font-style: oblique"
                  label="G-mail"
                  required
                ></v-text-field>

                <v-text-field
                  v-model="password"
                  :counter="15"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 15) ||
                      'Password must be less than 16 characters',
                  ]"
                  class="mt-8 mb-16"
                  style="font-style: oblique"
                  :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="passwordShow ? 'text' : 'password'"
                  @click:append="passwordShow = !passwordShow"
                  label="Password"
                  required
                ></v-text-field>

                <v-btn
                  :disabled="!loginForm"
                  class="mt-10 mb-6"
                  style="
                    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial,
                      sans-serif;
                    color: darkseagreen;
                    background-color:darkblue;
                  "
                  @click="login()"
                >
                  <span v-if="!loading">Login</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="primary"
                  ></v-progress-circular>
                </v-btn>

                <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                  Login Failed! <br />
                  Email or Password is wrong!
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
import utils from "@/utils/utils";

export default {
  name: "Login",

  components: {},

  data() {
    return {
      loginForm: false,
      gmail: "@gmail.com",
      password: "1111",
      errorAlert: false,
      loading: false,
      passwordShow: false,
    };
  },

  async created() {},

  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;

        try {
          this.loading = true;

          const resp = await utils.http.post("/user/login", {
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
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>