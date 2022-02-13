<template>
	<video-player
		class="video-player-box"
		ref="videoPlayer"
		height="280!important"
		:playsinline="true"
		:options="playerOptions"
		controlsList="nodownload"
	>
	</video-player>
</template>

<script>
	export default {
		props: {
			videoinfo: Object,
		},
		data() {
			return {
				videoUrl: this.videoinfo.content,
				loaded: false,
				playerOptions: [],
			};
		},
		created() {
			this.playerOptions = {
				preload: "auto",
				autoplay: true,
				muted: true,
				loop: true,
				aspectRatio: "4:3",
				sources: [
					{
						type: "application/x-mpegURL",
						src: this.videoinfo.content,
					},
				],
			};

			console.log(this.playerOptions.sources.src);
		},
	};
</script>

<style></style>
