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
import "video.js/dist/video-js.css";
import { videoPlayer } from "vue-video-player";

export default {
  name: "Player",
  components: {
    videoPlayer,
  },
  data() {
    return {
      playerOptions: {
        preload: "auto",
        // autoplay: true,
        muted: true,
        loop: true,
        aspectRatio: "4:3",
        sources: [
          {
            type: "video/mp4",
            src: "//vjs.zencdn.net/v/oceans.mp4",
          },
        ],
      },
    };
  },
};
</script>

<style>
</style>