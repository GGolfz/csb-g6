<template>
  <v-container>
    <v-row>
      <v-col cols="12" class="text-center">
        <b>General Info</b>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="6">{{ this.accountID }}</v-col>
      <v-col cols="6" class="text-end"
        >{{ this.balance }} {{ this.currency }}</v-col
      >
      <v-col cols="12">{{ this.accountType }} Account</v-col>
      <v-col cols="12" v-if="parseFloat(interest) > 0">
        Interest Rate : {{ this.interest }} %
      </v-col>
      <v-col cols="12" v-if="parseFloat(fixed) > 0">
        Fixed Deposit : {{ this.fixed }} {{ this.currency }}
      </v-col>
      <v-col cols="12" v-if="parseFloat(limit) > 0" justify="center">
        Payment Limit : {{ this.limit }} {{ this.currency }} / Day
        <v-btn
          v-if="!edit"
          @click="edit = true"
          color="blue"
          :outlined="hover ? false : true"
          class="ml-3"
        >
          edit
        </v-btn>
        <v-row v-if="edit">
          <v-col cols="8">
            <v-text-field
              class="pa-0"
              v-model="paymentlimit"
              :rules="[
                v =>
                  parseFloat(v) <= 100000000 ||
                  'Payment limit can not over 100 millions'
              ]"
              @change="check"
            ></v-text-field>
          </v-col>
          <v-col cols="4" justify="center" align="center">
            <v-hover v-slot:default="{ hover }">
              <v-btn
                class="pa-0"
                @click="save"
                color="success"
                :outlined="hover ? false : true"
                :disabled="validate ? false : true"
              >
                save
              </v-btn>
            </v-hover>
          </v-col>
        </v-row>
      </v-col>
      <v-col
        cols="12"
        v-for="(transaction, index) in transactions"
        :key="index"
      >
        <Transaction :transaction="transaction" :index="index + 1" />
      </v-col>
      <v-col cols="12" class="text-center">
        <v-btn class="blue white--text">see more</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Axios from "../../axios/axios";
import Transaction from "./TransactionList";
export default {
  data() {
    return {
      transactions: [],
      edit: false,
      paymentlimit: 0,
      validate: true,
      hover: false
    };
  },
  components: {
    Transaction
  },
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    limit: String,
    interest: String,
    fixed: String
  },
  mounted() {
    this.paymentlimit = this.limit;

    Axios.get("/GetTransactionHistoryServlet", {
      params: { accountID: this.accountID }
    })
      .then(response => {
        this.transactions = response.data;
        this.transactions = this.transactions.slice(
          this.transactions.length - 5
        );
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    save() {
      this.edit = false;
      this.$emit("changelimit", this.paymentlimit);
      Axios.post("/ChangeLimitServlet", null, {
        params: { accountID: this.accountID, limit: this.paymentlimit }
      }).catch(error => {
        console.log(error);
      });
    },
    check() {
      if (this.paymentlimit > 100000000) {
        this.validate = false;
      } else {
        this.validate = true;
      }
    }
  }
};
</script>
