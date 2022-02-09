<template>
	<v-card
		class="mt-4"
		:color="$vuetify.theme.dark ? '#2a2a2a' : 'grey lighten-4'"
		elevation="0"
		max-width="600"
	>
		<v-data-table
			:headers="headers"
			:items="challengeList"
			item-key="challenge_no"
			class="elevation-1"
			:hide-default-footer="true"
		>
			<!--	<template #item.challenge_participants="{ value }">
			</template>-->
			<template v-slot:item="{ item }">
				<div v-bind="sumField(item.challenge_participants)"></div>
			</template>
		</v-data-table>

		<v-card-title>
			<v-row> </v-row>
			<v-row>
				<v-icon
					:color="checking ? 'red lighten-2' : 'indigo'"
					class="mr-5; ml-5"
					size="64"
					@click="takePulse"
				>
					mdi-account-group
				</v-icon>

				<div>
					<div class="text-caption grey--text">전체 챌린지 참여 횟수</div>
					<span class="text-h3 font-weight-black">
						{{ setsum }}
					</span>

					<strong> 회</strong>
				</div>
			</v-row>
			<v-spacer></v-spacer>
		</v-card-title>

		<v-sheet color="transparent">
			<v-sparkline
				:key="String(avg)"
				:smooth="16"
				:gradient="['#f72047', '#ffd200', '#1feaea']"
				:line-width="3"
				:value="heartbeats"
				auto-draw
				stroke-linecap="round"
			></v-sparkline>
		</v-sheet>
	</v-card>
</template>

<script>
	const exhale = (ms) => new Promise((resolve) => setTimeout(resolve, ms));
	export default {
		data() {
			return {
				checking: false,
				setsum: -1,
				index: 0,
				heartbeats: [],
				headers: [],
				challengeList: [
					{
						challenge_no: 1,
						challenge_title: "아이스버킷챌린지",
						user_nickname: "챌림픽",
						challenge_participants: 13254,
						challenge_subscribe_no: 43554,
						challenge_start: "2022-01-26",
						challenge_end: "2022-07-26",
						challenge_report: 4,
						challenge_official: true,
					},
					{
						challenge_no: 17,
						challenge_title: "김싸피덤벼",
						user_nickname: "이싸피",
						challenge_participants: 1,
						challenge_subscribe_no: 0,
						challenge_start: "2021-12-5",
						challenge_end: "2022-01-02",
						challenge_report: 415,
						challenge_official: false,
					},
					{
						challenge_no: 7,
						challenge_title: "공식",
						user_nickname: "챌림픽",
						challenge_participants: 0,
						challenge_subscribe_no: 0,
						challenge_start: "2022-02-05",
						challenge_end: "2022-06-30",
						challenge_report: 0,
						challenge_official: true,
					},
				],
			};
		},
		computed: {
			avg() {
				const sum = this.heartbeats.reduce((acc, cur) => acc + cur, 0);
				const length = this.heartbeats.length;
				if (!sum && !length) return 0;
				return Math.ceil(sum / length);
			},
		},

		created() {
			this.takePulse(false);
		},

		methods: {
			sumField(value) {
				if (this.index < Object.keys(this.challengeList).length) {
					this.index++;
					this.setsum += value;

					//alert("done!" + this.setsum);
				}
			},
			heartbeat() {
				return Math.ceil(Math.random() * (120 - 80) + 80);
			},
			async takePulse(inhale = true) {
				this.checking = true;

				inhale && (await exhale(1000));

				this.heartbeats = Array.from({ length: 20 }, this.heartbeat);

				this.checking = false;
			},
		},

		/*	Api 통신 용
		created() {
			let param = {
				pg: 1,
				spp: 20,
				key: null,
				word: null,
			};
			userList(
				param,
				(response) => {
					this.userList = response.data;
				},
				(error) => {
					console.log(error);
				}
			);
		},*/
	};
</script>
