<template>
  <div class="box" style="overflow:auto hidden;">
    <div class="wrap">
      <div class="row1" style="overflow: auto hidden;">
        <div style="display:flex; flex-direction: row;" width="300px">
          <div
            style="height:140px;"
            class="text-center"
            v-for="(content, index) in list"
            :key="index"
          >
            <Box :content="content" @send="send" />
            <br />
            <v-label
              >{{ content.name.substring(0, 5) }}<br />{{
                content.name.substring(5)
              }}</v-label
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Box from "./FavoriteBox";
export default {
  props: {
    list: Array
  },
  components: {
    Box
  },
  methods: {
    send(id) {
      this.$emit("set", id);
    }
  }
};
</script>

<style>
.box {
  display: block;
  overflow-y: hidden;
}
.wrap {
  height: 150px;
  overflow-y: hidden;
  outline: 0;
}
.row1 {
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -webkit-flex-direction: row;
  -ms-flex-direction: row;
  flex-direction: row;
  -ms-overflow-style: -ms-autohiding-scrollbar;
  -webkit-overflow-scrolling: touch;
  height: 150px;
}
</style>
