<template>
  <v-container class="pt-0">
    <v-row>
      <v-col cols="12" class="text-center">
        <b>Transfer</b>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="6" class="text-left" style="align-self:center;">
        <b>Favorite</b>
      </v-col>
      <v-col cols="6" class="text-right">
        <v-btn class="white--text" color="#ee5566" @click="manage = true">
          EDIT
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="py-0">
        <Favorite @set="set" :list="list" />
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="6">
        <b>From</b>
        {{ this.accountID }}
      </v-col>
      <v-col cols="6" class="text-end">
        <b>Balance:</b>
        {{ this.balance }} {{ this.currency }}
      </v-col>
      <v-col cols="9">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="payeeID"
            label="To"
            type="number"
            counter
            maxlength="10"
            :rules="[
              v => v.length <= 10 || 'Max 10 characters',
              v => v.length == 10 || 'Invalid Account ID',
              v => check(v) === true || 'Account was not found',
              v => checkType(v) === true || 'Account type is not supported',
              v =>
                v !== accountID ||
                'Account ID can not be same with your account',
              v => checkType(v) === true || 'Account type is not supported'
            ]"
            required
          ></v-text-field>
          <v-text-field
            v-model="amount"
            label="Amount"
            type="number"
            :rules="[
              v =>
                parseFloat(v) <= parseFloat(this.balance) ||
                'Insufficient balance',
              v => parseFloat(v) % 10 == 0 || 'The amount must divisible by 10',
              v => parseFloat(v) > 0 || 'Insufficient amount',
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

      <v-col cols="3" class="text-center">
        <v-btn
          :disabled="!(valid && this.payeeID !== '' && this.amount !== '')"
          class="blue white--text"
          @click="submitted"
        >
          SUBMIT
        </v-btn>
        <v-dialog
          v-model="manage"
          class="manager"
          width="600px"
          content-class="manager"
        >
          <v-card style="min-height:600px !important;">
            <FavoriteManage :list="list" @back="back" @modify="modify" />
          </v-card>
        </v-dialog>
        <v-dialog v-model="submit" width="400px">
          <v-card>
            <TranferSubmitted
              :accountID="accountID"
              :payeeID="payeeID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :payer="payer"
              :balance="balance"
              @tranfer="Tranfer"
              @cancel="cancel"
            />
          </v-card>
        </v-dialog>
        <v-dialog v-model="confirm" width="400px">
          <v-card>
            <TranferConfirmed
              :accountID="accountID"
              :payeeID="payeeID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :payer="payer"
              :balance="balance"
              :update="update"
              @finish="finish"
            />
          </v-card>
        </v-dialog>
        <v-dialog v-model="errored" width="400px">
          <v-card>
            <v-row class="text-center errored">
              <v-col cols="12">
                <h1 class="red--text">
                  <b>ERROR</b>
                </h1>
              </v-col>
              <v-col cols="12">{{ this.error }}</v-col>
              <v-col cols="12">
                <v-btn @click="errored = false">BACK</v-btn>
              </v-col>
            </v-row>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Favorite from "./FavoriteList";
import FavoriteManage from "./FavoriteManage";
import Axios from "../../axios/axios";
import TranferSubmitted from "./TranferSubmitted";
import TranferConfirmed from "./TranferConfirmed";
export default {
  data() {
    return {
      valid: true,
      found: true,
      submit: false,
      confirm: false,
      manage: false,
      errored: false,
      error: "",
      amount: "",
      note: "",
      update: "",
      payeeID: "",
      payee: "",
      list: []
    };
  },
  mounted() {
    Axios.get("/GetFavoriteServlet?id=2").then(response => {
      this.list = response.data.content;
    });
  },
  props: {
    accountID: String,
    payer: String,
    accountType: String,
    balance: String,
    currency: String,
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
    TranferSubmitted,
    TranferConfirmed,
    FavoriteManage,
    Favorite
  },
  methods: {
    back() {
      this.manage = false;
    },
    set(id) {
      this.payeeID = id;
    },
    Tranfer() {
      if (this.amount !== null) {
        const Transaction = Object.assign(
          {},
          {
            owner_id: 2,
            account_id: this.accountID,
            payee_id: this.payeeID,
            amount: this.amount,
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
            this.submit = false;
            this.errored = true;
            this.error = error.response.data.error;
            console.log(error);
          });
      }
    },
    submitted() {
      var url = `/GetPayeeNameServlet?payee_id=${this.payeeID}`;
      Axios.get(url)
        .then(response => {
          this.payee = response.data.payeeName;
          this.found = true;
          this.submit = true;
        })
        .catch(error => {
          this.found = false;
          this.$refs.form.validate();
          this.found = true;
          console.log(error);
        });
    },
    cancel() {
      this.submit = false;
    },
    finish() {
      this.confirm = false;
      this.note = "";
      this.amount = "";
      this.valid = true;
      this.$emit("tranfer");
    },
    check(v) {
      var sum = 0;
      for (let i = 0; i < 9; i++) {
        sum += parseInt(v.charAt(i)) * (10 - i);
      }
      sum = sum % 11;
      sum = 11 - sum;
      sum = sum % 10;
      if (parseInt(sum) === parseInt(v.charAt(9)) && this.found) {
        return true;
      }
      return false;
    },
    checkType(v) {
      return (
        v.substring(2, 5) === "001" ||
        v.substring(2, 5) === "002" ||
        v.substring(2, 5) === this.accountID.substring(2, 5)
      );
    },
    modify(Favorite) {
      if (Favorite.method === "add") {
        Axios.post("/AddFavoriteServlet", null, { params: Favorite }).then(
          response => {
            this.list = response.data.content;
          }
        );
      } else if (Favorite.method === "edit") {
        Axios.post("/EditFavoriteServlet", null, { params: Favorite }).then(
          response => {
            this.list = response.data.content;
          }
        );
      } else if (Favorite.method === "delete") {
        Axios.post("/DeleteFavoriteServlet", null, { params: Favorite }).then(
          response => {
            this.list = response.data.content;
          }
        );
      }
    }
  }
};
</script>
<style>
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
.row {
  max-width: 100% !important;
  margin: 0 !important;
}
.manager {
  height: 540px;
}
.errored {
  font-size: 1.5em !important;
}
</style>
