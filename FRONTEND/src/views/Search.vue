<template>
  <v-app class="search-result-container">
    <router-view />
  </v-app>
</template>

<script>
export default {
  name: "Search",
};
</script>

<style>
.search-result-container {
  padding-left: 0%;
}
.search-control-box-mobile {
  font-weight: bold;
}
.search-control-box-desktop {
  font-weight: bold;
}
.search-control-box-mobile #search-keyword {
  color: #3396f4;
  margin-right: 1.25em;
  font-size: 1.5rem !important;
}
.search-control-box-desktop #search-keyword {
  color: #3396f4;
  margin-right: 1.25em;
  font-size: 1.5rem !important;
}
</style>
