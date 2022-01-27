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
    <v-toolbar-title>Challympic</v-toolbar-title>
    <v-spacer />
    <v-toolbar-items class="hidden-md-and-down">
      <div class="main-toolbar-search">
        <v-text-field
          label="@유저 #챌린지 검색"
          hide-details
          class="ml-5"
          flat
          solo
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
    <!--v-if users>
          <v-btn icon>
          <v-icon>mdi-bell-outline</v-icon>
            </v-if>
          </v-btn>
            else :
            -->
    <v-btn color="primary" outlined small x-small>로그인</v-btn>
    <!-- Add a navigation bar -->
    <v-navigation-drawer
      hide-overlay
      v-model="drawer"
      absolute
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
  },
};
</script>

<style scoped>
.v-text-field {
  width: 600px;
}
.main-toolbar-search {
  margin-top: 1.2%;
  /*background-color: pink;*/
}
</style>
