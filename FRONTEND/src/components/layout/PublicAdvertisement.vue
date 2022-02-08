<template>
  <v-img
  class="ad-width"
  @click="moveToAd"
  src="https://vrthumb.imagetoday.co.kr/2021/05/21/tid288t001533.jpg">
  </v-img>
</template>

<script>
export default {
    name: "PublicAdvertisement",
    data() {
        return {
            publicAd: '용기내_챌린지'
        }
    },
    methods: {
        moveToAd() {
           this.$router.push("/search/" + this.publicAd);
        }
    }
}
</script>

<style scoped>
.ad-width {
    width: 100%;
    margin: 8px auto;
    cursor: pointer;
}
</style>