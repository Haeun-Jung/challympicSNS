<template>
	<!-- 로그인 X -->	
	<div v-if="isGuest" class="title-width">
		<v-card-subtitle>
			<h2>구독</h2>
		</v-card-subtitle>
		<v-list-item-content>
			<h4 class="main-side-menu-contents">
				챌린지를 구독해보세요!
			</h4>
		</v-list-item-content>
	</div>
    <v-list v-else class="title-width">
        <v-list-group>
          <template v-slot:activator class="title-background">
            <v-card-subtitle class="title-width">
				<h2>구독</h2>
			</v-card-subtitle>
          </template>
		  <!-- 로그인 O -->
          <v-chip
			v-for="challenge in subscriptions"
			:key="challenge.id"
			:value="challenge"
			:to="{ path: '/search/' + challenge.title }"
			v-model="challenge.isOpen"
			color="primary"
			outlined
			close
			@click:close="remove(challenge.id)">
			{{ challenge.title }}
			</v-chip>
        </v-list-group>
    </v-list>
</template>

<script>
export default {
  name: 'MySubscription',
  props: { search: String },
  data() {
    return {
	  isGuest: false,
      subscriptions: [
			//사용자 태그
			{
				id: 1,
				title: "미라클모닝_챌린지",
			},
			{
				id: 2,
				title: "요리_챌린지",
			},
			{
				id: 3,
				title: "코딩_챌린지",
			},
		],
		index: 1,
    }
  },
  methods: {
		remove(id) {
			// 여기서 delete로 삭제된 태그 id마 보냄
			this.subscriptions.splice(id - this.index, 1);
			this.index++; //카운트 해줘야 다음 태그 제대로 지워짐
			// 이렇게 하고, 페이지 refresh 해서 태그 다시 받아와야함.....
			alert(id);
			alert(id - 1);
			this.subscriptions = [...this.subscriptions];
		},
	},
}
</script>

<style scoped>
.title-width {
	width: 100%;
	background: transparent;
}
.main-side-menu-contents {
	color: #121212;
	text-align: center;
	margin: 1%;
	padding-left: 5%;
	padding-right: 7%;
}
</style>