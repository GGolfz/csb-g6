<template>
  <v-container class="ma-0 px-0 py-4 fill-width">
    <v-card
      class="font-weight-bold pa-4 font-ilatic"
      color="blue lighten-4"
      width="100%"
      shaped
    >
      <v-row align="center" class="mb-4">
        <v-col cols="6">{{ accountID }}</v-col>
        <v-col cols="6" class="text-end">{{ accountType }} Account</v-col>
      </v-row>
      <v-row align="center">
        <v-col cols="6">{{ balance }} {{ currency }}</v-col>
        <v-col cols="6" class="text-end">
          <ViewDetail
            :accountID="this.accountID"
            :accountType="this.accountType"
            :balance="this.balance"
            :currency="this.currency"
            :limit="this.limit"
            :fixed="this.fixed"
            :interest="this.interest"
            :ownerName="this.ownerName"
            :remain="this.remain"
            :alreadydep="this.alreadydep"
            @changelimit="changelimit"
          />
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import ViewDetail from "./ViewDetail";
export default {
  name: "ViewAccount",
  data() {
    return {
      remain: ""
    };
  },
  props: {
    accountID: String,
    accountType: String,
    balance: String,
    currency: String,
    limit: String,
    interest: String,
    fixed: String,
    ownerName: String,
    alreadydep: String,
    used: String
  },
  components: {
    ViewDetail
  },
  mounted() {
    this.remain = "" + (parseFloat(this.limit) - parseFloat(this.used));
  },
  methods: {
    changelimit(val) {
      this.$emit("changelimit", { accountID: this.accountID, val });
    }
  }
};
</script>
<style scoped>
.v-card--shaped {
  border-radius: 24px;
}
.fill-width {
  max-width: 100%;
}
.row {
  margin: 0;
}
</style>
