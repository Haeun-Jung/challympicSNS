<template>
  <!-- 로그인 O -->
  <v-list v-if="isLoggedIn" class="title-width">
    <v-list-group>
      <template v-slot:activator>
        <v-card-subtitle class="title-width">
          <h2>내 관심사</h2>
        </v-card-subtitle>
      </template>
      <v-chip
        class="interest-one"
        v-for="(tag, idx) in listInterest"
        :key="tag.tag_no"
        :value="tag"
        :to="{ path: '/search/' + (tag.tag_content || '').slice(1) }"
        v-model="tag.isOpen"
        color="primary"
        outlined
        close
        @click:close="remove(idx, tag.tag_no)"
      >
        {{ tag.tag_content }}
      </v-chip>
    </v-list-group>
  </v-list>
  <!-- 로그인 X -->
  <div v-else class="interest-wrapper">
    <v-card-subtitle>
      <h2>내 관심사</h2>
    </v-card-subtitle>
    <v-list-item-content>
      <h4 class="main-side-menu-contents">
        관심사를 등록하고 관련 영상을 찾아보세요!
      </h4>
      <div class="main-side-menu-btn-container">
        <v-btn
          @click="clickLoginBtn"
          color="primary"
          class="main-side-menu-btn"
        >
          로그인
        </v-btn>
      </div>
    </v-list-item-content>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { getInterest } from "@/api/user.js";

const userStore = "userStore";
export default {
  name: "MyInterest",
  computed: {
    // ...mapState(userStore, ["listInterest"]),
    isLoggedIn() {
      return this.$store.state.userStore.isLoggedIn;
    },
  },
  data() {
    return {
      listInterest: [""],
    };
  },
  methods: {
    ...mapActions(userStore, ["getInterest"]),
    clickLoginBtn() {
      this.$router.push("/login");
    },
    remove(idx, no) {
      this.listInterest.splice(idx, 1);
      // this.index++; //카운트 해줘야 다음 태그 제대로 지워짐
      // 이렇게 하고, 페이지 refresh 해서 태그 다시 받아와야함.....
      this.listInterest = [...this.listInterest];
      this.$store.dispatch("userStore/deleteInterest", {
        no,
        token: sessionStorage.getItem("Authorization"),
      });
      setTimeout(() => {
        this.getInterest(sessionStorage.getItem("Authorization"));
      }, 300);
    },
  },
  created() {
    if(this.$store.state.userStore.userInfo){
      getInterest(this.$store.state.userStore.userInfo.user_no, (response) => {
        this.listInterest = response.data.data;
      });
    }
  },
};
</script>

<style scoped>
.interest-wrapper {
  margin-bottom: 10px;
}
.main-side-menu-contents {
  text-align: center;
  margin: 1%;
  padding-left: 5%;
  padding-right: 7%;
}
.main-side-menu-btn-container {
  margin: 5%;
  padding-left: 30%;
}
.title-width {
  width: 100%;
  justify-content: space-between;
  background: transparent;
  padding-right: 0px;
}

.interest-one {
  margin: 3px;
}
</style>
