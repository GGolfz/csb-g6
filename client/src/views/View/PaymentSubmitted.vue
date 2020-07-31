<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="text-center , Confirm"><b>Confirmation</b></v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row class="confirminfo">
      <v-col cols="12"></v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>From :</b> </v-col>
      <v-col cols="7">{{ this.accountID }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>To :</b></v-col>
      <v-col cols="7"> {{ this.payee }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Amount :</b></v-col>
      <v-col cols="7">{{ this.amount }} {{ this.currency }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Fee : </b></v-col>
      <v-col cols="7">{{ this.fee }} {{ this.currency }}</v-col>
      <v-col cols="12"></v-col>
    </v-row>
    <v-row>
      <v-col class="text-center">
        <v-btn class="grey white--text" @click="cancel">CANCEL</v-btn>
      </v-col>
      <v-col class="text-center">
        <v-btn class="blue white--text" @click="confirm">CONFIRM</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    accountID: String,
    payee: String,
    amount: String,
    currency: String,
    balance: String,
    fee: String
  },
  methods: {
    cancel() {
      this.$emit("cancel");
    },
    confirm() {
      this.$emit("payment");
    }
  }
};
</script>
