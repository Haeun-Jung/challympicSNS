<template>
  <div id="videoContainer">
    <video-player
      id="player"
      class="video-player-box"
      ref="videoPlayer"
      playsinline
      :options="playerOptions"
    ></video-player>
  </div>
</template>

<script>
export default {
  name: "Player",
  props: {
    fileName: String,
    filePath: String,
  },
  data() {
    return {
      playerOptions: {},
    };
  },
  created() {
    this.playerOptions = {
      preload: "auto",
      autoplay: true,
      muted: true,
      loop: true,
      aspectRatio: "16:9",
      sources: [
        {
          type: "application/x-mpegURL",
          // src: "http://d3iu4sf4n4i2qf.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
          src: `https://d3iu4sf4n4i2qf.cloudfront.net/${this.filePath}/video/${this.fileName}.m3u8`,
        },
      ],
    };
    console.log(this.playerOptions["sources"]);
  },
};
</script>

<style scoped>
#player {
  object-fit: cover;
}
</style>
