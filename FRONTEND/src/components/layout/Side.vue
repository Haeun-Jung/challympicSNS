<template>
<<<<<<< HEAD
	<v-card class="hidden-sm-and-down" flat elevation="100">
		<side-contents />
	</v-card>
=======
  <v-card
    max-width="20%"
    class="hidden-sm-and-down"
    elevation="0"
    height="100vh"
  >
    <side-contents />
  </v-card>
>>>>>>> 59b10cbf8e03668b5e7e2f3b3eda90cb0aab7a68
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
<<<<<<< HEAD

<style scoped>
	.sticky-card {
		position: fixed;
	}
</style>
=======
<!--
<style lang="scss" scoped>
 @import '~vuetify/src/components/VStepper/_variables.scss';
$navigation-drawer-border-width :20px;
</style>-->
>>>>>>> 59b10cbf8e03668b5e7e2f3b3eda90cb0aab7a68
