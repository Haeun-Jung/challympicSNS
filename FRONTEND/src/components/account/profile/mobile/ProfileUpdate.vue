<template>
	<v-card height="100%" width="94%" class="justify-center">
		<v-card-title class="justify-center">프로필 수정</v-card-title>
		<v-divider />
		<v-container class="mobile-profile-delete-user-container">
			<v-row>
				<v-col md="1" />
			</v-row>
			<v-row class="mobile-profile-delete-user-container">
				<v-col md="6" class="profile-setting-avatar-container">
					<v-avatar size="150">
						<img :src="profile" alt="profile" />
					</v-avatar>
					<v-container>
						<v-btn
							color="primary"
							class="text-none"
							depressed
						>
							<label for="file">이미지 선택</label>
						</v-btn>
						<input type="file" id="file" ref="file" @change="imageUpload" multiple />
						<!--<profile-upload-button />-->
					</v-container>
				</v-col>
				<v-col>
					<v-container>
						<v-row class="row-bottom">
							<!-- 타이틀 -->
							<v-col md="4">
								<v-list-item-title>대표 타이틀</v-list-item-title>
							</v-col>
							<v-col>
								<!--label에는 후에 selected 에 user.title로 가져오기-->
								<v-select
									:items="userInfo.titles"
									:label="userInfo.user_title"
									dense
									outlined
									@click="titleChange(item)"
								></v-select>
							</v-col>
						</v-row>
						<!-- 닉네임 -->
						<v-row class="row-bottom">
							<v-col md="4">
								<v-list-item-title>닉네임</v-list-item-title>
							</v-col>
							<v-col>
								<v-text-field
									:rules="nicknameRules"
									:success-messages="nicknameSuccess"
									:disabled="
									duplicateNicknameCheck && possibleNickname ? true : false
									"
									:label="userInfo.user_nickname"
									v-model="nickname"
									dense
									outlined
									clearable
								></v-text-field>
							</v-col>
						</v-row>
						<v-row>
							<v-spacer />
							<v-btn text color="primary" width="45px" @click="checkNickname">
								중복확인
							</v-btn>
						</v-row>
						<!-- 이메일 -->
						<v-row>
							<v-col md="4">
								<v-list-item-title>이메일</v-list-item-title>
							</v-col>
							<v-col>
								<v-list-item-title>{{userInfo.user_email}}</v-list-item-title>
							</v-col>
						</v-row>
						
						
						<!-- 내 관심사 -->

						<v-row>
							<v-col md="4">
								<v-list-item-title
									>내 관심사
									<!-- icon 누를 시 모달 창 pop up-->
									<v-dialog v-model="dialog" width="500">
										<template v-slot:activator="{ on, attrs }">
											<v-btn icon v-bind="attrs" v-on="on"
												><v-icon>mdi-heart-plus-outline</v-icon></v-btn
											>
										</template>

										<v-card>
											<v-card-title
												class="text-h6"
												:color="$vuetify.theme.dark ? '#424242' : '#FAFAFA'"
											>
												관심사 추가
											</v-card-title>

											<v-card-text>
												<v-container fluid>
													<v-combobox
														v-model="model"
														:items="AllTags"
														:search-input.sync="search"
														hide-selected
														hint="최대 5가지 태그 추가 가능"
														label=""
														multiple
														persistent-hint
														small-chips
													>
														<template v-slot:no-data>
															<v-list-item>
																<v-list-item-content>
																	<v-list-item-title>
																		입력된 "<strong>{{ search }}</strong
																		>" 태그가 존재하지 않습니다.
																		<kbd>enter</kbd> 를 눌러 새로운 태그를
																		추가하십시오.
																	</v-list-item-title>
																</v-list-item-content>
															</v-list-item>
														</template>
													</v-combobox>
												</v-container>
											</v-card-text>

											<v-divider></v-divider>

											<v-card-actions>
												<v-spacer></v-spacer>
												<v-btn color="primary" text @click="saveInterest">
													저장
												</v-btn>
												<v-btn color="primary" text @click="dialog = false">
													취소
												</v-btn>
											</v-card-actions>
										</v-card>
									</v-dialog>
								</v-list-item-title>
							</v-col>
						</v-row>

						<v-row>
							<v-col>
								<!-- 템플릿으로 chips 뿌림-->
								<v-chip
									v-for="tag in listInterest"
									:key="tag.tag_no"
									:value="tag"
									v-model="tag.isOpen"
									color="primary"
									outlined
									close
									@click:close="remove(tag.tag_no)"
								>
									{{ tag.tag_content }}
								</v-chip>
							</v-col>
						</v-row>
					</v-container>
				</v-col>
			</v-row>
			
			<v-row class="mobile-profile-delete-user-container page-bottom">
				<v-spacer />
				<v-btn
					class="text-none"
					depressed
					color="primary"
					@click="onSubmit"
				>
					회원 정보 수정</v-btn
				>
			</v-row>
			<v-row class="mobile-profile-delete-user-container">
				<v-spacer />
				<div class="alert-message">{{ this.alertMsg }}</div>
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";
	// import ProfileUploadButton from "@/components/button/ProfilelUploadButton.vue";
	const userStore = "userStore";
	export default {
		// components: { ProfileUploadButton },
		//props: [selectedFile],
		computed: {
			...mapState(userStore, ["userInfo", "listInterest"]),
			possibleNickname() {
				return this.$store.state.userStore.possibleNickname;
			},
			nicknameRules() {
			return [
					(v) => !!v || "닉네임을 입력해주세요.",
					(v) =>
					/^[가-힣a-zA-Z0-9].{1,10}$/.test(v) ||
					"한글/영문/숫자를 이용하여 2~10자로 입력해주세요.",
					this.duplicateNicknameCheck || "중복 여부를 확인해주세요.",
					this.possibleNickname || "사용할 수 없는 닉네임입니다.",
				];
			},
			nicknameSuccess() {
			if (!this.duplicateNicknameCheck || !this.possibleNickname) {
				return;
			}
			return "사용 가능한 닉네임입니다.";
			},
		},
		data() {
			return {
				nickname: null,
				nicknameValidation: false,
				duplicateNicknameCheck: false,
				profile : null,
				titles: ["밥 잘먹는", "타이틀2"],
				title: null,
				dialog: false,
				disabledTrue: false,
				AllTags: ["#Gaming", "#Programming", "#Vue", "#Vuetify"], //전체 태그
				model: [],
				search: "",
				file: {}, //업로드용 파일
				changeFile: {}, //업로드용 파일
				filePreview: {},
				formData: '',
				alertMsg: '',
			};
		},
		/* 프로필 이미지 설정 */
		mounted() {
			this.profile  = 
			"http://d384sk7z91xokb.cloudfront.net/" + 
			this.$store.state.userStore.filePath + "/" + 
			this.$store.state.userStore.fileSavedName;
		},
		methods: {
			...mapActions(userStore, ["getUserInfo", "getInterest", "modifyUser"]),
			onSubmit() {
				if (this.nickname != null && !this.duplicateNicknameCheck) {
					this.alertMsg = '닉네임 중복체크를 해주세요.';
					return;
				}
				if (!this.possibleNickname && this.nickname != null) {
					this.alertMsg = '현재 사용중인 닉네임입니다.';
					return;
				}
				/* 사진 변경 X */
				if (this.profile == "http://d384sk7z91xokb.cloudfront.net/" + 
									this.$store.state.userStore.filePath + "/" + 
									this.$store.state.userStore.fileSavedName) {
					if (this.nickname == null && this.title == null) {
						this.profile  = "http://d384sk7z91xokb.cloudfront.net/" + 
										this.$store.state.userStore.filePath + "/" + 
										this.$store.state.userStore.fileSavedName;
						return;
					}
					if (this.nickname == null) this.nickname = this.userInfo.user_nickname;
					if (this.title == null) this.title = this.userInfo.user_title;
					let formData = new FormData();
					formData.append("user_nickname", this.nickname);
					formData.append("user_title", this.title);
					this.modifyUser({file: formData, token: localStorage.getItem("Authorization")});
				}
				/* 사진 변경 O */ 
				else {
					if (this.nickname == null) this.nickname = this.userInfo.user_nickname;
					if (this.title == null) this.title = this.userInfo.user_title;
					this.formData.append("user_nickname", this.nickname);
					this.formData.append("user_title", this.title);
					this.modifyUser({file: this.formData, token: localStorage.getItem("Authorization")});
				}
				this.duplicateNicknameCheck = true;
				this.alertMsg = "회원정보가 변경되었습니다."
				// setTimeout(() => {
				// 	window.location.reload();
				// }, 300);
			},
			remove(no) {
				this.$store.dispatch('userStore/deleteInterest', { no, token: localStorage.getItem('Authorization') })
				setTimeout(() => {
					this.getInterest(localStorage.getItem("Authorization"));
				}, 300);
			},
			changed() {
				this.$emit("changed", this.attrArr);
			},
			checkNickname() {
				if (this.nickname == null) return;
				if (/^[가-힣a-zA-Z0-9].{1,10}$/.test(this.nickname)) this.nicknameValidation = true;
				if (this.nicknameValidation) {
					this.$store.dispatch('userStore/confirmNickname', this.nickname);
					this.duplicateNicknameCheck = true;
					//if (this.possibleNickname) // this.disabledTrue = false;
				}
			},
			saveInterest() {
				alert("save to list");
				this.dialog = false;
				this.disabledTrue = false;

				//api 요청 -> 현재 리스트 보내기
				//refresh하는것두...
			},
			titleChange(title) {
				this.title = title;
			},
			imageUpload() {
				let formData = new FormData();
      			formData.append("file", this.$refs.file.files[0]);
				this.formData = formData;
				this.changeFile = {
						//실제 파일
						file: this.$refs.file.files,
						//이미지 프리뷰
						preview: URL.createObjectURL(this.$refs.file.files[0]),
					}
				// 이미지 업로드용 프리뷰
				this.filePreview =  { file: URL.createObjectURL(this.$refs.file.files[0])}
				console.log(this.changeFile.preview);
				this.profile = this.changeFile.preview;
				// console.log(this.filesPreview);
			},
		},
		watch: {
			model(val) {
				if (val.length > 5) {
					this.$nextTick(() => this.model.pop());
				}
			},
		},
	};
</script>

<style>
.profile-setting-avatar-container {
	justify-content: center !important;
	flex-direction: row !important;
	text-align: center !important;
	align-items: center !important;
}

.row-bottom {
	margin-bottom: -40px;
}
.alert-message {
	color: #DB3425;
	font-weight: bold;
	text-align: right;
	padding-right: 0px;
	margin-top: -10px;
	margin-right: 0px;
	margin-bottom: 6px;
}
.page-bottom {
	margin-bottom: 20px;
}
</style>
