<template>
  <v-btn @click="copyChallengeLink" class="icon-margin" icon>
    <v-icon large> mdi-export-variant</v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "ShareButton",
  props: {
    postNo: Number,
  },
  methods: {
    copyChallengeLink() {
      // 크로스 브라우징 이슈 때문에 execCommand 메서드를 사용했습니다.
      const inputTag = document.createElement("input");
      document.body.appendChild(inputTag);
      inputTag.value = window.document.location.href;
      if (inputTag.value[inputTag.value.length - 1] === "#") {
        inputTag.value = inputTag.value.split("#")[0];
      }
      if (this.postNo) {
        inputTag.value += `#${this.postNo}`;
      }
      inputTag.select();
      document.execCommand("copy");
      document.body.removeChild(inputTag);
    },
  },
};
</script>

<style scoped>
.icon-margin {
  margin: 0 0.5rem;
}
</style>