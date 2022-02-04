<template>
  <div>
    <v-img
      height="500"
      contain
      class="grey darken-4"
      :src="require(`@/assets/${fileName}`)"
    ></v-img>
  </div>
</template>

<script>
export default {
  name: "PostImage",
  props: {
    fileName: String,
  },
};
</script>

<style>
</style>