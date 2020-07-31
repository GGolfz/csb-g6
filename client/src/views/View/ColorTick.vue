<template>
  <v-chip id="circle" class="ma-2" :color="color + 'C0'" @click="send"></v-chip>
</template>

<script>
export default {
  props: {
    color: String
  },
  methods: {
    send() {
      this.$emit("changecolor", this.color);
    }
  }
};
</script>

<style>
#circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
#circle :hover {
  cursor: pointer;
}
</style>
