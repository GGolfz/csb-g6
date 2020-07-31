<template>
  <v-container class="myFont">
    <v-btn
      rounded
      class="blue white--text"
      width="150px"
      @click="dialog1 = !dialog1"
      >Withdraw</v-btn
    >
    <p></p>
    <v-btn
      rounded
      class="blue white--text"
      width="150px"
      @click="dialog2 = !dialog2"
      >Tranfer</v-btn
    >
    <p></p>
    <v-btn
      rounded
      class="blue white--text"
      width="150px"
      @click="dialog3 = !dialog3"
      >Payment</v-btn
    >
    <p></p>
    <v-btn
      rounded
      class="blue white--text"
      width="150px"
      @click="dialog4 = !dialog4"
      >Deposit
    </v-btn>
    <v-dialog v-model="dialog1" width="600px">
      <v-card>
        <Withdraw
          :accountID="accountID"
          :balance="balance"
          :currency="currency"
          :payer="payer"
          :payee="payee"
          :date="date"
          :time="time"
        />
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialog2" width="600px">
      <v-card>
        <Tranfer
          :accountID="accountID"
          :payeeID="payeeID"
          :balance="balance"
          :currency="currency"
          :payer="payer"
          :payee="payee"
          :date="date"
          :time="time"
        />
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialog3" width="600px">
      <v-card>
        <Payment
          :accountID="accountID"
          :payeeID="payeeID"
          :balance="balance"
          :currency="currency"
          :payer="payer"
          :payee="payee"
          :date="date"
          :time="time"
        />
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialog4" width="600px">
      <v-card>
        <Deposit
          :accountID="accountID"
          :balance="balance"
          :currency="currency"
          :payee="payee"
          :date="date"
          :time="time"
        />
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import Payment from "./Payment";
import Tranfer from "./Tranfer";
import Withdraw from "./Withdraw";
import Deposit from "./Deposit";

export default {
  name: "Home",
  data() {
    return {
      dialog1: false,
      dialog2: false,
      dialog3: false,
      dialog4: false,
      accountID: "1234567890",
      balance: 10000000,
      currency: "THB",
      payer: "Payer NaJa",
      payee: "Payee NaJa",
      date: "01 Jan 20",
      time: "00:00"
    };
  },
  components: {
    Payment,
    Tranfer,
    Withdraw,
    Deposit
  }
};
</script>

<style>
.myFont {
  font-family: "Nunito";
}
</style>
