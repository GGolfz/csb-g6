<template>
  <v-stepper-content step="1">
    <v-card class="pa-5">
      <v-row>
        <h1>Opening Account Terms &amp; Condition</h1>
      </v-row>
      <v-row class="text-left">
        The deposit and withdrawal made into and from all accounts including the
        Checking Account, Savings Account, and Time Deposit Account shall be
        governed by CSB General Terms &amp; Conditions
      </v-row>
      <v-spacer></v-spacer>
      <v-row>
        <v-checkbox
          label="Accept Terms &amp; Condition"
          v-model="condition"
        ></v-checkbox>
      </v-row>
      <v-row>
        <v-alert type="info">Please Read Carefully</v-alert>
        <v-spacer></v-spacer>
        <v-btn color="grey" href="/">
          <h3>Cancel</h3>
        </v-btn>
        <v-btn
          @click="changePage"
          :disabled="condition ? false : true"
          class="green lighten-1"
        >
          <h3>Next</h3>
        </v-btn>
      </v-row>
    </v-card>
  </v-stepper-content>
</template>
<style scoped>
.v-btn {
  margin: 1%;
}
</style>
<script>
export default {
  data: function() {
    return {
      condition: false
    };
  },
  props: {
    page: Number
  },
  methods: {
    changePage() {
      this.$emit("changePage", 2);
    }
  }
};
</script>
