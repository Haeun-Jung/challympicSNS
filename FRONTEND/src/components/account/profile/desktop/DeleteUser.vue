<template>
	<v-card height="88vh" elevation="0">
		<v-card-title>회원 탈퇴</v-card-title>
		<v-divider />
		<v-container>
			<v-row md="6">
				<v-col md="1"> </v-col>
				<v-col md="10">
					<v-card elevation="0" outlined>
						<v-card-text
							>회원 탈퇴 시 개인정보 및 CHALLYMPIC에서 만들어진 모든 데이터는
							삭제됩니다.
							<br/>
							회원정보 및 챌린지 등 개인형 서비스 이용기록은 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다.
							<br/>
							삭제되기 전, 필요한 데이터는 미리 백업을 해주세요.</v-card-text
						>
					</v-card>
				</v-col>
			</v-row>

			<v-row>
				<v-col md="1" />
				<v-col md="8">
					<v-text-field
						label="탈퇴하시려면 ''탈퇴하겠습니다.'' 문구를 작성해주세요"
						v-model="optOutText"
						dense
						outlined
						clearable
						value=""
						:rules="[rules1.matchOptOut]"
						@keyup="checkOpt"
					></v-text-field>
				</v-col>
				<v-col md="2">
					<v-btn
						class="text-none"
						depressed
						color="error"
						@click="onSubmit"
						:disabled="disabledTrue"
					>
						회원 탈퇴</v-btn
					>
				</v-col>

				<v-spacer />
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
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
		computed: {
			...mapState(userStore, ["userInfo"]),
		},
		methods: {
			...mapActions(userStore, ["deleteUser"]),
			onSubmit() {
				this.deleteUser(sessionStorage.getItem("Authorization"));
				this.$store.commit('userStore/LOGOUT');
				window.location.href="/";
			},
			checkOpt() {
				if (this.optOutText === "탈퇴하겠습니다.") {
					this.disabledTrue = false;
				} else {
					this.disabledTrue = true;
				}
			},
		},
	};
</script>

<style></style>
