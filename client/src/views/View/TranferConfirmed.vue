<template>
  <v-container class="myFont">
    <v-row>
      <v-col cols="12" class="text-center">
        <font color="success"><b>Completed</b></font>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Account : </b></v-col>
      <v-col cols="8">{{ this.accountID }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"></v-col>
      <v-col cols="8"> {{ this.payer }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Payee : </b></v-col>
      <v-col cols="8">{{ this.payeeID }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"></v-col>
      <v-col cols="8"> {{ this.payee }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Amount :</b></v-col>
      <v-col cols="8">{{ this.amount }} {{ this.currency }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Balance : </b></v-col>
      <v-col cols="8">
        {{ parseFloat(this.balance) - parseFloat(this.amount) }}
        {{ this.currency }}
      </v-col>
      <v-col cols="4"></v-col>
      <v-col cols="12" class="text-center">{{ this.update }}</v-col>
      <v-col cols="12"></v-col>
    </v-row>
    <v-row id="container">
      <v-img src="../../assets/success.png" max-width="40px"></v-img>
    </v-row>
    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="10" class="text-end">
        <v-btn text small color="primary" @click="finish">Finish</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    accountID: String,
    payeeID: String,
    payee: String,
    payer: String,
    amount: String,
    currency: String,
    balance: String,
    update: String
  },
  methods: {
    finish() {
      this.$emit("finish");
    }
  }
};
</script>
<style scoped>
#container {
  display: flex;
  justify-content: center;
}
</style>
