<template>
  <v-row>
    <v-col cols="12" class="text-center">
      <Picker
        v-for="(color, index) in colors"
        :key="index"
        :color="color"
        @changecolor="change"
      />
    </v-col>
  </v-row>
</template>

<script>
import Picker from "./ColorTick";
export default {
  data() {
    return {
      colors: [
        "#C7A8DA",
        "#FFA3A5",
        "#FDE3A6",
        "#B2D570",
        "#4CDDB4",
        "#73E8F2",
        "#42A5F5",
        "#97CFDE"
      ]
    };
  },
  components: {
    Picker
  },
  methods: {
    change(color) {
      this.$emit("change", color);
    }
  }
};
</script>

<style></style>
