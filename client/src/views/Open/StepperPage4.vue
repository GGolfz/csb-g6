<template>
  <v-stepper-content :complete="page > 4" step="4">
    <v-card class="pa-5">
      <v-row>
        <v-col cols="3" v-for="(item, index) in type" :key="index">
          <v-btn
            v-bind:class="{
              red: accountType === item,
              blue: accountType !== item
            }"
            class="lighthen-1 white--text"
            width="160"
            height="50"
            @click="accountType = item"
          >
            {{ item }}<br />Account
          </v-btn>
        </v-col>
        <v-col cols="12">
          <v-row>
            <v-col cols="2" align-self="center">Branch :</v-col>
            <v-col cols="4" align-self="center">
              <v-select
                v-model="branch"
                label="Branch"
                :items="branches"
                item-text="name"
                item-value="id"
                :rules="[v => !!v || 'Branch is required']"
              />
            </v-col>
          </v-row>
          <v-row v-if="accountType === 'Fixed Deposit'">
            <v-col cols="2" align-self="center">Fixed Deposit :</v-col>
            <v-col cols="4" align-self="center">
              <v-select
                v-model="fixed"
                label="Fixed Deposit"
                :items="fixes"
                :rules="[v => !!v || 'Fixed Deposit is required']"
              />
            </v-col>
          </v-row>
          <v-row v-if="accountType === 'Fixed Deposit'">
            <v-col cols="2" align-self="center">Month :</v-col>
            <v-col cols="4" align-self="center">
              <v-select
                v-model="month"
                label="Month"
                :items="monthes"
                :rules="[v => !!v || 'Month is required']"
              />
            </v-col>
          </v-row>
          <v-row cols="12" v-else-if="accountType === 'Foreign'">
            <v-col cols="2" align-self="center">Currency :</v-col>
            <v-col cols="4" align-self="center">
              <v-select
                v-model="currency"
                label="Currency"
                :items="currencies"
                :rules="[v => !!v || 'Currency is required']"
              />
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-dialog v-model="dialog">
        <template v-slot:activator="{ on }">
          <v-row>
            <v-col class="text-start" cols="6">
              <v-btn color="grey" @click="backPage" justify="start">
                <h3>Back</h3>
              </v-btn>
            </v-col>
            <v-col class="text-end" cols="6">
              <v-btn
                color="green lighten-1"
                v-on="on"
                class="white--text"
                :disabled="
                  (accountType != null &&
                    branch != null &&
                    (accountType === 'Saving' || accountType === 'Current')) ||
                  (accountType === 'Foreign' && currency != null) ||
                  (accountType === 'Fixed Deposit' &&
                    fixed != null &&
                    month != null)
                    ? false
                    : true
                "
              >
                <h3>Submit</h3>
              </v-btn>
            </v-col>
          </v-row>
        </template>
        <v-card>
          <v-card-title>
            {{ accountType }} Account Terms &amp; Condition
          </v-card-title>
          <v-card-text>
            The deposit and withdrawal made into and from all accounts including
            the Checking Account, Savings Account, and Time Deposit Account
            shall be governed by these General Terms and Conditions:
            <br />
            <b>Deposit</b>
            <br />
            1. Bank's deposit slip shall be valid only if it is printed out from
            the Bank’s computer terminal.
            <br />2. If the Bank accepts cash deposit which cannot be verified
            immediately, such as the deposit through the Bank’s electronic
            equipment or ATM, the Bank shall have the right to accept the money
            subject to a later count. If the amount indicated on my/our slip
            differs from the amount counted by the Bank, the Bank’s figures in
            Bank’s deposit slip printed out from the Bank’s computer terminal
            shall be final and conclusive.
            <br />
            <br />
            <b>Cheque Deposit</b>
            <br />3. Cheque deposit shall be accepted for collection subject to
            final payment and I/we shall be entitled to withdraw the amount
            indicated on the cheque only when such amount has been collected and
            is credited to my/our account.
            <br />
            4. The Bank may refuse to accept or collect any cheque that the Bank
            finds invalid or doubtful or which is drawn in someone else's name.
            In the case of an endorsed cheque, I/we shall make an arrangement
            with the Bank for the confirmation of prior endorsement when the
            Bank so requests which shall include producing documentation to the
            Bank for the confirmation.
            <br />
            <br />
            <b>Cheque</b>
            <br />5. If I/we wish to obtain a new cheque book, I/we shall fill
            out the order form as specified by the Bank with the same signature
            registered with the Bank or I/we shall order a new cheque book as
            the CitiPhone Banking. I/We agree to pay the stamp duty at the rate
            specified by law. I/We also agree to pay the banking charges for
            issuing the new cheque book at the rate specified by the Bank and
            any other expenses in relation to the new cheque book.
            <br />
            6. In addition to the above Clause, I/we agree that the Bank shall
            issue a new cheque book and deliver it to me/us by registered mail
            immediately after the Bank’s computer checks that my/our cheque book
            has only 5 unused cheques.
            <br />7. I/We agree to allow the Bank to debit stamp duty, cheque
            book fee and other expenses involving the issuance of cheque from
            any of my accounts opened with the Bank.
            <br />
            8. If I/we order a cheque book through the CitiPhone Banking, I/we
            agree that the Bank will send the ordered cheque book to me/us by
            registered mail to my/our latest address given to the Bank. If I/we
            order a cheque book from the Bank’s officer at the Bank’s office,
            I/we agree that I/we must receive the ordered cheque book at the
            Bank’s office within the banking hours on the same day on which the
            order is given to the Bank’s officer. Otherwise, the Bank will send
            the ordered cheque book to me/us by registered mail to my/our latest
            address given to the Bank. I/We agree that the Bank shall not be
            responsible for any damage which may be incurred by me/us from the
            Bank’s sending the cheque book by registered mail to my/our address
            given to the Bank
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue ligthen-1" text @click="submit">
              Agree and Submit
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="success" height="600px" width="400px">
        <Successpage
          :accountID="fininfo.accountID"
          :accountType="fininfo.accountType"
        />
      </v-dialog>
    </v-card>
  </v-stepper-content>
</template>

<script>
import Axios from "../../axios/axios";
import Successpage from "./Success";
export default {
  data: function() {
    return {
      accountType: null,
      dialog: false,
      success: false,
      type: ["Current", "Saving", "Fixed Deposit", "Foreign"],
      fixes: [2000, 4000, 8000],
      monthes: [3, 6, 12],
      branch: null,
      fixed: null,
      month: null,
      currency: null,
      fininfo: { accountID: "", accountType: "" },
      currencies: [
        "CAD",
        "HKD",
        "ISK",
        "PHP",
        "DKK",
        "HUF",
        "CZK",
        "AUD",
        "RON",
        "SEK",
        "IDR",
        "INR",
        "BRL",
        "RUB",
        "HRK",
        "JPY",
        "CHF",
        "SGD",
        "PLN",
        "BGN",
        "TRY",
        "CNY",
        "NOK",
        "NZD",
        "ZAR",
        "USD",
        "USD",
        "MXN",
        "ILS",
        "GBP",
        "KRW",
        "MYR",
        "EUR"
      ]
    };
  },
  props: {
    page: Number,
    info: Object,
    address: Object,
    branches: Array
  },
  components: {
    Successpage
  },
  methods: {
    backPage() {
      this.$emit("changePage", 3);
    },
    submit() {
      const openInfo = Object.assign({}, this.info, this.address, {
        type: this.accountType,
        branch: this.branch,
        currency: this.currency,
        fixed: this.fixed,
        month: this.month
      });
      Axios.post("/OpenAccountServlet", null, { params: openInfo })
        .then(response => {
          this.fininfo = response.data;
          this.dialog = false;
          this.success = true;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
