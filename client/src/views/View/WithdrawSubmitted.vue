<template>
  <v-container class="myFont">
    <v-row>
      <v-col cols="12" class="text-center"><b>Confirmation</b></v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="12"></v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>From : </b></v-col>
      <v-col cols="5">{{ this.accountID }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="4"></v-col>
      <v-col cols="8"> {{ this.payee }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Amount :</b></v-col>
      <v-col cols="8">{{ this.amount }} {{ this.currency }}</v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3"><b>Branch :</b></v-col>
      <v-col cols="6">{{ this.branch.name }}</v-col>
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
    branch: Object
  },
  methods: {
    cancel() {
      this.$emit("cancel");
    },
    confirm() {
      this.$emit("withdraw");
    }
  }
};
</script>
