<template>
  <v-stepper-content :complete="page > 3" step="3">
    <v-card class="pa-5">
      <v-row>
        <v-col>
          <h1>Opening Account</h1>
        </v-col>
      </v-row>
      <v-form>
        <v-row>
          <v-col cols="4">
            <v-text-field
              v-model="addressInfo.addressNum"
              label="Address Number *"
              :rules="[v => !!v || 'Address Number is required']"
              required
              @change="check"
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <v-text-field
              v-model="addressInfo.villageNum"
              label="Village Number"
              @change="check"
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <v-text-field
              v-model="addressInfo.building"
              label="Village / Building / Floor Number"
              @change="check"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="4">
            <v-text-field
              v-model="addressInfo.soi"
              label="Alley / Soi"
              @change="check"
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <v-text-field
              v-model="addressInfo.road"
              label="Road"
              @change="check"
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <Suggestion
              @recieve="cal"
              @fill="fill"
              @notfill="notfill"
              address="Sub District"
              :value="item"
              @change="check"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="4">
            <Suggestion
              @recieve="cal"
              @fill="fill"
              @notfill="notfill"
              address="District"
              :value="item2"
              @change="check"
            />
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <Suggestion
              @recieve="cal"
              @fill="fill"
              @notfill="notfill"
              address="Province"
              :value="item3"
              @change="check"
            />
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="4">
            <Suggestion
              @recieve="cal"
              @fill="fill"
              @notfill="notfill"
              address="Zipcode"
              :value="item4"
              @change="check"
            />
          </v-col>
        </v-row>
      </v-form>
      <v-row>
        <v-col class="text-start">
          <v-btn color="grey" @click="backPage" justify="start">
            <h3>Back</h3>
          </v-btn>
        </v-col>
        <v-col class="text-end">
          <v-btn
            color="green lighten-1"
            @click="changePage"
            class="white--text"
            target="_blank"
            :disabled="validate ? false : true"
          >
            <h3>Next</h3>
          </v-btn>
        </v-col>
      </v-row>
    </v-card>
  </v-stepper-content>
</template>

<script>
import Suggestion from "./AddressSuggestion";
import Axios from "../../axios/axios";
export default {
  data: function() {
    return {
      addressInfo: {
        addressNum: "",
        villageNum: "",
        soi: "",
        road: "",
        district: "",
        subDistrict: "",
        building: "",
        province: "",
        zipcode: ""
      },
      item: null,
      item2: null,
      item3: null,
      item4: null,
      validate: false,
      sdval: false,
      dval: false,
      pval: false,
      zval: false
    };
  },
  props: {
    page: Number
  },
  components: {
    Suggestion
  },
  mounted() {
    Axios.get("/GetAddressServlet?ownerID=2")
      .then(response => {
        this.addressInfo = response.data.address;
      })
      .catch(error => {
        console.log(error);
      })
      .finally(() => {
        const subDistrict = this.addressInfo.subDistrict;
        const district = this.addressInfo.district;
        const province = this.addressInfo.province;
        const Zipcode = this.addressInfo.zipcode;
        this.cal(
          Object.assign(
            {},
            {
              subDistrict: subDistrict,
              district: district,
              province: province,
              Zipcode: Zipcode
            }
          )
        );
        setTimeout(() => {
          this.check();
        }, 3200);
      });
  },
  methods: {
    cal(val) {
      if (val.subDistrict.length > 0) {
        this.item = val;
        this.item2 = val;
        this.item3 = val;
        this.item4 = val;
        this.addressInfo.subDistrict = this.item.subDistrict;
        this.addressInfo.district = this.item.district;
        this.addressInfo.province = this.item.province;
        this.addressInfo.zipcode = this.item.Zipcode;
      }
    },
    changePage() {
      this.$emit("changePage", 4);
      this.$emit("send", this.addressInfo);
    },
    backPage() {
      this.$emit("changePage", 2);
    },
    checkAddress(v) {
      return !!v;
    },
    fill(v) {
      switch (v) {
        case "Sub District":
          this.sdval = true;
          break;
        case "District":
          this.dval = true;
          break;
        case "Province":
          this.pval = true;
          break;
        case "Zipcode":
          this.zval = true;
          break;
      }
      this.check();
    },
    notfill(v) {
      switch (v) {
        case "Sub District":
          this.sdval = false;
          break;
        case "District":
          this.dval = false;
          break;
        case "Province":
          this.pval = false;
          break;
        case "Zipcode":
          this.zval = false;
          break;
      }
      this.check();
    },
    check() {
      if (
        this.checkAddress(this.addressInfo.addressNum) &&
        this.sdval &&
        this.dval &&
        this.pval &&
        this.zval
      ) {
        this.validate = true;
      } else {
        this.validate = false;
      }
    }
  }
};
</script>

<style></style>
