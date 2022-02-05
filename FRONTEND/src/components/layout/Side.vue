<template>
  <v-card
    max-width="20%"
    class="hidden-sm-and-down"
    elevation="0"
    height="100vh"
  >
    <side-contents />
  </v-card>
</template>

<script>
import SideContents from "../layout/SideContents.vue";

export default {
  name: "Side",
  components: { SideContents },

  data() {
    return {
      user: false,
    };
  },
};
</script>
<!--
<style lang="scss" scoped>
 @import '~vuetify/src/components/VStepper/_variables.scss';
$navigation-drawer-border-width :20px;
</style>-->
