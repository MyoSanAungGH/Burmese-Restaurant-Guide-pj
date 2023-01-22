<template>
    <v-card 
      height="400"
      width="300"
      class="mx-auto">
      <v-navigation-drawer permanent width="400" style=" background-color: darkgrey;">
        <v-list style="color: cornsilk; background-color: darkslategray;">
          <v-list-item>
            <v-list-item-content>
            <v-list-item-title class="text-h6 mb-2" style="color: cornsilk;">
              Admin's action
            </v-list-item-title>
          </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-divider class="mb-8"></v-divider>

        <v-list class="mt-2">
          <v-list-item
            v-for="item in menuItemList"
            :key="item.title"
            link
            @click="goToRoute(item.path)"
            class="text-h9"
            style="font-family:Arial black;"
          >
            <v-list-item-icon class="mr-2">
              <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </v-card>
  </template>
  
<script>
export default {
  name: "admim_sidebar",

  data: () =>({
    menuItemList: [
      {
        title: "Admin",
        icon: "mdi-table",
        path: "/admin",
      },
      {
        title: "User List",
        icon: "mdi-account-supervisor ",
        path: "/admin/user_list",
      },
      {
        title: "Create Restaurant",
        icon: "mdi-movie",
        path: "/admin/create_restaurant",
      },
      {
        title: "Create Menu",
        icon: "mdi-movie",
        path: "/admin/create_menu",
      },
      
    ],
  }),

  created() {},

  methods: {
    goToRoute(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  },
};
</script>