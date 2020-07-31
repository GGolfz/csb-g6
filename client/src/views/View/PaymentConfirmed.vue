<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="text-center , Confirm">
        <font color="success"><b>Completed</b></font>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row class="confirminfo">
      <v-col cols="12"></v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Account :</b> </v-col>
      <v-col cols="7">{{ this.accountID }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Payee :</b></v-col>
      <v-col cols="7"> {{ this.payee }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Amount :</b></v-col>
      <v-col cols="7">{{ this.amount }} {{ this.currency }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Fee : </b></v-col>
      <v-col cols="7">{{ this.fee }} {{ this.currency }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Balance :</b> </v-col>
      <v-col cols="7">{{ left }} {{ this.currency }}</v-col>
      <v-col cols="12"></v-col>
    </v-row>
    <v-row id="container">
      <v-col cols="5"></v-col>
      <v-img src="../../assets/success.png" max-width="80px"></v-img>
    </v-row>
    <v-row>
      <v-col cols="9"></v-col>
      <v-col cols="3">
        <v-btn text color="primary" @click="finish">Finish</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      left: this.balance - this.amount - this.fee
    };
  },
  props: {
    accountID: String,
    payee: String,
    amount: String,
    currency: String,
    fee: String,
    balance: String
  },
  methods: {
    finish() {
      this.$emit("finish");
    }
  }
};
</script>
