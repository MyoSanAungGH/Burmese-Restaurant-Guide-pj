<template>
  <v-app>
    <v-main>
      <heading></heading>
      <router-view class="mx-3 my-2" style="height:100%" />
      <footing></footing>
    </v-main>
  </v-app>
</template>

<script>
import heading from './components/heading.vue';
import footing from  './components/footing.vue';

export default {
  name: 'App',

  components: {
    heading,
    footing
  },

  data:()=>({}),
};
</script>
