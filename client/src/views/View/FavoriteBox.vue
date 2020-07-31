<template>
  <v-hover v-slot:default="{ hover }">
    <v-chip
      id="chip"
      class="ma-2"
      :class="size === 'big' ? 'big' : ''"
      text-color="white"
      :color="!hover ? `${content.color}C0` : `${content.color}FF`"
      @click="send"
    >
      {{ content.text }}
    </v-chip>
  </v-hover>
</template>

<script>
export default {
  props: {
    content: Object,
    size: String
  },
  methods: {
    send() {
      this.$emit("send", this.content.id);
    }
  }
};
</script>

<style>
#chip {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin: 1%;
  font-size: 1.2em;
  cursor: pointer;
  transition: 0.5s;
}
.big {
  width: 100px !important;
  height: 100px !important;
  font-size: 2.5em !important;
}
.v-chip__content {
  width: 100% !important;
  justify-content: center !important;
  text-align: center !important;
}
</style>
