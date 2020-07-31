<template>
  <v-card
    class="py-8 text-center"
    width="250px"
    :color="color"
    elevation="5"
    shaped
  >
    <v-img :src="img" class="white--text align-end" height="200px"></v-img>
    <v-hover v-slot:default="{ hover }">
      <v-btn
        color="white"
        :outlined="hover ? false : true"
        class="mt-5 content1"
      >
        {{ msg }}
      </v-btn>
    </v-hover>
  </v-card>
</template>

<script>
export default {
  props: {
    msg: String,
    color: String,
    img: String
  }
};
</script>

<style scoped>
.content1 {
  font-size: 1.2em;
  font-family: "Nunito", sans-serif;
  font-weight: 500;
}
.v-card--shaped {
  border-radius: 24px;
}
</style>
