<template>
  <v-container class="pt-0">
    <v-row>
      <v-col cols="12" class="text-center">
        <b>Withdraw</b>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="6">
        <b>Account ID:</b>
        {{ this.accountID }}
      </v-col>
      <v-col cols="6" class="text-end">
        <b>Balance:</b>
        {{ this.balance }} {{ this.currency }}
      </v-col>
      <v-col cols="3" align-self="center">
        <b>ATM branch:</b>
      </v-col>
      <v-col cols="4">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-select
            v-model="branch"
            :items="branches"
            item-text="name"
            item-value="id"
            persistent-hint
            return-object
            single-line
            :rules="[v => !!v || 'Branch is required']"
            required
          ></v-select>
        </v-form>
      </v-col>
      <v-col cols="9">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="amount"
            label="Withdraw Amount"
            placeholder="Please enter the amount"
            :rules="[
              v =>
                parseFloat(v) <= parseFloat(this.balance) ||
                'Insufficient balance',
              v => parseFloat(v) > 0 || 'Invalid amount',
              v => parseFloat(v) % 10 == 0 || 'The amount must divisible by 10',
              v =>
                v <= parseFloat(this.remain) ||
                'The amount is over payment limit'
            ]"
          ></v-text-field>
        </v-form>
      </v-col>
      <v-col cols="9">
        <v-text-field v-model="note" label="Note"></v-text-field>
      </v-col>
      <v-col cols="3" class="text-end">
        <v-btn
          :disabled="!(valid && this.amount !== '' && this.branch !== '')"
          class="blue white--text"
          @click="submit = !submit"
        >
          SUBMIT
        </v-btn>
        <v-dialog v-model="submit" width="400px">
          <v-card>
            <WithdrawSubmitted
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :balance="balance"
              :branch="branch"
              @withdraw="withdraw"
              @cancel="cancel"
            />
          </v-card>
        </v-dialog>
        <v-dialog v-model="confirm" width="400px">
          <v-card>
            <WithdrawConfirmed
              :accountID="accountID"
              :amount="amount"
              :currency="currency"
              :payee="payee"
              :balance="balance"
              :branch="branch"
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
import WithdrawSubmitted from "./WithdrawSubmitted";
import WithdrawConfirmed from "./WithdrawConfirmed";
export default {
  data() {
    return {
      update: "",
      valid: true,
      submit: false,
      confirm: false,
      amount: "",
      note: "",
      branch: "",
      branches: [
        { name: "CS BANK KMUTT", id: "01" },
        { name: "CS BANK KMITL", id: "02" },
        { name: "CS BANK KMUTNB", id: "03" },
        { name: "CS BANK Samut Prakarn", id: "04" },
        { name: "CS BANK Nontaburi", id: "05" },
        { name: "CS BANK Pathum Thani", id: "06" },
        { name: "CS BANK Phra Nakhon Si Ayutthaya", id: "07" },
        { name: "CS BANK Ang Thong", id: "08" },
        { name: "CS BANK Lop Buri", id: "09" },
        { name: "CS BANK Sing Buri", id: "10" },
        { name: "CS BANK Chai Nat", id: "11" },
        { name: "CS BANK Saraburi", id: "12" },
        { name: "CS BANK Chon Buri", id: "13" },
        { name: "CS BANK Rayong", id: "14" },
        { name: "CS BANK Chanthaburi", id: "15" },
        { name: "CS BANK Trat", id: "16" },
        { name: "CS BANK Chachoengsao", id: "17" },
        { name: "CS BANK Prachin Buri", id: "18" },
        { name: "CS BANK Nakhon Nayok", id: "19" },
        { name: "CS BANK Sa kaeo", id: "20" },
        { name: "CS BANK Nakhon Ratchasima", id: "21" },
        { name: "CS BANK Buri Ram", id: "22" },
        { name: "CS BANK Surin", id: "23" },
        { name: "CS BANK Si Sa Ket", id: "24" },
        { name: "CS BANK Ubon Ratchathani", id: "25" },
        { name: "CS BANK Yasothon", id: "26" },
        { name: "CS BANK Chaiyaphum", id: "27" },
        { name: "CS BANK Amnat Charoen", id: "28" },
        { name: "CS BANK Bueng Kan", id: "29" },
        { name: "CS BANK Nong Bua Lam Phu", id: "30" },
        { name: "CS BANK Khon Kaen", id: "31" },
        { name: "CS BANK Udon Thani", id: "32" },
        { name: "CS BANK Loei", id: "33" },
        { name: "CS BANK Nong Khai", id: "34" },
        { name: "CS BANK Maha Sarakham", id: "35" },
        { name: "CS BANK Roi Et", id: "36" },
        { name: "CS BANK Kalasin", id: "37" },
        { name: "CS BANK Sakon Nakhon", id: "38" },
        { name: "CS BANK Nakhon Phanom", id: "39" },
        { name: "CS BANK Mukdahan", id: "40" },
        { name: "CS BANK Chiang Mai", id: "41" },
        { name: "CS BANK Lamphun", id: "42" },
        { name: "CS BANK Lampang", id: "43" },
        { name: "CS BANK Uttaradit", id: "44" },
        { name: "CS BANK Phrae", id: "45" },
        { name: "CS BANK Nan", id: "46" },
        { name: "CS BANK Phayao", id: "47" },
        { name: "CS BANK Chiang Rai", id: "48" },
        { name: "CS BANK Mae Hong Son", id: "49" },
        { name: "CS BANK Nakhon Sawan", id: "50" },
        { name: "CS BANK Uthai Thani", id: "51" },
        { name: "CS BANK Kamphaeng Phet", id: "52" },
        { name: "CS BANK Tak ", id: "53" },
        { name: "CS BANK Sukhothai", id: "54" },
        { name: "CS BANK Phitsanulok", id: "55" },
        { name: "CS BANK Phichit", id: "56" },
        { name: "CS BANK Phetchabun", id: "57" },
        { name: "CS BANK Ratchaburi", id: "58" },
        { name: "CS BANK Kanchanaburi", id: "59" },
        { name: "CS BANK Suphan Buri", id: "60" },
        { name: "CS BANK Nakhon Pathom", id: "61" },
        { name: "CS BANK Samut Sakhon", id: "62" },
        { name: "CS BANK Samut Songkhram", id: "63" },
        { name: "CS BANK Phetchaburi", id: "64" },
        { name: "CS BANK Prachuap Khiri Khan", id: "65" },
        { name: "CS BANK Nakhon Si Thammarat", id: "66" },
        { name: "CS BANK Krabi", id: "67" },
        { name: "CS BANK Phang-nga", id: "68" },
        { name: "CS BANK Phuket", id: "69" },
        { name: "CS BANK Surat Thani", id: "70" },
        { name: "CS BANK Ranong", id: "71" },
        { name: "CS BANK Chumphon", id: "72" },
        { name: "CS BANK Songkhla", id: "73" },
        { name: "CS BANK Satun", id: "74" },
        { name: "CS BANK Trang", id: "75" },
        { name: "CS BANK Phatthalung", id: "76" },
        { name: "CS BANK Pattani", id: "77" },
        { name: "CS BANK Yala", id: "78" },
        { name: "CS BANK Narathiwat", id: "79" },
        { name: "CS BANK CU", id: "80" },
        { name: "CS BANK MU", id: "81" },
        { name: "CS BANK TU", id: "82" }
      ]
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
    WithdrawSubmitted,
    WithdrawConfirmed
  },
  watch: {
    confirm(now, prev) {
      if (now === false && prev === true) {
        this.finish();
      }
    }
  },
  methods: {
    withdraw() {
      if (this.amount !== null) {
        if (this.branch.id !== this.accountID.substring(0, 2)) {
          this.amount = "" + (parseFloat(this.amount) + 5);
        }
        const Transaction = Object.assign(
          {},
          {
            owner_id: 2,
            account_id: this.accountID,
            amount: this.amount,
            note: this.note,
            branch: this.branch.id
          }
        );
        Axios.post("/WithdrawServlet", null, { params: Transaction })
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
      this.branch = "";
      this.valid = true;
      this.$emit("withdraw");
    }
  }
};
</script>
