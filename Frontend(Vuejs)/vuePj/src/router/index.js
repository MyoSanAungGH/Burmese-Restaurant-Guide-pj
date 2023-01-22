import Vue from "vue";
import VueRouter from 'vue-router'
import store from "../store";


import Home from "../views/Home.vue";
import Register from "../views/Register.vue";
import Login from "../views/Login.vue"
import Restaurant_details from "../views/Restaurant_details.vue";
import Profile from "../views/Profile.vue"

import Admin from "../views/Admin.vue";
import Admin_create_restaurant from "../views/Admin_create_restaurant.vue";
import Admim_user_list from "../views/Admin_user_list.vue"
import Admin_create_menu from "../views/Admin_create_menu.vue"

Vue.use(VueRouter);

const routes = [

  {
    path: "/",
    name: "home",
    component: Home,
  },

  {
    path: "/register",
    name: "register",
    component: Register,
  },

  {
    path: "/login",
    name: "login",
    component: Login,
  },

  {
    path: "/restaurant_details/:id",
    name: "restaurant_details",
    component: Restaurant_details,
    meta: {
      requiresAuth: true,
    }
  },

  {
    path: "/profile",
    name: "profile",
    component: Profile,
    meta: {
      requiresAuth: true,
    }
  },

  {
    path: "/admin",
    name: "admin",
    component: Admin,
    meta: {
      requiresAuth: true,
      requiresAdmin: true
    }
  },

  {
    path: "/admin/user_list",
    name: "admin_user_list",
    component: Admim_user_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/create_restaurant",
    name: "admin_create_restaurant",
    component: Admin_create_restaurant,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },

  {
    path: "/admin/create_menu",
    name: "admin_create_menu",
    component: Admin_create_menu,
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
