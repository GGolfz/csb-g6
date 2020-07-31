<template>
  <v-container fluid>
    <v-row>
      <v-col cols="4">
        <Description />
      </v-col>
      <v-col cols="8" class="text-center">
        <Form />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Description from "./AccountDescription";
import Form from "./OpenForm";
export default {
  data: () => ({}),
  components: {
    Description,
    Form
  },
  methods: {
    processFile(event) {
      this.pic = event.target.files[0];
    }
  }
};
</script>

<style scoped>
.row {
  margin: 0 !important;
  align-items: center;
  height: 100%;
}
h1 {
  font-size: 1.6em;
}

h2 {
  font-size: 1.4em;
}

h3 {
  font-size: 1.2em;
  color: white;
}

h5 {
  font-size: 0.6em;
}
</style>
