<template>
	<!-- 태그바 fixed 잡고 포지션 고정시키면 됨-->
	<v-app-bar
		fixed
		flat
		outlined
		elevation="0"
		:color="$vuetify.theme.dark ? '#424242' : 'grey lighten-4'"
		app
	>
		<v-toolbar-items>
			<v-app-bar-nav-icon
				slot="activator"
				@click.stop="drawer = !drawer"
				class="hidden-sm-and-up"
			></v-app-bar-nav-icon>
		</v-toolbar-items>
		<!--logo-->
		<v-toolbar-title class="v-toolbar-title font-weight" @click="goMain"
			>Challympic</v-toolbar-title
		>
		<v-spacer />
		<!--tag랑 사용자랑 join table 필요할 것 같다:아니 이거 처음[]리턴 왜 안되는데..-->
		<div class="main-toolbar-search hidden-md-and-down">
			<v-autocomplete
				v-model="searchInput"
				:items="dynamicArr"
				:search-input.sync="search"
				hide-selected
				hide-details="true"
				label=""
				v-click-outside="onClickOutside"
				:close="onClickOutside"
				rounded
				small-chips
				@change="keywordSearch"
				@keyup="test"
				@keyup.delete="test"
				@keydown.enter="keywordSearch"
				class="ml-5"
				flat
				solo
				item-text="key"
				item-value="key"
				clearable
				append-icon="mdi-magnify"
				width="600px"
			>
				<template v-slot:no-data>
					<v-list-item>
						<v-list-item-content>
							<v-list-item-title>
								입력된
								<kbd>
									<strong>{{ search }}</strong> </kbd
								>태그 또는 사용자가 존재하지 않습니다.
							</v-list-item-title>
						</v-list-item-content>
					</v-list-item>
				</template>
				<template v-slot:selection="data">
					<v-chip
						v-bind="data.attrs"
						:search="data.selected"
						@click="data.select"
					>
						{{ data.item.key }}
					</v-chip>
				</template>
			</v-autocomplete>
		</div>
		<v-spacer></v-spacer>
		<!--search on small screen -->
		<div class="main-toolbar-search hidden-sm-and-up">
			<v-menu
				open-on-hover
				offset-y
				v-model="menu"
				v-click-outside="onClickOutside"
				:close="onClickOutside"
				:close-on-content-click="false"
				min-width="220px"
			>
				<template v-slot:activator="{ on, attrs }">
					<v-btn v-bind="attrs" v-on="on" icon>
						<v-icon>mdi-magnify</v-icon>
					</v-btn>
				</template>

				<v-autocomplete
					v-model="mobileSearchInput"
					:items="dynamicArr"
					:search-input.sync="mobileSearch"
					label=""
					small-chips
					flat
					item-text="key"
					item-value="key"
					solo
					hide-details="true"
					clearable
					append-icon="mdi-magnify"
					@change="mobileKeywordSearch"
					@keyup="mobiletest"
					@keyup.delete="mobiletest"
				>
					<template v-slot:no-data>
						<v-list-item>
							<v-list-item-content>
								<v-list-item-title> 검색결과 없음 </v-list-item-title>
							</v-list-item-content>
						</v-list-item>
					</template>
					<!--
							@click="data.select"
							-->

					<template v-slot:selection="data">
						<v-chip
							v-bind="data.attrs"
							:search="data.selected"
							@click="data.select"
						>
							{{ data.item.key }}
						</v-chip>
					</template>
				</v-autocomplete>
			</v-menu>
		</div>

		<v-btn
			v-if="!isLoggedIn"
			@click="clickLoginBtn"
			color="primary"
			outlined
			small
			x-small
			>로그인</v-btn
		>

		<div v-if="!isMobile() && isLoggedIn">
			<v-menu bottom left offset-y max-height="260" width="300" display="block">
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on">
						<div v-if="activeAlert" id="alert-badge"></div>
						<v-icon> mdi-bell-outline </v-icon>
					</v-btn>
				</template>

				<v-card v-if="alertMenu.length > 1" width="300">
					<v-list class="overflow-y-auto">
						<v-list-item
							v-for="(item, i) in alertMenu"
							:key="i"
							:to="item.link1"
							class="px-3; mx-1;,my-2"
						>
							<v-list-item-title>
								{{ item.title }}
								<span class="date-text">{{ dayjsRegDate(item.regDate) }}</span>
							</v-list-item-title>
						</v-list-item>
					</v-list>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn text @click="deleteAlert"> 모두 읽음 </v-btn>
					</v-card-actions>
				</v-card>
				<v-card v-else>
					<v-list>
						<v-list-item>
							<v-list-item-title>새로운 알림이 없습니다.</v-list-item-title>
						</v-list-item>
					</v-list>
				</v-card>
			</v-menu>
			<v-menu bottom left offset-y display="block">
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on">
						<v-icon>mdi-account-circle</v-icon>
					</v-btn>
				</template>

				<v-list>
					<v-list-item
						v-for="(item, i) in profileMenu"
						:key="i"
						:to="item.link1"
						@click="handleProfileMenuClick(i)"
					>
						<v-list-item-title>{{ item.title }}</v-list-item-title>
					</v-list-item>
				</v-list>
			</v-menu>
		</div>
		<div v-if="isMobile() && isLoggedIn">
			<v-menu
				bottom
				center
				offset-y
				max-height="260"
				width="210"
				display="block"
			>
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on">
						<div v-if="activeAlert" id="alert-badge"></div>
						<v-icon> mdi-bell-outline </v-icon>
					</v-btn>
				</template>

				<v-card v-if="alertMenu.length > 1" width="210">
					<v-list class="overflow-y-auto">
						<v-list-item
							v-for="(item, i) in alertMenu"
							:key="i"
							:to="item.link1"
						>
							<v-list-item-title class="text-caption">
								{{ item.title }}
								<span class="date-text-mobile">{{
									dayjsRegDate(item.regDate)
								}}</span>
							</v-list-item-title>
						</v-list-item>
					</v-list>
					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn text @click="deleteAlert"> 모두 읽음 </v-btn>
					</v-card-actions>
				</v-card>
				<v-card v-else>
					<v-list>
						<v-list-item>
							<v-list-item-title class="text-caption"
								>새로운 알림이 없습니다.</v-list-item-title
							>
						</v-list-item>
					</v-list>
				</v-card>
			</v-menu>
			<v-menu bottom left offset-y width="300px">
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on">
						<v-icon>mdi-account-circle</v-icon>
					</v-btn>
				</template>

				<v-list>
					<v-list-item
						v-for="(item, i) in profileMenu"
						:key="i"
						:to="item.link2"
						@click="handleProfileMenuClick(i)"
					>
						<v-list-item-title>{{ item.title }}</v-list-item-title>
					</v-list-item>
				</v-list>
			</v-menu>
		</div>

		<!-- Add a navigation bar -->
		<v-navigation-drawer
			hide-overlay
			v-model="drawer"
			absolute.
			width="70%"
			height="100vh"
			clipped
			class="hidden-sm-and-up"
			app
		>
			<v-card-title><h3>Challympic</h3></v-card-title>
			<v-container>
				<side-contents />
			</v-container>
		</v-navigation-drawer>
	</v-app-bar>
</template>

<script>
	import SideContents from "@/components/layout/SideContents.vue";
	import fromNow from "@/plugins/dayjs.js";
	import { getSearchList } from "@/api/search.js";

	export default {
		name: "ToolBar",
		components: { SideContents },
		data() {
			return {
				drawer: false,
				onSelect: false,
				loading: false,
				search: "",
				mobileSearch: "",
				searchInput: "",
				mobileSearchInput: "",
				active: false,
				menu: false,
				profileMenu: [
					{
						title: "프로필 설정",
						link1: "/user/account/:userNo/",
						link2: "/mobile/user/account/:userNo/",
					},
					{
						title: "내 피드",
						link1: "/feed/:userNo/",
						link2: "/feed/:userNo/",
					},
					{
						title: "관리자 페이지",
						link1: "/admin/",
						link2: "/admin/",
					},
					{
						title: "로그아웃",
					},
					{ title: "Click Me 2" },
				],
				alertMenu: [],
				tags: [],
				empty: [],
				dynamicArr: [],
				obj1: [],
				obj2: [],
				temp: [],
			};
		},
		watch: {
			group() {
				this.drawer = false;
			},
		},
		computed: {
			activeAlert() {
				if (this.alertMenu.length > 1) {
					return true;
				}
				return false;
			},
			isLoggedIn() {
				return this.$store.state.userStore.isLoggedIn;
			},
		},
		methods: {
			test() {
				if (this.search.trim().length > 0) this.dynamicArr = this.tags;
				else if (this.search.length == 0) this.dynamicArr = this.empty;
			},
			mobiletest() {
				if (this.mobileSearch.trim().length > 0) this.dynamicArr = this.tags;
				else if (this.mobileSearch.length == 0) this.dynamicArr = this.empty;
			},
			dayjsRegDate(regDate) {
				return fromNow(regDate);
			},
			menuItems() {
				return this.menu;
			},
			onClickOutside() {
				this.active = false;
			},
			closeBox() {
				this.onSelect = true;
			},
			goMain() {
				this.$router.push("/");
			},
			clickLoginBtn() {
				this.$router.push("/login");
			},
			handleProfileMenuClick(i) {
				if (this.profileMenu[i].title === "로그아웃") {
					console.log("로그아웃");
					localStorage.removeItem("Authorization");
					this.$store.commit("userStore/LOGOUT");
					this.$router.push("/login");
				}
			},
			deleteAlert() {
				this.menu = false;
				setTimeout(() => {
					this.alertMenu = [];
				}, 500);
			},
			keywordSearch(val) {
				var to = val.substring(1);
				let searchCategory = this.searchInput.charAt(0);
				if (searchCategory === "@") {
					var foundValue = this.obj2.filter((obj) => obj.key == val);
					to = foundValue[0].no; // : feed 번호
					this.dynamicArr = this.empty;
					this.$router.push("/feed/" + to);
				} else if (searchCategory === "#") {
					this.dynamicArr = this.empty;
					this.$router.push("/search/" + to);
				}
			},
			mobileKeywordSearch() {
				var to = this.mobileSearchInput.substring(1);
				let searchCategory = this.mobileSearchInput.charAt(0);
				alert(this.mobileSearchInput);
				if (searchCategory === "@") {
					var foundValue = this.obj2.filter(
						(obj) => obj.key == this.mobileSearchInput
					);
					this.mobileSearchInput = "";
					to = foundValue[0].no; // : feed 번호
					this.dynamicArr = this.empty;
					this.$router.push("/feed/" + to);
				} else if (searchCategory === "#") {
					this.mobileSearchInput = "";
					this.dynamicArr = this.empty;
					//	this.$router.push("/search/" + to);
					window.location.href = "search/" + to;
				}
			},
			isMobile() {
				if (
					/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
						navigator.userAgent
					)
				) {
					return true;
				} else {
					return false;
				}
			},
		},
		created() {
			getSearchList(
				(response) => {
					this.obj1 = response.data.data.tagList;
					this.obj2 = response.data.data.userList;
					renameKeyInObjArray(this.obj1, "tag_no", "no", "tag_content", "key");
					renameKeyInObjArray(
						this.obj2,
						"user_no",
						"no",
						"user_nickname",
						"key"
					);
					delete this.obj2[0];
					this.tags = this.obj1.concat(this.obj2);
				},
				(error) => {
					console.log(error);
				}
			);
		},
	};
	//작동하면 나중에 다 빼자..
	function renameKeyInObjArray(array, oldKey, newKey, oldKey2, newKey2) {
		return array.map(function (obj) {
			if (oldKey2 == "user_nickname")
				obj["user_nickname"] = "@" + obj["user_nickname"];
			Object.defineProperty(
				obj,
				newKey,
				Object.getOwnPropertyDescriptor(obj, oldKey)
			);
			Object.defineProperty(
				obj,
				newKey2,
				Object.getOwnPropertyDescriptor(obj, oldKey2)
			);
			delete obj[oldKey];
			delete obj[oldKey2];
			return obj;
		});
	}
</script>
<style>
	.v-toolbar-title:hover {
		cursor: pointer;
	}
	.v-text-field {
		width: 600px;
	}
	.v-toolbar__content,
	.v-toolbar__extension {
		padding: 0;
	}
	.date-text-mobile {
		font-size: 6px;
		color: rgb(160, 160, 160);
	}
	.date-text {
		font-size: 13px;
		color: rgb(160, 160, 160);
	}
	.font-weight {
		font-weight: bold;
	}
	#alert-badge {
		position: absolute;
		top: 2px;
		right: 13px;
		width: 10px;
		height: 10px;
		background-color: red;
		border-radius: 50%;
		z-index: 1;
	}
	::v-deep .v-list-item {
		padding: 2px 16px;
	}
	::v-deep .v-list-item__title {
		white-space: normal;
	}
	.v-menu__content::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px #5d5d5d;
		background-color: #5d5d5d;
	}
	.v-menu__content::-webkit-scrollbar {
		width: 0px;
	}
	.v-menu__content::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px #424242;
		background-color: #424242;
	}
	.v-list-item-content__text::-webkit-scrollbar-track {
		-webkit-box-shadow: inset 0 0 6px #5d5d5d;
		background-color: #5d5d5d;
	}
	.v-list-item-content__text::-webkit-scrollbar {
		width: 0px;
	}
	.v-list-item-content__text::-webkit-scrollbar-thumb {
		-webkit-box-shadow: inset 0 0 6px #424242;
		background-color: #424242;
	}
</style>
