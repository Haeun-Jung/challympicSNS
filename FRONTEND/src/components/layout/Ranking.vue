<template>
	<div>
		<v-card-subtitle>
			<h2>👑도전왕👑</h2>
		</v-card-subtitle>
		<v-list-item-content>
			<div v-for="(champion, idx) in champions" class="rank-item" :key="champion.id">
				<span class="number black--text">{{
					idx + 1
				}}</span>
				<span class="nickname" @click="moveToFeed(champion.user_no)">
				<span v-if="champion.user_title != null">
				{{champion.user_title}}	
				</span>
				{{
					champion.user_nickname
				}}</span>
			</div>
		</v-list-item-content>
	</div>
</template>

<script>
import { getChampions } from '@/api/side.js';
	export default {
		name: "Ranking",
		data() {
			return {
				champions: [],
			};
		},
		methods: {
			moveToFeed(champion_no) {
				this.$router.push("/feed/" + champion_no+"/post");
			},
		},
		created(){
			getChampions(
				(response) => {
					this.champions = response.data.data;
				}
			)
		}
	};
</script>

<style scoped>
	.rank-item {
		display: flex;
		align-items: center;
		margin: 0.5rem 0;
	}
	.number {
		text-align: center;
		line-height: 1.3;
		background: rgb(255, 243, 74);
		width: 20px;
		height: 20px;
		border-radius: 50%;
		margin: 0 0.5rem;
	}
	.nickname {
		cursor: pointer;
	}
	.main-side-menu-contents {
		margin: 1%;
		padding-left: 7%;
		padding-right: 7%;
	}
</style>
