<template>
  <v-dialog v-model="dialog" width="600px">
    <template v-slot:activator="{ on }">
      <v-hover v-slot:default="{ hover }">
        <v-btn
          color="error"
          :outlined="hover ? false : true"
          @click="check = false"
          v-on="on"
        >
          Terminate Account
        </v-btn>
      </v-hover>
    </template>
    <v-card>
      <v-card-title primary-title>
        <v-row dense justify="center">
          <v-col cols="4" class="text-start mb-2">{{ accountID }}</v-col>
          <v-col cols="4" class="text-center">{{ accountType }} Account</v-col>
          <v-col cols="4" class="text-end">{{ balance }} {{ currency }}</v-col>
          <v-col cols="10" class="text-start">
            <v-checkbox
              v-model="check"
              label="I agree that I have to go to the bank branch with ID Card for getting my money"
            />
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="12" class="text-center">
            <v-hover v-slot:default="{ hover }">
              <v-btn
                color="info"
                :outlined="hover ? false : true"
                @click="dialog = false"
                class="cancel"
              >
                Cancel
              </v-btn>
            </v-hover>
            <v-hover v-slot:default="{ hover }">
              <v-btn
                color="error"
                :disabled="check ? false : true"
                :outlined="hover ? false : true"
                @click="close"
              >
                Terminate Account
              </v-btn>
            </v-hover>
          </v-col>
        </v-row>
      </v-card-title>
    </v-card>
  </v-dialog>
</template>
<script>
import Axios from "../../axios/axios";
import BankAxios from "../../axios/bankaxios";
export default {
  name: "Popup",
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    limit: String,
    interest: String,
    fixed: String,
    on: Boolean
  },
  data() {
    return {
      check: false,
      dialog: false,
      finish: false,
      info: null
    };
  },
  methods: {
    close() {
      Axios.post("/CloseAccountServlet", null, {
        params: {
          accountID: this.accountID
        }
      })
        .then(response => {
          this.info = response.data;
          BankAxios.post("/MoneyManagementServlet", null, {
            params: { value: this.info.interest, code: "013" }
          })
            .then(() => {
              this.dialog = false;
              this.$emit("closed", this.info);
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>
<style>
.cancel {
  margin-right: 5% !important;
}
</style>
