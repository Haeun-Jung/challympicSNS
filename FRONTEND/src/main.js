import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import vueCookies from "vue-cookies";
import VueVideoPlayer from "vue-video-player";
import "video.js/dist/video-js.css";
import 'videojs-contrib-hls'

Vue.use(vueCookies);
Vue.use(VueVideoPlayer);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
