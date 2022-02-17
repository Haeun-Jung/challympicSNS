<template>
	<v-toolbar
		:color="$vuetify.theme.dark ? 'grey darken-4' : '#fafafa'"
		dense
		fixed
		elevation="0"
		><!-- 추천 태그 리스트 뿌리기ㅣ 이부분 어차피 나중에 template써서 다시 해야하니 지금은 관상용으로만 냅두기 -->
		<v-row v-if="!isMobile()">
			<v-col />
			<v-chip-group class="ml-15">
				<v-chip
					v-for="tag in tagList"
					class="s recommendation-chips"
					:key="tag.tag_no"
					:value="tag"
					text-color="#3396F4"
					@click="moveTag(tag.tag_content)"
				>
					{{ tag.tag_content }}
				</v-chip>
			</v-chip-group>
			<v-col />
		</v-row>
		<v-row v-else>
			<div style="margin: 0 auto">
				<v-chip-group>
					<v-chip
						v-for="(tag, idx) in mobileTagList"
						class="s recommendation-chips"
						:key="tag.tag_no"
						:value="tag"
						text-color="#3396F4"
						@click="moveTag(tag.tag_content)"
					>
						<div v-if="idx < 4">
							{{ tag.tag_content }}
						</div>
						<div v-else>아니 왜 안돼</div>
					</v-chip>
				</v-chip-group>
			</div>
		</v-row>
	</v-toolbar>
</template>

<script>
	import { getRecommend } from "@/api/search.js";
	export default {
		name: "TagBar",
		created() {
			if (this.$store.state.userStore.userInfo) {
				getRecommend(
					this.$store.state.userStore.userInfo.user_no,
					(response) => {
						this.tagList = response.data.data.tagList;
						this.list = this.makeFour(response.data.data.tagList);
					},
					(error) => {
						console.log(error);
					}
				);
			} else {
				getRecommend(
					0,
					(response) => {
						this.tagList = response.data.data.tagList;
						this.list = this.makeFour(response.data.data.tagList);
					},
					(error) => {
						console.log(error);
					}
				);
			}
		},
		methods: {
			moveTag(tag_content) {
				const subTag = tag_content.substring(1);
				this.$router.push(`/search/${encodeURIComponent(subTag)}`);
			},
			makeFour(list) {
				for (var i = 0; i < 4; i++) {
					this.mobileTagList.push(list[i]);
				}
			},
			isMobile() {
				if (
					/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
						navigator.userAgent
					)
				) {
					return true;
				} else {
					return false;
				}
			},
		},
		data() {
			return {
				tagList: [],
				list: [],
				mobileTagList: [],
			};
		},
	};
</script>

<style scoped>
	.meow {
		padding: 0;
	}
</style>
