<template>
  <v-stepper-content :complete="page > 2" step="2">
    <v-card class="pa-5">
      <v-row>
        <v-col>
          <h1>Opening Account</h1>
        </v-col>
      </v-row>
      <v-form>
        <v-row>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.citizenID"
              label="Citizen ID *"
              :rules="[
                idCheck(
                  this.registerInfo.citizenID,
                  this.registerInfo.passportID
                ) || 'Citizen ID is required',
                v =>
                  v.length == 13 ||
                  v.length == 0 ||
                  'Citizen ID must be 13 digits'
              ]"
              @change="check"
              required
            ></v-text-field>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.passportID"
              label="Passport *"
              :rules="[
                idCheck(
                  this.registerInfo.citizenID,
                  this.registerInfo.passportID
                ) || 'Passport ID is required',
                v =>
                  v.length == 9 ||
                  v.length == 0 ||
                  'Passport ID must be 9 digits'
              ]"
              @change="check"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.ownerName"
              label="First Name (English) *"
              :rules="[v => !!v || 'First Name is required']"
              @change="check"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.ownerLastname"
              label="Last Name (English) *"
              :rules="[v => !!v || 'Last Name is required']"
              @change="check"
              required
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="3">
            <v-select
              v-model="registerInfo.gender"
              :items="sex"
              menu-props="auto"
              single-line
              label="Gender *"
              :rules="[v => !!v || 'Gender is required']"
              @change="check"
              required
            ></v-select>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="3">
            <Date @date="changeDate" :birthdate="registerInfo.birthdate" />
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.email"
              label="Email *"
              :rules="emailRules"
              @change="check"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.tel"
              label="Telephone Number *"
              :rules="[v => !!v || 'Telephone Number is required']"
              @change="check"
              required
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="3">
            <v-text-field
              v-model="registerInfo.occupation"
              label="Occupation *"
              :rules="[v => !!v || 'Occupation is required']"
              @change="check"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="9" id="button">
            <v-btn color="grey" @click="backPage">
              <h3>Back</h3>
            </v-btn>
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
      </v-form>
    </v-card>
  </v-stepper-content>
</template>

<style scoped>
.v-btn {
  margin: 2%;
}
#button {
  display: flex;
  justify-content: flex-end;
}
</style>
<script>
import Date from "./Datepicker";
import Axios from "../../axios/axios";
export default {
  data: function() {
    return {
      registerInfo: {
        ownerName: "",
        ownerLastname: "",
        birthdate: "",
        citizenID: "",
        passportID: "",
        email: "",
        occupation: "",
        tel: "",
        gender: ""
      },
      emailRules: [
        v => !!v || "E-mail is required",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid"
      ],
      sex: ["Male", "Female"],
      validate: false
    };
  },
  components: {
    Date
  },
  mounted() {
    Axios.get("/GetOwnerServlet?ownerID=2")
      .then(response => {
        this.registerInfo = response.data;
        this.check();
      })
      .catch(error => {
        console.log(error);
      });
  },
  props: {
    page: Number
  },
  methods: {
    changePage() {
      this.$emit("changePage", 3);
      this.$emit("send", this.registerInfo);
    },
    backPage() {
      this.$emit("changePage", 1);
    },
    changeDate(date) {
      this.registerInfo.birthdate = date;
    },
    formcheck(v) {
      return !!v;
    },
    citizenIDcheck(v) {
      return !!v && v.length == 13;
    },
    passportCheck(v) {
      return !!v && v.length == 9;
    },
    bothcheck(v1, v2) {
      return (
        (this.citizenIDcheck(v1) && v2.length == 0) ||
        (this.passportCheck(v2) && v1.length == 0) ||
        (this.citizenIDcheck(v1) && this.passportCheck(v2))
      );
    },
    idCheck(v1, v2) {
      return !!v1 || !!v2;
    },
    emailcheck(v) {
      return /.+@.+\..+/.test(v);
    },
    check() {
      if (
        this.formcheck(this.registerInfo.ownerName) &&
        this.formcheck(this.registerInfo.ownerLastname) &&
        this.bothcheck(
          this.registerInfo.citizenID,
          this.registerInfo.passportID
        ) &&
        this.formcheck(this.registerInfo.birthdate) &&
        this.formcheck(this.registerInfo.gender) &&
        this.formcheck(this.registerInfo.email) &&
        this.formcheck(this.registerInfo.occupation) &&
        this.formcheck(this.registerInfo.tel) &&
        this.emailcheck(this.registerInfo.email)
      ) {
        this.validate = true;
      } else {
        this.validate = false;
      }
    }
  }
};
</script>
