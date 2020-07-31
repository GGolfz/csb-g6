<template>
  <v-dialog v-model="dialog" width="800px">
    <template v-slot:activator="{ on }">
      <v-hover v-slot:default="{ hover }">
        <v-btn
          color="teal"
          :outlined="hover ? false : true"
          @click="check = false"
          v-on="on"
        >
          View Detail
        </v-btn>
      </v-hover>
    </template>
    <v-card class="px-0">
      <v-card-title primary-title class="px-2">
        <v-row v-if="this.accountType === 'Fixed Deposit'">
          <v-col
            cols="6"
            :class="this.state === 'info' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'info';
              substate = 'info';
            "
          >
            General info
          </v-col>
          <v-col
            cols="6"
            :class="this.substate === 'deposit' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'transaction';
              substate = 'deposit';
            "
          >
            Deposit
          </v-col>
          <v-col cols="12" class="pt-0">
            <General
              v-if="this.state === 'info'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :fixed="this.fixed"
              :interest="this.interest"
              :limit="this.limit"
              @changelimit="changelimit"
            />
            <Deposit
              v-if="this.substate === 'deposit'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payee="this.ownerName"
              :remain="this.remain"
              :fixed="this.fixed"
              :alreadydep="this.alreadydep"
              @deposit="transaction"
            />
          </v-col>
        </v-row>
        <v-row v-if="this.accountType === 'Foreign'">
          <v-col
            cols="6"
            :class="this.state === 'info' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'info';
              substate = 'info';
            "
          >
            General info
          </v-col>
          <v-col
            cols="6"
            :class="this.state === 'transaction' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'transaction';
              substate = 'deposit';
            "
          >
            Transaction
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="4"
            :class="this.substate === 'deposit' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'deposit'"
          >
            Deposit
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="4"
            :class="this.substate === 'withdraw' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'withdraw'"
          >
            Withdraw
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="4"
            :class="this.substate === 'tranfer' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'tranfer'"
          >
            Transfer
          </v-col>
          <v-col cols="12" class="pt-0">
            <General
              v-if="this.substate === 'info'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :fixed="this.fixed"
              :interest="this.interest"
              :limit="this.limit"
              @changelimit="changelimit"
            />
            <Deposit
              v-if="this.substate === 'deposit'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payee="this.ownerName"
              :remain="this.remain"
              @deposit="transaction"
            />
            <Withdraw
              v-if="this.substate === 'withdraw'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payee="this.ownerName"
              :remain="this.remain"
              @withdraw="transaction"
            />
            <Tranfer
              v-if="this.substate === 'tranfer'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payer="this.ownerName"
              :remain="this.remain"
              @tranfer="transaction"
            />
          </v-col>
        </v-row>
        <v-row v-else>
          <v-col
            cols="6"
            :class="this.state === 'info' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'info';
              substate = 'info';
            "
          >
            General info
          </v-col>
          <v-col
            cols="6"
            :class="this.state === 'transaction' ? 'blue--text' : ''"
            class="text-center button"
            @click="
              state = 'transaction';
              substate = 'deposit';
            "
          >
            Transaction
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="3"
            :class="this.substate === 'deposit' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'deposit'"
          >
            Deposit
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="3"
            :class="this.substate === 'withdraw' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'withdraw'"
          >
            Withdraw
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="3"
            :class="this.substate === 'tranfer' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'tranfer'"
          >
            Transfer
          </v-col>
          <v-col
            v-if="this.state === 'transaction'"
            cols="3"
            :class="this.substate === 'payment' ? 'blue--text' : ''"
            class="text-center button"
            @click="substate = 'payment'"
          >
            Payment
          </v-col>
          <v-col cols="12" class="pt-0">
            <General
              v-if="this.substate === 'info'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :fixed="this.fixed"
              :interest="this.interest"
              :limit="this.limit"
              @changelimit="changelimit"
            />
            <Deposit
              v-if="this.substate === 'deposit'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payee="this.ownerName"
              :remain="this.remain"
              @deposit="transaction"
            />
            <Withdraw
              v-if="this.substate === 'withdraw'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payee="this.ownerName"
              :remain="this.remain"
              @withdraw="transaction"
            />
            <Tranfer
              v-if="this.substate === 'tranfer'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payer="this.ownerName"
              :remain="this.remain"
              @tranfer="transaction"
            />
            <Payment
              v-if="this.substate === 'payment'"
              :accountID="this.accountID"
              :accountType="this.accountType"
              :balance="this.balance"
              :currency="this.currency"
              :payer="this.ownerName"
              :remain="this.remain"
              @payment="transaction"
            />
          </v-col>
        </v-row>
      </v-card-title>
    </v-card>
  </v-dialog>
</template>
<script>
import General from "./General";
import Deposit from "./Deposit";
import Withdraw from "./Withdraw";
import Tranfer from "./Tranfer";
import Payment from "./Payment";
export default {
  name: "ViewDetail",
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    limit: String,
    interest: String,
    fixed: String,
    ownerName: String,
    on: Boolean,
    remain: String,
    alreadydep: String
  },
  data() {
    return {
      check: false,
      dialog: false,
      state: "info",
      substate: "info"
    };
  },
  components: {
    General,
    Deposit,
    Withdraw,
    Tranfer,
    Payment
  },
  methods: {
    transaction() {
      this.dialog = false;
      this.$router.push("/");
    },
    changelimit(val) {
      this.$emit("changelimit", val);
    }
  }
};
</script>
<style scoped>
.button {
  cursor: pointer;
}
.row {
  margin: 0;
}
</style>
