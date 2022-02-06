<template>
	<v-card height="100vh">
		<v-card-title>개인 정보 수정</v-card-title>
		<v-divider />
		<v-container>
			<v-row md="2">
				<v-col md="4" class="profile-setting-avatar-container">
					<v-avatar size="150">
						<img :src="profileUrl" alt="John" />
					</v-avatar>
					<v-container>
						<profile-upload-button />
					</v-container>
				</v-col>
				<v-col>
					<v-container>
						<!-- 이름 -->
						<v-row>
							<v-col md="4">
								<!-- 후에 {{user.name}}-->
								<v-list-item-title>이름</v-list-item-title>
							</v-col>
							<v-col>
								<v-list-item-title>김싸피</v-list-item-title>
							</v-col>
						</v-row>
						<!-- 이메일 -->
						<v-row>
							<v-col md="4">
								<!-- 후에 {{user.email}}-->
								<v-list-item-title>이메일</v-list-item-title>
							</v-col>
							<v-col>
								<v-list-item-title>ssafy@gmail.com</v-list-item-title>
							</v-col>
						</v-row>
						<v-row class="row-bottom">
							<!-- 타이틀 -->
							<v-col md="4">
								<v-list-item-title>대표 타이틀</v-list-item-title>
							</v-col>
							<v-col>
								<!--label에는 후에 selected 에 user.title로 가져오기-->
								<v-select
									:items="titles"
									label=""
									dense
									outlined
									@click="titleChange"
								></v-select>
							</v-col>
							<v-col md="2" />
						</v-row>
						<!-- 닉네임 -->
						<v-row class="row-bottom">
							<v-col md="4">
								<v-list-item-title>닉네임</v-list-item-title>
							</v-col>
							<v-col>
								<v-text-field
									label=""
									v-model="nickname"
									dense
									outlined
									clearable
								></v-text-field>
							</v-col>
							<v-col md="2">
								<v-btn text color="primary" width="45px" @click="nicknameCheck"
									>중복확인</v-btn
								>
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
							<v-col>
								<!-- 템플릿으로 chips 뿌림-->
								<v-chip
									v-for="tag in interests"
									:key="tag.id"
									:value="tag"
									v-model="tag.isOpen"
									color="primary"
									outlined
									close
									@click:close="remove(tag.id)"
								>
									<!-- {{tag.tagContent}}-->
									{{ tag.name }}
								</v-chip>
							</v-col>
							<v-col md="1"> </v-col>
						</v-row>
					</v-container>
				</v-col>
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
					회원 정보 수정</v-btn
				>
				<v-col md="1" />
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
	import ProfileUploadButton from "@/components/button/ProfilelUploadButton.vue";
	export default {
		components: { ProfileUploadButton },
		//props: [selectedFile],
		data() {
			return {
				profileUrl: "https://cdn.vuetifyjs.com/images/john.jpg", //후에 유저테이블에서 가져오기
				titles: ["밥 잘먹는", "타이틀2"],
				nickname: "nickname",
				dialog: false,
				search: "",
				disabledTrue: true,
				AllTags: ["#Gaming", "#Programming", "#Vue", "#Vuetify"], //전체 태그
				model: [],
				interests: [
					//사용자 태그
					{
						id: 1,
						name: "Work",
					},
					{
						id: 2,
						name: "Food",
					},
					{
						id: 3,
						name: "Art",
					},
					{
						id: 4,
						name: "Shopping",
					},
					{
						id: 5,
						name: "Tech",
					},
					{
						id: 6,
						name: "Home Improvement",
					},
				],
				index: 1,
			};
		},
		methods: {
			onSubmit() {
				//Submit 눌렀을 떄, 파일 명, 파일 이름 프롭스해서 보내기
			},
			remove(id) {
				// 여기서 delete로 삭제된 태그 id마 보냄
				this.interests.splice(id - this.index, 1);
				this.index++; //카운트 해줘야 다음 태그 제대로 지워짐
				// 이렇게 하고, 페이지 refresh 해서 태그 다시 받아와야함.....
				alert(id);
				alert(id - 1);
				this.interests = [...this.interests];
				this.disabledTrue = false;
			},
			changed() {
				this.$emit("changed", this.attrArr);
			},
			nicknameCheck() {
				alert("중복확인처리");
				//닉네임 바꿨다면 ->
				this.disabledTrue = false;
			},
			saveInterest() {
				alert("save to list");
				this.dialog = false;
				this.disabledTrue = false;

				//api 요청 -> 현재 리스트 보내기
				//refresh하는것두...
			},
			titleChange() {
				this.disabledTrue = false;
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
</style>
