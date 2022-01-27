<template>
  <v-fab-transition>
                <v-btn
                  v-show="!hidden"
                  color="#3396F4"
                  dark
                  fixed
                  bottom 
                  right
                  fab
                >
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
</v-fab-transition>
</template>

<script>
export default {
  name:'FabButton',
}
</script>

<style>

</style>