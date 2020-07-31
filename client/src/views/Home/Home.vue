<template>
  <v-container class="pa-12" fluid>
    <v-row class="pt-8" dense>
      <v-col cols="4" align="center">
        <router-link to="/View">
          <HomeButton
            msg="My Account"
            color="#42A5F5"
            :img="require('@/assets/button1.png')"
          />
        </router-link>
      </v-col>
      <v-col cols="4" align="center">
        <router-link to="/Open">
          <HomeButton
            msg="Open Account"
            color="#64B5F6"
            :img="require('@/assets/button2.png')"
          />
        </router-link>
      </v-col>
      <v-col cols="4" align="center">
        <router-link to="/Close">
          <HomeButton
            msg="Terminate"
            color="#90CAF9"
            :img="require('@/assets/button3.png')"
          />
        </router-link>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import HomeButton from "./HomeButton.vue";
export default {
  name: "Home",
  components: {
    HomeButton
  }
};
</script>
<style></style>
