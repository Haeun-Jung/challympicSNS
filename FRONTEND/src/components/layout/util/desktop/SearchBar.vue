<template>
	<v-autocomplete
		v-model="searchInput"
		:items="dynamicArr"
		:search-input.sync="search"
		hide-selected
		hide-details="true"
		label=""
		rounded
		small-chips
		@change="keywordSearch"
		@keyup="test"
		@keyup.delete="test"
		@keydown.enter="keywordSearch"
		flat
		solo
		item-text="key"
		item-value="key"
		clearable
		append-icon="mdi-magnify"
		placeholder="#태그 @사용자 검색"
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
			<v-chip v-bind="data.attrs" :search="data.selected" @click="data.select">
				{{ data.item.key }}
			</v-chip>
		</template>
	</v-autocomplete>
</template>

<script>
	export default {
		name: "DesktopSearchBar",
		props: {
			tags: Array,
		},
		data() {
			return {
				searchInput: "",
				onSelect: false,
				search: "",
				dynamicArr: [],

      empty: [],
    };
  },
  methods: {
    test() {
      if (this.search.trim().length > 0) this.dynamicArr = this.tags;
      else if (this.search.length == 0) this.dynamicArr = this.empty;
    },
    keywordSearch() {
      //	alert(this.searchInput);
      this.$emit("searchInput", this.searchInput);
    },
  },
};
</script>

<style scoped>
<<<<<<< HEAD
.v-toolbar-title:hover {
  cursor: pointer;
}
.v-text-field {
  width: 200px;
} /*
=======
	.v-autocomplete:not(.v-input--is-focused).v-select--chips >>> input {
		max-height: inherit;
	}
	.v-toolbar-title:hover {
		cursor: pointer;
	}
	.v-text-field {
		width: 200px;
	} /*
>>>>>>> a014fa4771d1067af18b61e94217d8ace066a5a6
	.main-toolbar-search {*/
/*margin-bottom: 0%;*/
/*background-color: pink;*/
/*	}
	.v-toolbar__content,
	.v-toolbar__extension {
		padding: 0;
	}*/
</style>
