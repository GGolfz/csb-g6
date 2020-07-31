<template>
  <v-app>
    <Nav />
    <v-content>
      <router-view />
      <Footer />
    </v-content>
  </v-app>
</template>
<style>
.theme--light.v-application {
  background: transparent !important;
  font-family: "Nunito", sans-serif;
}
.container--fluid {
  position: absolute;
  bottom: 64px;
  top: 0px;
  overflow-y: scroll;
}
footer {
  height: 64px !important;
}
a {
  text-decoration: none;
}
h3 {
  font-size: 1.2em;
  color: white;
}
</style>

<script>
import Nav from "./components/Nav";
import Footer from "./components/BankFooter";
export default {
  name: "App",
  components: {
    Nav,
    Footer
  }
};
</script>
