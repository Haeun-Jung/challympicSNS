<template>
	<v-app-bar
		fixed
		flat
		outlined
		elevation="0"
		:color="$vuetify.theme.dark ? '#424242' : '#FAFAFA'"
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
		<v-toolbar-items class="hidden-md-and-down">
			<div class="main-toolbar-search">
				<v-text-field
					label="@유저 #챌린지 검색"
					hide-details
					class="ml-5"
					flat
					solo
          			rounded
					append-icon="mdi-magnify"
					width="600px"
				>
				</v-text-field>
			</div>
		</v-toolbar-items>
		<v-spacer></v-spacer>
		<!--search on small screen -->
		<v-menu
			open-on-hover
			offset-y
			v-model="menu"
			:close-on-content-click="false"
			min-width="220px"
		>
			<template v-slot:activator="{ on, attrs }">
				<v-btn v-bind="attrs" v-on="on" icon class="hidden-sm-and-up">
					<v-icon>mdi-magnify</v-icon>
				</v-btn>
			</template>
			<v-text-field
				label="@사용자,#챌린지로 검색"
				solo
				dense
				hide-details="true"
				height="50px"
			></v-text-field>
		</v-menu>

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
						link2: "/feed/:userNo/"
					},
					{ title: "Click Me" },
					{ title: "Click Me 2" },
				],
			};
		},
		watch: {
			group() {
				this.drawer = false;
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
	.main-toolbar-search {
		margin-top: 1.2%;
		/*background-color: pink;*/
	}
	.font-weight {
		font-weight: bold;
	}
</style>
