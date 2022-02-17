<template>
  <!--search on small screen -->
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
      placeholder="#태그 @사용자 검색"
    >
      <template v-slot:no-data>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title> 검색결과 없음 </v-list-item-title>
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
  </v-menu>
</template>

<script>
export default {
  name: "MobileSearchBar",
  props: {
    tags: Array,
    //	obj1: Array,
    //	obj2: Array,
  },
  data() {
    return {
      searchInput: "",
      dynamicArr: [],
      empty: [],
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
    mobiletest() {
      if (this.mobileSearch.trim().length > 0) this.dynamicArr = this.tags;
      else if (this.mobileSearch.length == 0) this.dynamicArr = this.empty;
    },
    onClickOutside() {
      this.active = false;
    },
    mobileKeywordSearch() {
      this.$emit("mobileSearchInput", this.mobileSearchInput);
      /*	var to = this.mobileSearchInput.substring(1);
				let searchCategory = this.mobileSearchInput.charAt(0);
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
					this.$router.push("/search/" + to);
				}*/
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
.v-autocomplete:not(.v-input--is-focused).v-select--chips >>> input {
  max-height: inherit;
}
</style>
