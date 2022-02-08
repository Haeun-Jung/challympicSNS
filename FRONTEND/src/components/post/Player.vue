<template>
  <div>
    <video-player
      class="video-player-box"
      ref="videoPlayer"
      :playsinline="true"
      :options="playerOptions"
    ></video-player>
  </div>
</template>

<script>
export default {
  name: "Player",
  data() {
    return {
      playerOptions: {
        preload: "auto",
        autoplay: true,
        muted: true,
        loop: true,
        aspectRatio: "4:3",
        sources: [
          {
            type: "application/x-mpegURL",
            src: "http://d384sk7z91xokb.cloudfront.net/output/video/2022.02.06/c88b277171a94d9c3ac7e32f82bb9d93/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
          },
        ],
      },
    };
  },
};
</script>

<style>
</style>