<template>
  <v-container fluid>
    <v-row class="font-weight-bold content1" dense>
      <v-col cols="12" class="text-center title1">
        Terminate Bank Account System
      </v-col>
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
          @close="close"
        />
      </v-col>
    </v-row>
    <v-dialog v-model="success" width="400px" height="600px">
      <CloseSuccess :info="closed" />
    </v-dialog>
  </v-container>
</template>

<script>
import Account from "./CloseAccount.vue";
import CloseSuccess from "./CloseSuccess.vue";
import Axios from "../../axios/axios";
export default {
  name: "Close",
  components: {
    Account,
    CloseSuccess
  },
  data: function() {
    return {
      info: "",
      closed: null,
      success: false
    };
  },
  mounted() {
    Axios.get("/ShowAccountServlet?ownerID=2")
      .then(response => {
        this.info = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    close(val) {
      this.closed = val;
      this.success = true;
    }
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
