<template>
	<v-card height="90vh" width="100%" class="justify-center">
		<v-card-title class="justify-center">회원 탈퇴</v-card-title>
		<v-divider />
		<v-container class="mobile-profile-delete-user-container">
			<v-row>
				<v-col md="1" />
			</v-row>
			<v-row class="mobile-profile-delete-user-container">
				<v-col>
					<v-card-subtitle
						>탈퇴하시려면 ''탈퇴하겠습니다.'' 문구를
						작성해주세요</v-card-subtitle
					>
					<v-text-field
						v-model="optOutText"
						dense
						outlined
						clearable
						d-flex
						value=""
						:rules="[rules1.matchOptOut]"
						@keyup="checkOpt"
					></v-text-field>
				</v-col>
			</v-row>
			<v-row class="mobile-profile-delete-user-container">
				<v-spacer />
				<v-btn
					class="text-none"
					round
					depressed
					color="error"
					@click="onSubmit"
					:disabled="disabledTrue"
				>
					회원 탈퇴</v-btn
				>
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
	export default {
		name: "PasswordUpdate",
		data() {
			return {
				optOutText: "",
				disabledTrue: true,
				rules1: {
					matchOptOut: (v) =>
						v === "탈퇴하겠습니다." || "회원탈퇴 문구가 일치하지 않습니다.",
				},
			};
		},
		methods: {
			onSubmit() {
				//	alert(this.optOutText);
				// api로 유저넘버 보내기
			},
			checkOpt() {
				if (this.optOutText === "탈퇴하겠습니다.") {
					this.disabledTrue = false;
					alert("eye");
				} else {
					this.disabledTrue = true;
				}
			},
		},
	};
</script>

<style>
	.mobile-profile-delete-user-container {
		padding-right: 5%;
		padding-left: 5%;
	}
</style>
