<template>
  <v-card>
    <v-row>
      <v-col cols="12">
        <v-row>
          <v-col cols="12" class="pa-12" justify="center" align="center">
            <v-img src="../../assets/success.png" width="80%" />
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" class="text-center pa-5 body-1">
        Your Account ID is {{ accountID }} <br />
        Account Type is {{ accountType }} Account
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
    accountID: String,
    accountType: String
  }
};
</script>
