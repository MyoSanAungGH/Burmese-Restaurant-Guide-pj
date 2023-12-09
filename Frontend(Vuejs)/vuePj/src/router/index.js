import Vue from "vue";
import VueRouter from 'vue-router'
import store from "../store";

import home from "../views/Home.vue";
import register from "../views/Register.vue";
import login from "../views/Login.vue";
import profile from "../views/Profile.vue";
import restaurant_details from "../views/Restaurant_details.vue";

import admin from "../views/Admin.vue";
import admin_user_list from "../views/Admin_user_list.vue";
import admin_create_restaurant from "../views/Admin_create_restaurant.vue";
import admin_create_menu from "../views/Admin_create_menu.vue";


Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "home",
    component: home,
  },

  {
    path: "/register",
    name: "register",
    component: register,
  },

  // {
  //   path: "/about",
  //   name: "about",
  //   component: About,
  // },

  {
    path: "/login",
    name: "login",
    component: login,
  },

  {
    path: "/restaurant_details/:id",
    name: "restaurant_details",
    component: restaurant_details,
    meta: {
      requiresAuth: true,
    }
  },

  {
    path: "/profile",
    name: "profile",
    component: profile,
    meta: {
      requiresAuth: true,
    }
  },

  {
    path: "/admin",
    name: "admin",
    component: admin,
    meta: {
      requiresAuth: true,
      requiresAdmin: true
    }
  },

  {
    path: "/admin/user_list",
    name: "admin_user_list",
    component: admin_user_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/create_restaurant",
    name: "admin_create_restaurant",
    component: admin_create_restaurant,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/create_menu",
    name: "admin_create_menu",
    component: admin_create_menu,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  let loginUser = router.app.$store.getters.loginUser;
  let isLogin = router.app.$store.getters.isLogin;

  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/" });
  }
  else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresAdmin == true &&
    loginUser.role != "admin"
  ) {
    next({ path: "/" });
  }
  else {
    next();
  }
});


export default router;
