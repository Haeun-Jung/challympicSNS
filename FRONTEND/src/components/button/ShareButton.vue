<template>
  <div>
      <v-btn @click="copyChallengeLink" class="icon-margin" icon>
        <v-icon large class="hidden-md-and-down"> mdi-export-variant</v-icon>
        <v-icon class="hidden-sm-and-up"> mdi-export-variant</v-icon>
      </v-btn>    
      <v-snackbar
      v-model="snackbar"
      :timeout="timeout"
      color="success"
      outlined
      style="font-weight: bold; border: 2px solid"
    >
      {{ text }}

      <template v-slot:action="{ attrs }">
        <v-btn color="success" text v-bind="attrs" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  name: "ShareButton",
  props: {
    postNo: Number,
  },
  data() {
    return {
      snackbar: false,
      text: "URL이 복사되었습니다.",
      timeout: 1500,
    }
  },
  methods: {
    copyChallengeLink() {
      this.snackbar = true;
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
