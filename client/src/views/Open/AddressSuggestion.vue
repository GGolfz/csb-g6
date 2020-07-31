<template>
  <div class="suggestor">
    <v-autocomplete
      @input="send"
      v-model="model"
      :items="list"
      :search-input.sync="search"
      :label="address + '*'"
      item-text="description"
      hide-no-data
      return-object
      :rules="[v => !!v || `${this.address} is required`]"
    >
      <template v-slot:selection class="text1">
        <div v-if="address === 'Sub District'">{{ model.subDistrict }}</div>
        <div v-else-if="address === 'District'">{{ model.district }}</div>
        <div v-else-if="address === 'Province'">{{ model.province }}</div>
        <div v-else-if="address === 'Zipcode'">{{ model.Zipcode }}</div>
      </template>
    </v-autocomplete>
  </div>
</template>

<script>
import json from "../../json/csvjson.json";
export default {
  data: function() {
    return {
      model: null,
      search: null,
      default: true,
      items: [],
      item: null
    };
  },
  props: {
    address: String,
    value: Object
  },
  mounted() {
    setTimeout(() => {
      this.search = this.value.district;
      setTimeout(() => {
        const list = this.list;
        var index = 0;
        let count = 0;
        Array.from(list).forEach(val1 => {
          if (
            val1.subDistrict === this.value.subDistrict &&
            val1.district === this.value.district &&
            val1.province === this.value.province &&
            val1.Zipcode.toString() === this.value.Zipcode.toString()
          ) {
            index = count;
          }
          count++;
        });
        this.model = list[index];
        this.default = false;
        this.$emit("fill", this.address);
        this.send();
      }, 1300);
    }, 1000);
  },
  computed: {
    list() {
      const itemlist = [];
      if (this.items.length > 0) {
        Array.from(this.items).forEach(item => {
          if (item.subDistrict.length == 0) {
            const li = "";
            item = Object.assign({}, item, { description: li });
            itemlist.push(item);
          } else {
            const li = `${item.subDistrict} » ${item.district} » ${item.province} » ${item.Zipcode}`;
            item = Object.assign({}, item, { description: li });
            itemlist.push(item);
          }
        });
      }
      return itemlist;
    }
  },
  watch: {
    search(val) {
      if (val != null) {
        if (this.default) {
          const searchdata = Array.from(json).filter(data => {
            return new RegExp(val.toLowerCase()).test(
              data.subDistrict.substring(0, val.length).toLowerCase()
            );
          });
          this.items = searchdata;
        } else {
          switch (this.address) {
            case "Sub District": {
              const searchdata = Array.from(json).filter(data => {
                return new RegExp(val.toLowerCase()).test(
                  data.subDistrict.substring(0, val.length).toLowerCase()
                );
              });
              this.items = searchdata;
              break;
            }
            case "District": {
              const searchdata = Array.from(json).filter(data => {
                return new RegExp(val.toLowerCase()).test(
                  data.district.substring(0, val.length).toLowerCase()
                );
              });
              this.items = searchdata;
              break;
            }
            case "Province": {
              const searchdata = Array.from(json).filter(data => {
                return new RegExp(val.toLowerCase()).test(
                  data.province.substring(0, val.length).toLowerCase()
                );
              });
              this.items = searchdata;
              break;
            }
            case "Zipcode": {
              val = val.toString();
              const searchdata = Array.from(json).filter(data => {
                return new RegExp(val.toLowerCase()).test(
                  data.Zipcode.toString()
                    .substring(0, val.length)
                    .toLowerCase()
                );
              });
              this.items = searchdata;
              break;
            }
          }
        }
      }
    },
    value(val) {
      switch (this.address) {
        case "Sub District": {
          this.default = true;
          this.search = val.subDistrict;
          setTimeout(() => {
            const list = this.list;
            var index = 0;
            let count = 0;
            Array.from(list).forEach(val1 => {
              if (
                val1.subDistrict === val.subDistrict &&
                val1.district === val.district &&
                val1.province === val.province &&
                val1.Zipcode.toString() === val.Zipcode.toString()
              ) {
                index = count;
              }
              count++;
            });
            this.model = list[index];
            this.default = false;
            this.$emit("fill", this.address);
          }, 100);
          break;
        }
        case "District": {
          this.default = true;
          this.search = val.subDistrict;
          setTimeout(() => {
            const list = this.list;
            var index = 0;
            let count = 0;
            Array.from(list).forEach(val1 => {
              if (
                val1.subDistrict === val.subDistrict &&
                val1.district === val.district &&
                val1.province === val.province &&
                val1.Zipcode.toString() === val.Zipcode.toString()
              ) {
                index = count;
              }
              count++;
            });
            this.model = list[index];
            this.default = false;
            this.$emit("fill", this.address);
          }, 100);
          break;
        }
        case "Province": {
          this.default = true;
          this.search = val.subDistrict;
          setTimeout(() => {
            const list = this.list;
            var index = 0;
            let count = 0;
            Array.from(list).forEach(val1 => {
              if (
                val1.subDistrict === val.subDistrict &&
                val1.district === val.district &&
                val1.province === val.province &&
                val1.Zipcode.toString() === val.Zipcode.toString()
              ) {
                index = count;
              }
              count++;
            });
            this.model = list[index];
            this.default = false;
            this.$emit("fill", this.address);
          }, 100);
          break;
        }
        case "Zipcode": {
          this.default = true;
          this.search = val.subDistrict;
          setTimeout(() => {
            const list = this.list;
            var index = 0;
            let count = 0;
            Array.from(list).forEach(val1 => {
              if (
                val1.subDistrict === val.subDistrict &&
                val1.district === val.district &&
                val1.province === val.province &&
                val1.Zipcode.toString() === val.Zipcode.toString()
              ) {
                index = count;
              }
              count++;
            });
            this.model = list[index];
            this.default = false;
            this.$emit("fill", this.address);
          }, 100);
          break;
        }
      }
    }
  },
  methods: {
    send() {
      if (this.model) {
        this.$emit("recieve", this.model);
        this.$emit("fill", this.address);
      } else {
        const result = {
          district: "",
          subDistrict: "",
          province: "",
          Zipcode: "",
          description: ""
        };
        this.$emit("recieve", result);
        this.$emit("notfill", this.address);
      }
    }
  }
};
</script>

<style scoped>
.suggestor {
  width: 100%;
  position: relative;
}
</style>
