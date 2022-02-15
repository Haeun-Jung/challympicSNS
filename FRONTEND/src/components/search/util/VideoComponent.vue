<template>
  <div>
    <video-player
      vjs-default-skin
      class="video-player-box"
      ref="videoPlayer"
      :playsinline="true"
      :options="playerOptions"
      controlsList="nodownload"
    >
    </video-player>
  </div>
</template>

<script>
export default {
  props: {
    post: Object,
  },
  data() {
    return {
      loaded: false,
      playerOptions: [],
    };
  },
  created() {
    this.playerOptions = {
      preload: "auto",
      muted: true,
      aspectRatio: "4:3",
      sources: [
        {
          type: "application/x-mpegURL",
          src:
            "https://d3iu4sf4n4i2qf.cloudfront.net/" +
            this.post.file_path +
            "/video/" +
            this.post.file_savedname +
            ".m3u8",
        },
      ],
    };
    //console.log(this.playerOptions.sources.src);
  },
};
</script>

<style>
.vjs-afterglow-skin .vjs-big-play-button:before {
  display: none;
}
.vjs-default-skin {
  display: none;
}
.vjs-default-skin.vjs-paused .vjs-big-play-button {
  display: none;
}
.vjs-default-skin.vjs-paused .vjs-control-bar {
  display: block;
}
/* Hide if the video is playing/
    .vjs-has-started .vjs-big-play-button {
        display: none;
    }

    / Show if the video is paused*/
.vjs-paused .vjs-big-play-button {
  display: none;
}
</style>
