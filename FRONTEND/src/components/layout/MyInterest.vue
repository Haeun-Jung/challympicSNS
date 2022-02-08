<template>
	<!-- 로그인 X -->	
	<div v-if="isGuest" class="interest-wrapper">
		<v-card-subtitle>
			<h2>내 관심사</h2>
		</v-card-subtitle>
		<v-list-item-content >
			<h4 class="main-side-menu-contents">
				관심사를 등록하고 관련 영상을 찾아보세요!
			</h4>
			<div class="main-side-menu-btn-container">
				<v-btn
					@click="clickLoginBtn"
					color="primary"
					class="main-side-menu-btn"
				>
					로그인
				</v-btn>
			</div>
		</v-list-item-content>
	</div>
	<!-- 로그인 O -->
	<v-list v-else class="title-width">
        <v-list-group>
          <template v-slot:activator>
            <v-card-subtitle class="title-width">
				<h2>내 관심사</h2>
			</v-card-subtitle>
          </template>
          <v-chip
			v-for="tag in interests"
			:key="tag.id"
			:value="tag"
			:to="{ path: '/search/' + tag.name }"
			v-model="tag.isOpen"
			color="primary"
			outlined
			close
			@click:close="remove(tag.id)">
			{{ tag.name }}
		</v-chip>
        </v-list-group>
    </v-list>
</template>

<script>
export default {
	name: "MyInterest",
	data() {
		return {
			isGuest: false,
			interests: [
				//사용자 태그
				{
					id: 1,
					name: "요리",
				},
				{
					id: 2,
					name: "운동",
				},
				{
					id: 3,
					name: "IT",
				},
				{
					id: 4,
					name: "인테리어",
				},
			],
			index: 1,
		};
	},
	methods: {
		clickLoginBtn() {
			this.$router.push("/login");
		},
		remove(id) {
			// 여기서 delete로 삭제된 태그 id마 보냄
			this.interests.splice(id - this.index, 1);
			this.index++; //카운트 해줘야 다음 태그 제대로 지워짐
			// 이렇게 하고, 페이지 refresh 해서 태그 다시 받아와야함.....
			alert(id);
			alert(id - 1);
			this.interests = [...this.interests];
		},
	},
};
</script>

<style scoped>
.interest-wrapper {
  margin-bottom: 10px;
}
.main-side-menu-contents {
	text-align: center;
	margin: 1%;
	padding-left: 5%;
	padding-right: 7%;
}
.main-side-menu-btn-container {
	margin: 5%;
	padding-left: 30%;
}
.title-width {
	width: 100%;
	background: transparent;
}
</style>
