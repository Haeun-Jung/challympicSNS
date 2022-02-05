<template>
	<!--tag랑 사용자랑 join table 필요할 것 같다:아니 이거 처음[]리턴 왜 안되는데..-->
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
</template>

<script>
	export default {
		name: "DesktopSearchBar",
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

<style>
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
