<template>
  <v-img
    class="ad-width"
    @click="moveToAd"
    src="https://vrthumb.imagetoday.co.kr/2021/05/21/tid288t001533.jpg"
  >
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
      this.$router.push("/challenge/1");
    },
  },
};
</script>

<style scoped>
.ad-width {
  width: 100%;
  margin: 8px auto;
  cursor: pointer;
}
</style>
