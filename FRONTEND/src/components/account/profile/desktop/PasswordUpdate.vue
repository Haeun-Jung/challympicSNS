<template>
	<v-card height="100vh">
		<v-card-title>비밀번호 수정</v-card-title>
		<v-divider />
		<v-container>
			<v-row md="2" class="row-bottom">
				<!-- 사진 이상해서 뺐어요.. 우측 정렬도 이상해요.. class="text-right align-self-start"-->
				<v-col md="1"> </v-col>
				<v-col md="3">
					<v-list-item-title>이전 비밀번호</v-list-item-title>
				</v-col>
				<v-col>
					<v-text-field
						label=""
						v-model="originalPassword"
						dense
						outlined
						clearable
						:append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
						:rules="[rules1.required, rules1.originMatch]"
						:type="show1 ? 'text' : 'password'"
						name="input-10-2"
						value=""
						@click:append="show1 = !show1"
					></v-text-field>
				</v-col>
				<v-col md="3"></v-col>
			</v-row>
			<v-row md="2" class="row-bottom">
				<!-- 새 비번.. -->
				<v-col md="1"> </v-col>
				<v-col md="3">
					<v-list-item-title>새 비밀번호</v-list-item-title>
				</v-col>
				<v-col>
					<v-text-field
						label=""
						v-model="newPassword"
						dense
						outlined
						clearable
						:rules="[rules2.required, rules2.min, rules2.special]"
						type="password"
						name="input-10-2"
						value=""
					></v-text-field>
				</v-col>
				<v-col md="3"></v-col>
			</v-row>
			<v-row md="2">
				<!-- removed error from default.. -->
				<v-col md="1"> </v-col>
				<v-col md="3">
					<v-list-item-title>새 비밀번호 확인</v-list-item-title>
				</v-col>
				<v-col>
					<v-text-field
						label=""
						v-model="passwordCheck"
						dense
						outlined
						clearable
						:rules="[rules3.required, rules3.passwordMatch]"
						type="password"
						name="input-10-2"
						value=""
					></v-text-field>
				</v-col>
				<v-col md="3"></v-col>
			</v-row>
			<v-row>
				<v-spacer />
				<v-btn
					class="text-none"
					depressed
					color="primary"
					@click="onSubmit"
					:disabled="disabledTrue"
				>
					비밀번호 수정</v-btn
				>
				<v-col md="1" />
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
	export default {
		name: "PasswordUpdate",
		data() {
			return {
				profileUrl: "https://cdn.vuetifyjs.com/images/john.jpg",
				password: "Password",
				passwordCheck: "",
				originalPassword: "",
				newPassword: "",
				disabledTrue: false,
				show1: false,
				rules1: {
					required: (value) => !!value || "기존 비밀번호를 입력해주세요.",
					originMatch: (v) =>
						v === this.password || "기존 비밀번호와 일치하지 않습니다.",
					//min: (v) => v.length >= 8 || "Min 8 characters",
					//originMatch: () => `기존 비밀번호가 잘못 입력되었습니다.`,
				},
				rules2: {
					required: (value) => !!value || "새 비밀번호를 입력해주세요.",
					min: (v) =>
						v.length >= 8 || "비밀번호는 영문, 특수문자 포함 8자 이상입니다.",
					special: (v) =>
						/([!@$%])/.test(v) ||
						"비밀번호는 영문, 특수문자 포함 8자 이상입니다.",
				},
				rules3: {
					required: (value) =>
						!!value || "새 비밀번호를 다시 한번 입력해주세요.",
					passwordMatch: (v) =>
						v === this.newPassword || "새 비밀번호와 일치하지 않습니다.",
				},
			};
		},
		methods: {
			onSubmit() {
				alert(this.newPassword);
			},
		},
	};
</script>

<style></style>
