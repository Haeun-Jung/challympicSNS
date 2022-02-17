<template>
  <v-img class="ad-width" @click="moveToAd" src="../../assets/public-ad.png">
  </v-img>
</template>

<script>
export default {
  name: "PublicAdvertisement",
  data() {
    return {
      publicAd: "용기내_챌린지",
    };
  },
  methods: {
    moveToAd() {
      //  TODO: this.$router.push("/challenge/" + this.challengeNo); 로 수정하기
      window.location.href = `/challenge/5`;
    },
  },
};
</script>

<style scoped>
.ad-width {
  /* width: 100%; */
  margin: 8px auto;
  cursor: pointer;
}
</style>
