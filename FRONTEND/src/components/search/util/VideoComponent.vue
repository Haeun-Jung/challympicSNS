<template>
	<div>
		<video-player
			vjs-default-skin
			class="video-player-box"
			ref="videoPlayer"
			height="280!important"
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
			video: String,
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
						src: this.video,
					},
				],
			};

			console.log(this.playerOptions.sources.src);
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
	/* Hide if the video is playing*/
	.vjs-has-started .vjs-big-play-button {
		display: none;
	}

	/* Show if the video is paused*/
	.vjs-paused .vjs-big-play-button {
		display: none;
	}
</style>
