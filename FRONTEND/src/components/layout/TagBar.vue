<template>
	<v-toolbar
		:color="$vuetify.theme.dark ? 'grey darken-4' : '#fafafa'"
		dense
		fixed
		elevation="0"
		><!-- 추천 태그 리스트 뿌리기ㅣ 이부분 어차피 나중에 template써서 다시 해야하니 지금은 관상용으로만 냅두기 -->
		<v-row>
			<v-col class="hidden-md-and-down" />
			<v-col class="hidden-sm-and-up" />
			<v-chip-group class="ml-15 hidden-md-and-down">
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
			<v-chip-group class="hidden-sm-and-up ml-3">
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
			<v-col class="hidden-md-and-down" />
			<v-col class="hidden-sm-and-up" />
		</v-row>
	</v-toolbar>
</template>

<script>
	import { getRecommend } from "@/api/search.js";
	export default {
		name: "TagBar",
		created() {
			if (this.$store.state.userStore.userInfo) {
				console.log("추천태그 유저 번호");
				console.log(this.$store.state.userStore.userInfo.user_no);
				getRecommend(
					this.$store.state.userStore.userInfo.user_no,
					(response) => {
						console.log("추천태그 userNo있을 때");
						console.log(response.data.data);
						this.tagList = response.data.data.tagList;
						this.list = this.makeFour(response.data.data.tagList);
					},
					(error) => {
						console.log(error);
					}
				);
			} else {
				console.log("추천태그 유저 번호");
				console.log(0);
				getRecommend(
					0,
					(response) => {
						console.log("추천태그 userNo0 일때");
						console.log(response.data.data);
						this.tagList = response.data.data.tagList;
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
