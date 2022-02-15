<template>
  <router-link
    v-if="item != `Admin`"
    :to="{ path: `/feed/${itemno}` }"
    style="text-decoration: none; color: inherit; mr-2"
  >
    {{ item }}
  </router-link>
  <div
    v-else
    @click="warning"
    style="text-decoration: none; color: inherit; mr-2; cursor: pointer;"
  >
    {{ item }}
  </div>
</template>

<script>
export default {
  name: "NickNameModule",
  props: {
    item: String,
    itemno: Number, //피드 번호
  },
  methods: {
    warning() {
      alert("관리자 계정은 피드가 존재하지 않습니다");
    },
  },
};
</script>

<style>
.cursor-on-hover {
  cursor: pointer;
}
</style>
