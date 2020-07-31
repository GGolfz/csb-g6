<template>
  <v-container class="pt-0">
    <v-row>
      <v-col cols="12" class="text-center"><b>Payment</b></v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="12"></v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000001'"
          >Electricity Bill</v-btn
        >
      </v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000002'"
          >Water Bill</v-btn
        >
      </v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000003'"
          >Phone Bill</v-btn
        >
      </v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000004'"
          >Internet bill</v-btn
        >
      </v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000005'"
          >TAX</v-btn
        >
      </v-col>
      <v-col cols="4" class="text-center">
        <v-btn class="blue white--text" block @click="payee = '0000000006'"
          >Creditcard Bill</v-btn
        > </v-col
      ><v-col></v-col>
    </v-row>
    <v-divider></v-divider>

    <v-row class="confirminfo">
      <v-col cols="12"></v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>From :</b> </v-col>
      <v-col cols="7">{{ this.accountID }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Payee ID :</b></v-col>
      <v-col cols="7"> {{ this.payee }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3" align-self="center"><b>Amount :</b></v-col>
      <v-col cols="5">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="amount"
            label="Payment Amount"
            placeholder="Please enter the amount"
            :rules="[
              v =>
                parseFloat(v) <= parseFloat(this.balance) ||
                'Insufficient balance',
              v => parseFloat(v) > 0 || 'Invalid amount',
              v =>
                v <= parseFloat(this.remain) ||
                'The amount is over payment limit'
            ]"
            required
          ></v-text-field>
        </v-form>
      </v-col>
      <v-col cols="2"></v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3"><b>Fee : </b></v-col>
      <v-col cols="7">{{ this.fee }}</v-col>
      <v-col cols="2"></v-col>
      <v-col cols="9">
        <v-text-field v-model="note" label="Note"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12"></v-col>
      <v-col cols="5"></v-col>
      <v-col cols="2" class="text-end">
        <v-btn
          :disabled="!(valid && this.payee !== '')"
          class="blue white--text"
          @click="submit = !submit"
          >SUBMIT</v-btn
        >
        <v-dialog v-model="submit" width="500px">
          <v-card>
            <PaymentSubmitted
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :balance="balance"
              :fee="fee"
              @payment="Payment"
              @cancel="cancel"
            />
          </v-card>
        </v-dialog>
        <v-dialog persistent v-model="confirm" width="500px">
          <v-card>
            <PaymentConfirmed
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :fee="fee"
              :balance="balance"
              @finish="finish"
            />
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Axios from "../../axios/axios";
import PaymentConfirmed from "./PaymentConfirmed";
import PaymentSubmitted from "./PaymentSubmitted";
export default {
  data() {
    return {
      amount: "",
      note: "",
      payee: "",
      fee: "10",
      submit: false,
      confirm: false,
      valid: true
    };
  },
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    payer: String,
    remain: String
  },
  watch: {
    confirm(now, prev) {
      if (now === false && prev === true) {
        this.finish();
      }
    }
  },
  components: {
    PaymentSubmitted,
    PaymentConfirmed
  },
  methods: {
    Payment() {
      if (this.amount !== null) {
        const Transaction = Object.assign(
          {},
          {
            owner_id: 2,
            account_id: this.accountID,
            payee_id: this.payee,
            amount: parseFloat(this.amount) + parseFloat(this.fee),
            note: this.note
          }
        );
        Axios.post("/TransferServlet", null, { params: Transaction })
          .then(response => {
            this.submit = false;
            this.update = response.data.update;
            this.confirm = true;
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    cancel() {
      this.submit = false;
    },
    finish() {
      this.confirm = false;
      this.note = "";
      this.amount = "";
      this.valid = true;
      this.$emit("payment");
    }
  }
};
</script>
<style></style>
