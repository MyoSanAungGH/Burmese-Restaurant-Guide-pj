import router from '@/router';
import Vue from 'vue'
import { RouterLink } from 'vue-router';
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {},

  state: {
    loginUser:{},
    isLogin:false,
  },

  getters: {
    loginUser: (state) => {
      let loginUser = sessionStorage.getItem("loginUser");
      if(loginUser != null){
        state.loginUser = JSON.parse(loginUser);
        return JSON.parse(loginUser);
      }else{
        return state.loginUser;
      }
    },

    isLogin:(state) => {
      let isLogin= sessionStorage.getItem("isLogin");
      if(isLogin != null){
        state.isLogin = isLogin;
        return isLogin;
      }else{
        return state.isLogin;
      }
    }
  },

  mutations: {
    setLoginUser(state, user){
      sessionStorage.setItem("loginUser", JSON.stringify(user));
      sessionStorage.setItem("isLogin", true);
      state.loginUser= user;
      state.isLogin= true;
    },

    logout(state){
      sessionStorage.removeItem("loginUser");
      sessionStorage.removeItem("isLogin");
      state.loginUser={};
      state.isLogin= false;
    }
  },

  actions: {},
  
})
