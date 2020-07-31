<template>
  <v-row class="pa-5">
    <v-col cols="2" class="text-center">
      <v-btn
        fab
        elevation="2"
        class="back blue lighten-2 white--text"
        small
        outlined
        @click="back"
      >
        ◀
      </v-btn>
    </v-col>
    <v-col cols="8" class="text-center title">
      <b>Favorite</b>
    </v-col>
    <v-col cols="2" class="text-center">
      <v-btn
        fab
        elevation="2"
        class="bot blue lighten-2 white--text"
        small
        @click="add"
      >
        +
      </v-btn>
    </v-col>
    <v-col cols="12" v-for="(content, index) in list" :key="index">
      <Text1 :content="content" @edit="edit" @delete="vdelete" />
    </v-col>
    <v-dialog v-model="added" width="500px">
      <v-card>
        <FavoriteAdd @addfav="addfav" />
      </v-card>
    </v-dialog>
    <v-dialog v-model="edited" width="500px">
      <v-card>
        <FavoriteEdit @editfav="editfav" :content="datas" />
      </v-card>
    </v-dialog>
    <v-dialog v-model="deleted" width="400px">
      <v-card>
        <FavoriteDelete
          @deletefav="deletefav"
          @cancel="cancel"
          :content="datas"
        />
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import Text1 from "./FavoriteText";
import FavoriteAdd from "./FavoriteAdd";
import FavoriteEdit from "./FavoriteEdit";
import FavoriteDelete from "./FavoriteDelete";
export default {
  data() {
    return {
      added: false,
      edited: false,
      deleted: false,
      datas: {
        favid: "",
        text: "",
        name: "",
        id: "",
        color: "#42A5F5"
      }
    };
  },
  props: {
    list: Array
  },
  components: {
    Text1,
    FavoriteAdd,
    FavoriteEdit,
    FavoriteDelete
  },
  methods: {
    add() {
      this.datas = {
        favid: "",
        text: "",
        name: "",
        id: "",
        color: "#42A5F5"
      };
      this.added = true;
    },
    edit(data) {
      this.datas = data;
      this.edited = true;
    },
    vdelete(data) {
      this.datas = data;
      this.deleted = true;
    },
    cancel() {
      this.deleted = false;
    },
    addfav(content) {
      this.added = false;
      var Favorite = Object.assign({ ownerID: 2 }, content, { method: "add" });
      this.$emit("modify", Favorite);
    },
    editfav(content) {
      this.edited = false;
      var Favorite = Object.assign({ ownerID: 2 }, content, { method: "edit" });
      this.$emit("modify", Favorite);
    },
    deletefav(content) {
      this.deleted = false;
      var Favorite = Object.assign({ ownerID: 2 }, content, {
        method: "delete"
      });
      this.$emit("modify", Favorite);
    },
    back() {
      this.$emit("back");
    }
  }
};
</script>

<style>
.bot .v-btn__content {
  font-size: 2.3em;
}
.back .v-btn__content {
  font-size: 1.8em !important;
  margin-left: -3px;
}
</style>
