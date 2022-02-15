<template>
  <v-main>
    <!--
		<tag-bar />
		<fab-button />
		-->
    <v-alert v-model="alert" class="alert-position" :value="alert" type="info">
      신고가 접수되었습니다.
    </v-alert>
    <v-container fluid>
      <v-layout row>
        <!-- 패딩pr-16 16준게 최선..사이드바 어디에놔도이상함..-->
        <!--데스크탑-->
        <v-flex xs3 class="hidden-md-and-down">
          <v-container fluid>
            <v-layout row>
              <v-flex xs2>
                <v-layout column>
                  <v-flex> </v-flex>
                </v-layout>
              </v-flex>
              <v-flex xs8>
                <v-layout column>
                  <v-flex>
                    <side />
                  </v-flex>
                </v-layout>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
        <!--모바일은 pa5이하로 내려가면, 영역이 깨집니다 !-->
        <v-flex
          :class="{
            'pa-6': $vuetify.breakpoint.smAndDown,
            'ma-3': $vuetify.breakpoint.mdAndUp,
          }"
          xs12
          sm6
          md7
        >
          <v-layout column>
            <v-flex>
              <!-- 라우터뷰 자리에 들어가잇음 -->
              <v-layout row>
                <v-main>
                  <!--데스크탑 환경일 경우 : -->
                  <div v-if="!isMobile()">
                    <v-card-title>
                      <h2>{{ challenge.challenge_title }}</h2>
                      <span v-if="challenge.challenge_official == 'true'"
                        >✅</span
                      >
                      <share-button size="x-large" />
                      <v-btn
                        v-if="isSubscribed"
                        @click="subscribe"
                        class="icon-margin"
                        icon
                      >
                        <v-icon large>mdi-bookmark</v-icon>
                      </v-btn>
                      <v-btn v-else @click="subscribe" class="icon-margin" icon>
                        <v-icon large>mdi-bookmark-outline</v-icon>
                      </v-btn>
                      <v-spacer />
                      <v-btn
                        @click="confirmReportDialog = true"
                        class="icon-margin"
                        icon
                      >
                        <v-icon large>mdi-alarm-light-outline</v-icon>
                      </v-btn>
                      <v-btn
                        @click="postDialog = true"
                        outlined
                        color="#3396F4"
                      >
                        참여하기
                      </v-btn>
                    </v-card-title>

                    <v-list-item-subtitle class="ml-5">
                      {{
                        `기간: ${challenge.challenge_start} ~ ${challenge.challenge_end}`
                      }}
                    </v-list-item-subtitle>
                  </div>
                  <!-- 모바일 환경일 경우-->
                  <div v-else>
                    <v-row>
                      <v-col xs="2" />
                      <v-card-title>
                        <h2>{{ challenge.challenge_title }}</h2>
                        <span v-if="challenge.challenge_official">✅</span>
                      </v-card-title>
                      <v-col xs2 />
                    </v-row>
                    <p style="text-align: center">
                      {{
                        `기간: ${challenge.challenge_start} ~ ${challenge.challenge_end}`
                      }}
                    </p>
                    <v-row class="text-center">
                      <v-card width="100%" elevation="0" color="transparent">
                        <share-button />
                        <v-btn
                          v-if="isSubscribed"
                          @click="subscribe"
                          class="icon-margin"
                          icon
                        >
                          <v-icon>mdi-bookmark</v-icon>
                        </v-btn>
                        <v-btn
                          v-else
                          @click="subscribe"
                          class="icon-margin"
                          icon
                        >
                          <v-icon>mdi-bookmark-outline</v-icon>
                        </v-btn>
                        <v-btn
                          @click="confirmReportDialog = true"
                          class="icon-margin"
                          icon
                        >
                          <v-icon>mdi-alarm-light-outline</v-icon>
                        </v-btn>
                        <v-btn
                          @click="postDialog = true"
                          outlined
                          color="#3396F4"
                        >
                          참여하기
                        </v-btn>
                      </v-card>
                    </v-row>
                  </div>
                  <v-list-item-subtitle>
                    <v-chip
                      v-for="tag in tags"
                      :key="tag.id"
                      @click="clickTag(tag)"
                      class="mt-6 ml-4 challenge-chip"
                      color="#3396F4"
                      text-color="white"
                    >
                      {{ tag }}
                    </v-chip>
                  </v-list-item-subtitle>

                  <v-card-subtitle>
                    <span
                      v-html="
                        $options.filters.hashAnchor(challenge.challenge_content)
                      "
                    ></span>
                  </v-card-subtitle>
                </v-main>
              </v-layout>
              <div>
                <!-- End of Mobile -->
                <!--Data Iterator -->
                <v-data-iterator
                  v-if="postList.length != 2"
                  :items="postList"
                  :items-per-page.sync="itemsPerPage"
                  :sort-by="sortBy"
                  :sort-desc="sortDesc"
                  hide-default-footer
                >
                  <template v-slot:header>
                    <v-row class="justify-end">
                      <v-col class="col-5 col-sm-3 col-lg-2">
                        <v-select
                          v-model="sortBy"
                          :items="sortKeys"
                          :sort-desc="sortDesc"
                          class="align-end"
                          flat
                          solo-inverted
                          hide-details
                          label="정렬"
                        ></v-select>
                      </v-col>
                    </v-row>
                  </template>
                  <!-- props로 포스트 목록 내려주기 -->
                  <template v-slot:default="props">
                    <post-item
                      v-for="post in props.items"
                      :post="post"
                      :type="challenge.challenge_type"
                      :key="post.post_no"
                    ></post-item>
                  </template>
                </v-data-iterator>
                <battle-item
                  v-else
                  :postList="postList"
                  :type="challenge.challenge_type"
                />
              </div>
              <confirm-report
                :confirm-report-dialog="confirmReportDialog"
                @close-dialog="confirmReportDialog = false"
                @reported="showReportedAlert"
              />
              <post-upload
                v-if="postDialog"
                :propChallengeName="{
                  challengeName: challenge.challenge_title,
                  challengeNo: challenge.challenge_no,
                  challangeType: challenge.challenge_type,
                }"
                @close-modal="postDialog = false"
              />
            </v-flex>
          </v-layout>
        </v-flex>
      </v-layout>

      <!--
      -->
    </v-container>
  </v-main>
</template>

<script>
import Side from "@/components/layout/Side.vue";
import PostItem from "@/components/post/PostItem.vue";
import BattleItem from "@/components/post/BattleItem.vue";
import ShareButton from "@/components/button/ShareButton.vue";
import ConfirmReport from "@/components/report/ConfirmReport.vue";
import PostUpload from "@/components/upload/PostUpload.vue";

export default {
  name: "ChallengeDetail",
  components: {
    Side,
    PostItem,
    BattleItem,
    ShareButton,
    ConfirmReport,
    PostUpload,
  },
  data() {
    return {
      confirmReportDialog: false,
      alert: false,
      isSubscribed: false,
      postDialog: false,
      linkInput: false,
      itemsPerPage: -1,
      // TODO: 기본 정렬 키 어떤 걸로 할지 결정
      sortBy: "post_no",
      sortKeys: [
        { text: "등록 순", value: "post_no" },
        { text: "최신 순", value: "post_regdate" },
        { text: "인기 순", value: "likeCnt" },
        // 댓글 목록 요청은 따로 보내므로 주석 처리 해두었습니다.
        // { text: "댓글 순", value: "comments.length" },
      ],
    };
  },
  methods: {
    showReportedAlert() {
      // 챌린지 신고 API 호출
      this.alert = true;
      this.report = true;
      setTimeout(() => {
        this.alert = false;
      }, 3000);
    },
    subscribe() {
      if (this.isSubscribed) {
        // 챌린지 구독 delete 요청
      } else {
        // 챌린지 구독 post 요청
      }
      this.isSubscribed = !this.isSubscribed;
    },
    clickTag(tagContent) {
      console.log(tagContent);
      console.log(tagContent.split("#")[1]);
      this.$router.push(`/search/${encodeURIComponent(tagContent.split("#")[1])}`);
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
  filters: {
    hashAnchor(str) {
      // TODO: anchor 태그에서 href base url 주석 처리된 url로 변경!!!!!
      str = (str || "").replace(
        /#[^\s]+/g,
        '<a class="text-decoration-none" href="http://192.168.219.106:8080/search/$&">$&</a>'
      );
      // str = str.replace(
      //   /#[^\s]+/g,
      //   '<a class="text-decoration-none" href="http://i6b101.p.ssafy.io/search/$&">$&</a>'
      // );
      return str.replace(/\/#/g, "/");
    },
  },
  computed: {
    sortDesc() {
      if (this.sortBy === "likeCnt" || this.sortBy === "post_regdate") {
        return true;
      }
      return false;
    },
    tags() {
      let splitedContent = (
        this.$store.state.challengeStore.challenge.challenge_content || ""
      )
        .split(" ")
        .filter((word) => {
          return word.startsWith("#");
        });
      if (splitedContent.length > 0) {
        return splitedContent;
      } else {
        return null;
      }
    },
    challenge() {
      return this.$store.state.challengeStore.challenge;
    },
    postList() {
      if(this.$route.query.postNo){
        let org = this.$store.state.postStore.postList;
        let list = [];
        
        for(let i = 0; i < org.length; i++){
          if(org[i].post_no == this.$route.query.postNo){
            list.unshift(org[i]);
          } else {
            list.push(org[i]);
          }
        }

        return list;
      }
      return this.$store.state.postStore.postList;
    },
  },
  created() {
    this.$store.dispatch(
      "challengeStore/getChallenge",
      this.$route.params.challengeNo
    );
    this.$store.dispatch("postStore/getPostList", {
      challengeNo: this.$route.params.challengeNo,
      userNo: this.$store.state.userStore.userNo,
    });

    if(this.$route.query.postNo){
      this.sortBy = 'post_regdate';
    }
  },
};
</script>

<style scoped>
.challenge-detail-button-container {
  padding: 0;
}
.alert-position {
  position: absolute;
  width: 100%;
  z-index: 1;
  /*top: 3.3rem;*/
}
.detail-page {
  margin-top: 5rem;
}
.icon-margin {
  margin: 0 0.5rem;
}
.side-and-main {
  justify-content: space-evenly;
}
/*
  (수정)v-chip에 직접 주었습니다.
	.challenge-chip {
		color: white;
	}*/
.select {
  width: 120px;
}
.v-chip {
  cursor: pointer;
}
</style>
