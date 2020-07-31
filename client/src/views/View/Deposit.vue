<template>
  <v-container class="pt-0">
    <v-row>
      <v-col cols="12" class="text-center"><b>Deposit</b></v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="6"><b>Account ID: </b>{{ this.accountID }}</v-col>
      <v-col cols="6" class="text-end"
        ><b>Balance: </b>{{ this.balance }} {{ this.currency }}</v-col
      >
      <v-col cols="9">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="amount"
            label="Deposit Amount"
            placeholder="Please enter the amount"
            :rules="[
              v =>
                parseFloat(v) >= parseFloat(10) ||
                'Minimum 10 ' + this.currency,
              v => parseFloat(v) % 10 == 0 || 'The amount must divisible by 10',
              v =>
                v <= parseFloat(this.remain) ||
                'The amount is over payment limit'
            ]"
            required
          ></v-text-field>
        </v-form>
      </v-col>
      <v-col cols="9">
        <v-text-field v-model="note" label="Note"></v-text-field>
      </v-col>
      <v-col cols="3" class="text-end">
        <v-btn
          :disabled="!(valid && this.amount !== '')"
          class="blue white--text"
          @click="submit = !submit"
          >SUBMIT</v-btn
        >
        <v-dialog v-model="submit" width="400px">
          <v-card>
            <DepositSubmitted
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :balance="balance"
              @deposit="Deposit"
              @cancel="cancel"
            />
          </v-card>
        </v-dialog>
        <v-dialog v-model="confirm" width="400px">
          <v-card>
            <DepositConfirmed
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :balance="balance"
              :update="update"
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
import DepositSubmitted from "./DepositSubmitted";
import DepositConfirmed from "./DepositConfirmed";
export default {
  data() {
    return {
      valid: true,
      submit: false,
      confirm: false,
      amount: "",
      note: "",
      branch: null,
      update: ""
    };
  },
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    payee: String,
    remain: String
  },
  components: {
    DepositSubmitted,
    DepositConfirmed
  },
  watch: {
    confirm(now, prev) {
      if (now === false && prev === true) {
        this.finish();
      }
    }
  },
  methods: {
    Deposit() {
      if (this.amount !== null) {
        const Transaction = Object.assign(
          {},
          {
            owner_id: 2,
            account_id: this.accountID,
            amount: this.amount,
            note: this.note,
            branch: this.branch
          }
        );
        Axios.post("/DepositServlet", null, { params: Transaction })
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
      this.$emit("deposit");
    }
  }
};
</script>
