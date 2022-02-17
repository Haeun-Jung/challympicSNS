import {
  getRecentPostList,
  getPostList,
  createPost,
  getLikeList,
} from "@/api/post.js";

const postStore = {
  namespaced: true,
  state: {
    recentPostList: [],
    postList: [],
    likeList: [],
  },
  getters: {},
  mutations: {
    SET_RECENT_POST_LIST(state, postList) {
      state.recentPostList = postList.map((post) => {
        return {
          ...post,
          post_content: post.post_content,
          post_regdate: post.post_regdate.split("T")[0].replace(/-/g, "."),
        };
      });
    },
    SET_POST_LIST(state, postList) {
      state.postList = postList.map((post) => {
        return {
          ...post,
          post_content: post.post_content,
          post_regdate: post.post_regdate.split("T")[0].replace(/-/g, "."),
        };
      });
    },
    SET_LIKE_LIST(state, likeList) {
      state.likeList = likeList;
    },
  },
  actions: {
    getRecentPostList({ commit }, userNo) {
      if (!userNo) {
        userNo = "";
      }
      getRecentPostList(
        userNo,
        (response) => {
          commit("SET_RECENT_POST_LIST", response.data.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getPostList({ commit }, { challengeNo, userNo }) {
      getPostList(
        challengeNo,
        userNo,
        (response) => {
          const { data } = response;
          commit("SET_POST_LIST", data.data);
        },
        () => {}
      );
    },
    createPost(context, { challengeNo, post }) {
      createPost(
        challengeNo,
        post,
        () => {},
        () => {}
      );
    },
    getLikeList({ commit }, { postNo, userNo }) {
      getLikeList(postNo, userNo, (response) => {
        const { data } = response;
        commit("SET_LIKE_LIST", data.data);
      });
    },
  },
};

export default postStore;
