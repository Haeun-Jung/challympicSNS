<template>
  <div>
    <video-player
      id="player"
      class="video-player-box vjs-big-play-centered"
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
      muted: true,
      loop: true,
      sources: [
        {
          type: "application/x-mpegURL",
          // src: "http://d3iu4sf4n4i2qf.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
          src: `https://d3iu4sf4n4i2qf.cloudfront.net/${this.filePath}/video/${this.fileName}.m3u8`,
        },
      ],
    };
  },
};
</script>

<style scoped>
.video-player-box {
  position: relative;
  /* 16:9 비율 설정 */
  padding-top: 56.25%;
  background-color: rgb(0, 0, 0);
}
::v-deep .video-js {
  display: flex;
  justify-content: center;
  position: absolute;
  overflow: hidden;
  height: 100%;
  width: 100%;
  top: 0;
}
::v-deep .video-js .vjs-tech {
  width: auto;
  left: auto;
}
</style>
