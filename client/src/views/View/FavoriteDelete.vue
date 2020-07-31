<template>
  <v-row class="text-center">
    <v-col cols="12">
      <b>Confirm to delete this favorite</b>
    </v-col>
    <v-col cols="12">
      This favorite will no longer be in the favorite list
    </v-col>
    <v-col cols="12">
      <v-row justify="space-around">
        <v-col cols="5">
          <v-btn @click="can">No, keep it</v-btn>
        </v-col>
        <v-col cols="5">
          <v-btn @click="del">Yes, delete</v-btn>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    content: Object
  },
  methods: {
    can() {
      this.$emit("cancel");
    },
    del() {
      this.$emit("deletefav", this.content);
    }
  }
};
</script>

<style></style>
