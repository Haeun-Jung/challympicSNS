http://192.168.0.110:8080/
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
		<v-toolbar-title class="v-toolbar-title" @click="goMain"
			>Challympic</v-toolbar-title
		>
		<v-spacer />
		<!--tag랑 사용자랑 join table 필요할 것 같다:아니 이거 처음[]리턴 왜 안되는데..-->
		<div class="main-toolbar-search hidden-md-and-down">
			<v-autocomplete
				v-model="searchInput"
				:items="AllTags"
				:search-input.sync="search"
				hide-selected
				hide-details="true"
				label=""
				persistent-hint
				small-chips
				@change="keywordSearch"
				class="ml-5"
				flat
				solo
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
			</v-autocomplete>
		</div>
		<v-spacer></v-spacer>
		<!--search on small screen -->
		<div class="main-toolbar-search hidden-sm-and-up">
			<v-menu
				open-on-hover
				offset-y
				v-model="menu"
				:close-on-content-click="false"
				min-width="220px"
			>
				<template v-slot:activator="{ on, attrs }">
					<v-btn v-bind="attrs" v-on="on" icon>
						<v-icon>mdi-magnify</v-icon>
					</v-btn>
				</template>
				<v-autocomplete
					v-model="searchInput"
					:items="AllTags"
					:search-input.sync="search"
					label=""
					small-chips
					flat
					solo
					hide-details="true"
					clearable
					append-icon="mdi-magnify"
					@change="keywordSearch"
					@click="keywordSearch"
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
							@change="keywordSearch"
						>
							{{ data.item }}
						</v-chip>
					</template>
				</v-autocomplete>
			</v-menu>
		</div>

		<v-btn @click="clickLoginBtn" color="primary" outlined small x-small
			>로그인</v-btn
		>

		<div v-if="!isMobile()">
			<v-menu bottom left>
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
					>
						<v-list-item-title>{{ item.title }}</v-list-item-title>
					</v-list-item>
				</v-list>
			</v-menu>
		</div>
		<div v-else>
			<v-menu bottom left>
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

	export default {
		name: "ToolBar",
		components: { SideContents },
		data() {
			return {
				drawer: false,
				loading: false,
				searchInput: "",
				menu: false,
				profileMenu: [
					{
						title: "프로필 설정",
						link1: "/user/account/:userNo/",
						link2: "/mobile/user/account/:userNo/",
					},
					{ title: "Click Me" },
					{ title: "Click Me" },
					{ title: "Click Me 2" },
				],
				AllTags: [
					//검색용 태그 : 태그 + 사용자
					"#Gaming",
					"#Food",
					"#Work",
					"#Art",
					"@Loren",
					"#김싸피덤벼",
					"@김싸피",
					"#Gaming1",
					"#Food1",
					"#Work1",
					"#Art1",
					"@Loren1",
					"@김싸피1",
					"#Gaming2",
					"#Food2",
					"#Work2",
					"#Art2",
					"@Loren2",
					"@김싸피2",
				],
			};
		},
		watch: {
			group() {
				this.drawer = false;
			},
			search(val) {
				val && val !== this.searchInput && this.querySelections(val);
				if (val.length > 2) {
					alert(this.val);
					this.minimumCharacter = "show";
				} else {
					this.minimumCharacter = "null";
				}
			},
		},
		methods: {
			menuItems() {
				return this.menu;
			},
			goMain() {
				this.$router.push("/");
			},
			clickLoginBtn() {
				this.$router.push("/login");
			},
			handleInput() {
				if (this.search.length < 2) {
					return [];
				} else {
					//alert("?");
					//watch나 이쪽 로직에서 처리하면 될 것 같은데..
				}
			},
			keywordSearch() {
				if (this.searchInput) {
					console.log(this.searchInput);
					//이쪽에서 start with @인지 #인지 확인
					let searchCategory = this.searchInput.charAt(0);
					//	alert(searchCategory);
					if (searchCategory === "@") {
						alert("유저 피드 페이지로 라우팅");
					} else if (searchCategory === "#") {
						var to = this.searchInput.substring(1);
						//	this.$emit("search", this.searchInput);
						this.$router.push("/search/" + to);
					}
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
	};
</script>
<style scoped>
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
</style>
