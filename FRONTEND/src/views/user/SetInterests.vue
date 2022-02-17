<template>
	<div class="container ma-auto" color="transparent">
		<v-list color="transparent">
			<v-card-text class="tag-width hidden-md-and-down">
				<div :class="tag - div">
					<h2 class="text-h6 mt-8 mb-5">
						🎈 챌림픽을 이용하며 보고싶은 태그를 선택하세요
					</h2>
					<v-chip-group v-model="selectInterests" column multiple>
						<v-chip
							filter
							outlined
							class="interest-one"
							v-for="tag in tagList"
							:key="tag.tag_no"
							:value="tag.tag_no"
							color="primary"
						>
							{{ tag.tag_content }}
						</v-chip>
					</v-chip-group>
					<v-col class="save-btn">
						<v-btn @click="saveInterests" color="#3396F4" class="" height="50">
							<span class="tag-save-btn-txt">저장하기</span>
						</v-btn>
					</v-col>
				</div>
			</v-card-text>
			<v-card-text class="tag-width hidden-sm-and-up">
				<div class="mobile-tag-div">
					<h2 class="text-h6 mt-15 mb-5">
						🎈 챌림픽을 이용하며 보고싶은 태그를 선택하세요
					</h2>
					<v-chip-group v-model="selectInterests" column multiple>
						<v-chip
							filter
							outlined
							class="interest-one"
							v-for="tag in mobileTagList"
							:key="tag.tag_no"
							:value="tag.tag_no"
							color="primary"
						>
							{{ tag.tag_content }}
						</v-chip>
					</v-chip-group>
					<v-col class="save-btn">
						<v-btn @click="saveInterests" color="#3396F4" class="" height="50">
							<span class="tag-save-btn-txt">저장하기</span>
						</v-btn>
					</v-col>
				</div>
			</v-card-text>
		</v-list>
	</div>
</template>

<script>
	import { getAllInterest } from "@/api/side.js";
	export default {
		name: "SetInterest",
		data() {
			return {
				selectInterests: [],
				tagList: [],
				mobileTagList: [],
				list: [],
			};
		},
		computed: {},
		methods: {
			saveInterests(event) {
				event.preventDefault();
				// console.log("유저 이메일");
				// console.log(this.$store.state.userStore.tempUserEmail);
				// console.log(this.selectInterests);
				this.$store.dispatch("userStore/setInterests", {
					user_email: this.$store.state.userStore.tempUserEmail,
					interests: this.selectInterests,
				});
			},
			resize(list) {
				for (var i = 0; i < 26; i++) {
					this.mobileTagList.push(list[i]);
				}
			},
		},
		created() {
			getAllInterest((response) => {
				this.tagList = response.data.data;
				this.list = this.resize(response.data.data);
			});
		},
	};
</script>

<style scoped>
	.interest-one {
		margin: 7px;
	}

	.tag-save-btn-txt {
		color: white;
	}

	.tag-width {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.save-btn {
		display: flex;
		justify-content: end;
	}

	.tag-div {
		width: 75%;
	}
	.mobile-tag-div {
		margin-left: 2%;
		margin-right: 2%;
	}
</style>
