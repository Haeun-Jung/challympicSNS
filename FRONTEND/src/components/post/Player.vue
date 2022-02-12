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
  props: {
    fileName: String,
    filePath: String
  },
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
            src: "http://d384sk7z91xokb.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
            // src: `https://d384sk7z91xokb.cloudfront.net/${this.file_path}/video/${this.fileName}.m3u8`,
          },
        ],
      },
    };
  },
};
</script>

<style></style>
