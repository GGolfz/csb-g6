<template>
  <v-row justify="center">
    <v-col cols="2" class="pa-0 text-center">
      <Box :content="content" />
    </v-col>
    <v-col cols="6" style="align-self:center;">{{ content.name }}</v-col>
    <v-col cols="4" align="end" style="align-self:center;">
      <v-btn
        fab
        elevation="2"
        @click="edit"
        class="bot2"
        small
        style="margin-right:10%;"
      >
        ✎
      </v-btn>
      <v-btn
        fab
        elevation="2"
        @click="deleted"
        class="bot2"
        color="#ee5566"
        small
      >
        x
      </v-btn>
    </v-col>
  </v-row>
</template>

<script>
import Box from "./FavoriteBox";
export default {
  components: {
    Box
  },
  props: {
    content: Object
  },
  methods: {
    edit() {
      this.$emit("edit", this.content);
    },
    deleted() {
      this.$emit("delete", { favid: this.content.favid });
    }
  }
};
</script>

<style>
.bot2 .v-btn__content {
  font-size: 1.5em;
  margin-top: 2px;
}
</style>
