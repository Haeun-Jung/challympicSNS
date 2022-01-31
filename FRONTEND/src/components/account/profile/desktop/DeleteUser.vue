<template>
	<v-card height="100vh">
		<v-card-title>회원 탈퇴</v-card-title>
		<v-divider />
		<v-container>
			<v-row md="6">
				<v-col md="1"> </v-col>
				<v-col md="10">
					<v-card elevation="0" outlined>
						<v-card-text
							>회원 탈퇴 시 개인정보 및 CHALLYMPIC에서 만들어진 모든 데이터는
							삭제됩니다. 이부분..시간남을때..Lorem ipsum, or lipsum as it is
							sometimes known, is dummy text used in laying out print, graphic
							or web designs. The passage is attributed to an unknown typesetter
							in the 15th century who is thought to have scrambled parts of
							Cicero's De Finibus Bonorum et Malorum for use in a type specimen
							book. It usually begins with: “Lorem ipsum dolor sit amet,
							consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
							labore et dolore magna aliqua.” The purpose of lorem ipsum is to
							create a natural looking block of text (sentence, paragraph, page,
							etc.) that doesn't distract from the layout. A practice not
							without controversy, laying out pages with meaningless filler text
							can be very useful when the focus is meant to be on design, not
							content.</v-card-text
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
						round
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

<style></style>
