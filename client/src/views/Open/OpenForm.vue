<template>
  <v-stepper v-model="page">
    <v-stepper-header>
      <v-stepper-step :complete="page > 1" step="1">
        Terms &amp; Condition
      </v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="page > 2" step="2">
        Personal Information
      </v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="page > 3" step="3">
        Personal Information
      </v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="page > 4" step="4">
        Account Type Selection
      </v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <Page1 :page="page" @changePage="childMessageRecieved" />
      <Page2 :page="page" @send="getInfo" @changePage="childMessageRecieved" />
      <Page3
        :page="page"
        @send="getAddress"
        @changePage="childMessageRecieved"
      />
      <Page4
        :page="page"
        :info="info"
        :address="address"
        :branches="branch"
        @changePage="childMessageRecieved"
      />
    </v-stepper-items>
  </v-stepper>
</template>

<script>
import Axios from "../../axios/axios";
import Page1 from "./StepperPage1";
import Page2 from "./StepperPage2";
import Page3 from "./StepperPage3";
import Page4 from "./StepperPage4";
export default {
  data: function() {
    return {
      page: 1,
      info: null,
      address: null,
      branches: null,
      branch: null
    };
  },
  components: {
    Page1,
    Page2,
    Page3,
    Page4
  },
  methods: {
    childMessageRecieved(chpage) {
      this.page = chpage;
    },
    getInfo(info) {
      this.info = info;
    },
    getAddress(address) {
      this.address = address;
      this.showBranch();
    },
    showBranch() {
      const list = [];
        Array.from(this.branches).forEach(val => {
          if (this.address.province === val.province) {
            list.push(val);
          }
        });
      this.branch = list;
    }
  },
  mounted() {
    Axios.get("/GetBranchesServlet")
      .then(response => {
        this.branches = response.data.branch;
      })
      .catch(error => {
        console.log(error);
      });
  }
};
</script>

<style></style>
