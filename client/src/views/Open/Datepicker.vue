<template>
  <v-menu
    v-model="fromDateMenu"
    :close-on-content-click="false"
    :nudge-right="40"
    transition="scale-transition"
    offset-y
    max-width="290px"
    min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
        label="Date of Birth *"
        readonly
        :value="fromDateDisp"
        v-on="on"
        :rules="[v => !!v || 'Date of Birth is required']"
        required
      ></v-text-field>
    </template>
    <v-date-picker
      locale="en-in"
      :min="minDate"
      :max="maxDate"
      v-model="birthdate"
      no-title
      @input="fromDateMenu = false"
    ></v-date-picker>
  </v-menu>
</template>
<script>
export default {
  data() {
    return {
      fromDateMenu: false,
      fromDateVal: null,
      minDate: "1900-01-01",
      maxDate: `${new Date().getUTCFullYear()}-${
        new Date().getMonth() >= 10
          ? new Date().getMonth() + 1
          : "0" + (new Date().getMonth() + 1)
      }-${
        new Date().getDate() >= 10
          ? new Date().getDate()
          : "0" + new Date().getDate()
      }`
    };
  },
  props: {
    birthdate: String
  },
  computed: {
    fromDateDisp() {
      this.$emit("date", this.birthdate);
      return this.birthdate;
    }
  }
};
</script>
