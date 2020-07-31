<template>
  <v-row>
    <v-col cols="5" class="coll">{{ index }}.) {{ transaction.Date }}</v-col>
    <v-col cols="2" class="coll">{{ transaction.type }}</v-col>
    <v-col cols="5" class="coll">
      {{ transaction.amount }} {{ transaction.currency }}
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    transaction: Object,
    index: Number
  }
};
</script>

<style scoped>
.row {
  padding: 0;
}
.coll {
  padding-left: 0 !important;
  padding-top: 0 !important;
}
</style>
