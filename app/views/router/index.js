import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../components/model/landingpage.vue")
  },
  {
    path: "/about",
    name: "About",
    component: () => import("../views/About.vue")
  },
  {
    path: "/game",
    name: "Game",
    component: () => import("../views/Game.vue")
  }
];

const router = new VueRouter({
  routes
});

export default router;
