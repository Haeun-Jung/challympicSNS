<template>
  <v-main>
    <tag-bar />
    <fab-button />
    <v-container fluid>
      <v-layout row>
        <!-- 패딩pr-16 16준게 최선..사이드바 어디에놔도이상함..-->
        <!--데스크탑-->
        <v-flex xs1 class="hidden-sm-and-up" />
        <v-flex xs2 class="hidden-md-and-down" />
        <v-flex xs2 class="hidden-md-and-down">
          <v-container fluid>
            <v-layout column>
              <v-flex>
                <side />
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
        <!-- 반응형 처리: 핸드폰 고정시켜놔서 이부분 제거했습니다 'pa-4': $vuetify.breakpoint.smAndDown, -->
        <v-flex
          :class="{
            'ma-3': $vuetify.breakpoint.mdAndUp,
          }"
          xs10
          sm6
          md6
        >
          <v-layout column>
            <v-flex>
              <router-view />
            </v-flex>
          </v-layout>
        </v-flex>
        <v-flex xs2 class="hidden-md-and-down" />
        <v-flex xs1 class="hidden-sm-and-up" />
      </v-layout>
    </v-container>
  </v-main>
</template>

<script>
import TagBar from "../components/layout/TagBar.vue";
import Side from "../components/layout/Side.vue";
import FabButton from "../components/button/FabButton.vue";
export default {
  components: { TagBar, Side, FabButton },
};
</script>
