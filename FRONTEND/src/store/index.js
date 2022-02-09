import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import userStore from '@/store/modules/userStore.js'

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    userStore,
  },
  plugins: [
    createPersistedState({
      storage: localStorage,
    })
  ]
});