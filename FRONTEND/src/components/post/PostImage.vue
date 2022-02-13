<template>
  <div>
    <v-img
      max-height="600"
      contain
      class="grey darken-4"
      :src="`https://d384sk7z91xokb.cloudfront.net/${filePath}/${fileName}`"
    ></v-img>
  </div>
</template>

<script>
export default {
  name: "PostImage",
  props: {
    fileName: String,
    filePath: String
  },
};
</script>

<style>
</style>