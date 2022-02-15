<template>
  <div>
    <v-img
      max-height="600"
      contain
      class="grey darken-4"
      :src="`https://d3iu4sf4n4i2qf.cloudfront.net/${filePath}/${fileName}`"
    ></v-img>
  </div>
</template>

<script>
export default {
  name: "PostImage",
  props: {
    fileName: String,
    filePath: String,
  },
};
</script>

<style></style>
