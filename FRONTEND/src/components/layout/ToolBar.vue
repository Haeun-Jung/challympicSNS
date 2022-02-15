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
		<div class="main-toolbar-search" v-if="!isMobile()">
			<desktop-search-bar :tags="tags" @searchInput="childSearch" />
		</div>
		<v-spacer></v-spacer>
		<!--search on small screen -->
		<div v-if="isMobile()">
			<mobile-search-bar :tags="tags" />
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
			<!-- 알림 버튼 위치 -->
			<alert-button />
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
			<alert-button />
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
	import AlertButton from "@/components/button/AlertButton.vue";
	import DesktopSearchBar from "@/components/layout/util/desktop/SearchBar.vue";
	import MobileSearchBar from "@/components/layout/util/mobile/SearchBar.vue";
	import { getSearchList } from "@/api/search.js";

	export default {
		name: "ToolBar",
		components: {
			SideContents,
			AlertButton,
			DesktopSearchBar,
			MobileSearchBar,
		},
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
			isLoggedIn() {
				return this.$store.state.userStore.isLoggedIn;
			},
			profileMenu() {
				let menu = [];
				if (this.$store.state.userStore.userInfo.auth === "USER") {
					menu.push(
						{
							title: "프로필 설정",
							link1:
								"/user/account/" +
								this.$store.state.userStore.userInfo.user_no +
								"/",
							link2:
								"/mobile/user/account/" +
								this.$store.state.userStore.userInfo.user_no +
								"/",
						},
						{
							title: "내 피드",
							link1:
								"/feed/" + this.$store.state.userStore.userInfo.user_no + "/",
							link2:
								"/feed/" + this.$store.state.userStore.userInfo.user_no + "/",
						}
					);
				} else {
					menu.push({
						title: "관리자 페이지",
						link1: "/admin/",
						link2: "/admin/",
					});
				}
				menu.push({ title: "로그아웃" });
				return menu;
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
					sessionStorage.removeItem("Authorization");
					this.$store.commit("userStore/LOGOUT");
					this.$router.push("/recent");
				}
			},
			/*keywordSearch(val) {
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
			},*/
			childSearch(searchInput) {
				this.searchInput = searchInput;
				var to = this.searchInput.substring(1);
				let searchCategory = this.searchInput.charAt(0);
				if (searchCategory === "@") {
					var foundValue = this.obj2.filter((obj) => obj.key == searchInput);
					to = foundValue[0].no; // : feed 번호
					this.dynamicArr = this.empty;
					this.$router.push("/feed/" + to);
				} else if (searchCategory === "#") {
					this.dynamicArr = this.empty;
					this.$router.push("/search/" + to);
				}
			},
			/*		mobileKeywordSearch() {
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
					window.location.href = "search/" + to;
				}
			},*/

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
	//배열 받을 때, 동일한 변수명을 적용하기 위해 rename 시켜주었다.
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
