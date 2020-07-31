import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home/Home";
import View from "../views/View/View";
import Open from "../views/Open/Open";
import Close from "../views/Close/Close";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/view",
    name: "View",
    component: View
  },
  {
    path: "/open",
    name: "Open",
    component: Open
  },
  {
    path: "/close",
    name: "Close",
    component: Close
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
