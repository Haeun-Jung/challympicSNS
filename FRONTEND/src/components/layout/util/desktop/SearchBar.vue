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
      onSelect: false,
      search: "",
    };
  },
  methods: {
    keywordSearch() {
      alert(this.searchInput);
      this.$emit("searchInput", this.searchInput);
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
