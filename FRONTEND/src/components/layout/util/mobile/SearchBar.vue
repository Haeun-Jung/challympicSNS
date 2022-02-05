<template>
	<!--search on small screen -->
	<div class="main-toolbar-search">
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
</template>

<script>
	export default {
		name: "MobileSearchBar",
		props: {
			AllTags: Object,
		},
		data() {
			return {
				searchInput: "",
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
		/*margin-bottom: 0%;*/
		/*background-color: pink;*/
	}
	.v-toolbar__content,
	.v-toolbar__extension {
		padding: 0;
	}
</style>
