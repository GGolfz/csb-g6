<template>
  <v-card>
    <v-row class="pa-6">
      <v-col cols="12">
        <v-row>
          <v-col cols="12" class="pa-12" justify="center" align="center">
            <v-img src="../../assets/success.png" width="80%" />
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" class="text-center body-1">
        Terminate Account Success
        <br />You can go to any branch to get your money.
      </v-col>
      <v-col cols="12" class="text-center body-1">
        AccountID: {{ this.info.accountID }}
      </v-col>
      <v-col cols="12" class="text-center body-1">
        Balance : {{ this.info.balance }}
      </v-col>
      <v-col cols="12" class="text-center pa-5">
        <v-hover v-slot:default="{ hover }">
          <router-link to="/">
            <v-btn color="success" :outlined="hover ? false : true">
              <h2 class="font-weight-black">DONE</h2>
            </v-btn>
          </router-link>
        </v-hover>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  props: {
    info: Object
  }
};
</script>
