<template>
  <v-card>
    <v-list disabled>
      <v-list-item-group color="primary">
        <v-list-item v-for="(item, index) in accountDescription" :key="index">
          <v-list-item-content>
            <h4>
              {{ item.type }} Account
              <br />
            </h4>
            <p>{{ item.description }}</p>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "Description",
  data: function() {
    return {
      accountDescription: [
        {
          type: "Current",
          description:
            "is a country's trade balance plus net income and direct payments. The trade balance is a country's imports and exports of goods and services. The current account also measures international transfers of capital."
        },
        {
          type: "Saving",
          description:
            "is a deposit account held at a retail bank that pays interest but cannot be used directly as money in the narrow sense of a medium of exchange. These accounts let customers set aside a portion of their liquid assets while earning a monetary return."
        },
        {
          type: "Fixed",
          description:
            "is a financial instrument provided by banks or NBFCs which provides investors a higher rate of interest than a regular savings account, until the given maturity date."
        },
        {
          type: "Foreign",
          description:
            "is a transactional account denominated in a currency other than the home currency and can be maintained by a bank in the home country or a bank in another country. Foreign currency accounts are generally not covered by national deposit insurance schemes."
        }
      ]
    };
  }
};
</script>

<style>
.v-list-item__content {
  padding-top: 15px;
  padding-bottom: 0px;
}
</style>
