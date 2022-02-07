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
				:items="AllTags"
				:search-input.sync="search"
				hide-selected
				hide-details="true"
				label=""
				persistent-hint
				rounded
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
					<!--
							@click="data.select"
							-->
					<template v-slot:selection="data">
						<v-chip
							v-bind="data.attrs"
							:search="data.selected"
							@click="data.select"
						>
							{{ data.item }}
						</v-chip>
					</template>
				</v-autocomplete>
			</v-menu>
		</div>

		<v-btn v-if="!isLoggedIn" @click="clickLoginBtn" color="primary" outlined small x-small
			>로그인</v-btn
		>

		<div v-if="!isMobile() && isLoggedIn">
			<v-menu bottom left offset-y max-height="260" max-width="400">
				<template v-slot:activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <div v-if="activeAlert" id="alert-badge">
            </div>
            <v-icon>
              mdi-bell-outline
            </v-icon>
          </v-btn>
				</template>

        <v-card v-if="alertMenu.length > 1">
          <v-list>
            <v-list-item
              v-for="(item, i) in alertMenu"
              :key="i"
              :to="item.link1"
            >
              <v-list-item-title>
                {{ item.title }}
                <span class="date-text">{{ dayjsRegDate(item.regDate) }}</span>
              </v-list-item-title>
            </v-list-item>
          </v-list>
          <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text
            @click="deleteAlert"
          >
            모두 읽음
          </v-btn>
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
			<v-menu bottom left offset-y>
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
		<div v-if="isMobile() && isLoggedIn">
      <v-menu bottom left offset-y max-height="260" max-width="270">
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on">
            <div v-if="activeAlert" id="alert-badge">
            </div>
            <v-icon>
              mdi-bell-outline
            </v-icon>
          </v-btn>
				</template>

        <v-card v-if="alertMenu.length > 1">
          <v-list>
            <v-list-item
              v-for="(item, i) in alertMenu"
              :key="i"
              :to="item.link1"
            >
              <v-list-item-title class="text-caption">
                {{ item.title }}
                <span class="date-text">{{ dayjsRegDate(item.regDate) }}</span>
              </v-list-item-title>
            </v-list-item>
          </v-list>
          <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            text
            @click="deleteAlert"
          >
            모두 읽음
          </v-btn>
          </v-card-actions>
        </v-card>
        <v-card v-else>
          <v-list>
            <v-list-item>
              <v-list-item-title class="text-caption">새로운 알림이 없습니다.</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-card>
			</v-menu>
			<v-menu bottom left offset-y>
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
  import fromNow from "@/plugins/dayjs.js";

	export default {
		name: "ToolBar",
		components: { SideContents },
		data() {
			return {
				drawer: false,
				onSelect: false,
				loading: false,
				search: "",
				searchInput: "",
				active: false,
        isLoggedIn: true,
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
					{ title: "관리자 페이지", link1: "/admin/" },
					{ title: "Click Me 2" },
				],
        alertMenu: [
          {title: 'nickname님이 회원님을 팔로우하기 시작했습니다.', regDate: '2022-02-07 15:30:25'},
          {title: 'SSAFY님이 회원님의 게시글에 좋아요를 눌렀습니다.', regDate: '2022-02-06 20:13:25'},
          {title: 'SSAFY님이 회원님의 게시글에 좋아요를 눌렀습니다.', regDate: '2022-02-06 20:10:25'},
          {title: 'SSAFY님이 회원님의 게시글에 좋아요를 눌렀습니다.', regDate: '2022-02-05 20:20:25'},
          {title: 'SSAFY님이 회원님의 게시글에 좋아요를 눌렀습니다.', regDate: '2022-02-05 20:20:25'},
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
			/* chore for warning msg : 이후에 autocomplete 처음 눌렀을 때 제한하고 싶어서 만든 메서드
			search(val) {
				val && val !== this.searchInput && this.querySelections(val);
				if (val.length > 2) {
					alert(this.val);
					this.minimumCharacter = "show";
				} else {
					this.minimumCharacter = "null";
				}
			},*/
		},
    computed: {
      activeAlert() {
        if (this.alertMenu.length > 1) {
          return true;
        }
        return false;
      }
    },
		methods: {
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
      deleteAlert() {
        this.menu = false;
        setTimeout(() => {
          this.alertMenu = [];
				}, 500);
      },
			/*	autocomplete 처리
			handleInput() {
				if (this.search.length < 2) {
					return [];
				} else {
					//alert("?");
					//watch나 이쪽 로직에서 처리하면 될 것 같은데..
				}
			},*/
			keywordSearch() {
				if (this.searchInput) {
					console.log(this.searchInput);
					alert(this.searchInput);
					//이쪽에서 start with @인지 #인지 확인
					let searchCategory = this.searchInput.charAt(0);
					var to = this.searchInput.substring(1);
					//	alert(searchCategory);
					if (searchCategory === "@") {
						this.$router.push("/feed/" + to);
					} else if (searchCategory === "#") {
						//	this.$emit("search", this.searchInput);
						this.$router.push("/search/" + to);
					}
				}
			},

			selectSearch(val) {
				alert("selected" + this.search);

				var to = val.substring(1);
				let searchCategory = this.searchInput.charAt(0);
				//	alert(searchCategory);
				if (searchCategory === "@") {
					this.$router.push("/feed/" + to);
				} else if (searchCategory === "#") {
					//	this.$emit("search", this.searchInput);
					this.$router.push("/search/" + to);
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
      // API 요청 - alert 목록 불러오기
    }
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
</style>

