import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import userStore from '@/store/modules/userStore.js'
import postStore from '@/store/modules/postStore.js'
import challengeStore from '@/store/modules/challengeStore.js'

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    userStore,
    postStore,
    challengeStore
  },
  plugins: [
    createPersistedState({
      storage: localStorage,
    })
  ]
});