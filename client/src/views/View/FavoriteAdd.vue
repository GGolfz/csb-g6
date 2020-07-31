<template>
  <v-row>
    <v-col cols="12" class="text-center title">ADD</v-col>
    <v-col cols="12" class="text-center pb-0">
      <Box :content="contents" size="big" />
    </v-col>
    <v-col cols="12" class="py-0">
      <Palette @change="set" />
    </v-col>
    <v-col cols="12" class="mt-0 pt-0">
      <v-row>
        <v-col cols="12">
          <v-text-field
            v-model="contents.id"
            label="Account ID"
            type="number"
            counter
            maxlength="10"
            :rules="[
              v => v.length <= 10 || 'Max 10 characters',
              v => v.length == 10 || 'Invalid Account ID',
              v => check(v) === true || 'Account was not found'
            ]"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="9">
          <v-text-field
            v-model="name"
            label="Name"
            counter
            maxlength="10"
            :rules="[
              v => v.length <= 10 || 'Max 10 characters',
              v => !!v || 'Name can not be blank'
            ]"
          ></v-text-field>
        </v-col>
        <v-col cols="3" align="center" style="align-self:center">
          <v-btn
            class="success"
            :disabled="!validate || name.length === 0"
            @click="add"
          >
            ADD
          </v-btn>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
import Box from "./FavoriteBox";
import Palette from "./Palette";
export default {
  components: {
    Box,
    Palette
  },
  data() {
    return {
      name: "",
      contents: {
        text: "",
        name: "",
        id: "",
        color: "#42A5F5"
      },
      validate: false
    };
  },
  methods: {
    set(color) {
      this.contents.color = color;
    },
    check(v) {
      var sum = 0;
      for (let i = 0; i < 9; i++) {
        sum += parseInt(v.charAt(i)) * (10 - i);
      }
      sum = sum % 11;
      sum = 11 - sum;
      sum = sum % 10;
      if (parseInt(sum) === parseInt(v.charAt(9))) {
        this.validate = true;
        return true;
      }
      this.validate = false;
      return false;
    },
    add() {
      this.$emit("addfav", this.contents);
    }
  },
  watch: {
    name() {
      this.contents.text = this.name.substring(0, 1);
      this.contents.name = this.name;
    }
  }
};
</script>

<style></style>
