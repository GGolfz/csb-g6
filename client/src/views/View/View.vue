<template>
  <v-container fluid>
    <v-row class="font-weight-bold content1" dense>
      <v-col cols="12" class="text-center title1">My Account</v-col>
      <v-col cols="12" class="account">
        Name : {{ info.name }} {{ info.lastname }}
      </v-col>
      <v-col cols="12" class="account">
        <Account
          v-for="account in info.accounts"
          :key="account.accountID"
          :accountID="account.accountID"
          :accountType="account.accountType"
          :limit="account.limit"
          :interest="account.interest"
          :fixed="account.fixed"
          :currency="account.currency"
          :balance="account.balance"
          :used="account.remain"
          :ownerName="info.name + ' ' + info.lastname"
          :alreadydep="account.alreadydep"
          @changelimit="changelimit"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Account from "./ViewAccount.vue";
import Axios from "../../axios/axios";
export default {
  name: "Views",
  components: {
    Account
  },
  data: function() {
    return {
      info: ""
    };
  },
  methods: {
    changelimit(val) {
      Array.from(this.info.accounts).forEach(account => {
        if (account.accountID === val.accountID) {
          account.limit = val.val;
        }
      });
    }
  },
  mounted() {
    Axios.get("/ShowAccountServlet?ownerID=2")
      .then(response => {
        this.info = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>
<style scoped>
.row {
  margin: 0 !important;
  height: auto;
}
.title1 {
  font-size: 1.6em;
  font-family: "Nunito", sans-serif;
  font-weight: 900;
}
.content1 {
  font-size: 1.4em;
  font-family: "Nunito", sans-serif;
  font-weight: 700;
}
.account {
  padding: 0 2%;
}
</style>
