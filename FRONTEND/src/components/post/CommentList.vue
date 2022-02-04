<template>
  <div>
    <v-alert v-model="alert" :value="alert" type="info">
      신고가 접수되었습니다.
    </v-alert>
    <!-- <v-card-text>
      <span>{{ comments.length }}개의 댓글이 있습니다.</span>
    </v-card-text> -->
    <!-- <v-divider></v-divider> -->
    <v-card-text
      v-for="comment in comments"
      class="content-and-btns py-2"
      :key="comment.commentNo"
    >
      <div class="profile-img-and-comment">
        <img
          class="profile mr-2"
          src="../../assets/profile.png"
          alt="profile img"
        />
        <span>
          <div>
            <span class="font-weight-bold">
              <a href="#" class="text-decoration-none black--text mr-2">
                {{ comment.nickname }}
              </a>
            </span>
            <span>{{ comment.content }}</span>
          </div>
          <div class="comment-info">
            <span class="mr-2">{{ comment.regDate }}</span>
            <span class="mr-2">좋아요 {{ comment.likeCnt }}</span>
            <span @click="confirmReportDialog = true" class="report-btn"
              >신고하기</span
            >
          </div>
        </span>
      </div>
      <div>
        <span v-if="comment.nickname === nickname">
          <v-btn @click="editComment" icon>
            <v-icon small>mdi-pencil-outline</v-icon>
          </v-btn>
          <v-btn @click="deleteComment" icon>
            <v-icon small>mdi-close</v-icon>
          </v-btn>
        </span>
        <span v-else>
          <v-btn @click="likeComment" icon>
            <v-icon :class="{ 'active-like-btn': comment.isLiked }" small>
              mdi-heart-outline
            </v-icon>
          </v-btn>
        </span>
      </div>
    </v-card-text>
    <confirm-report
      :confirm-report-dialog="confirmReportDialog"
      @close-dialog="confirmReportDialog = false"
      @reported="showReportedAlert"
    />
  </div>
</template>

<script>
import ConfirmReport from "../report/ConfirmReport.vue";
export default {
  components: { ConfirmReport },
  name: "CommentList",
  props: {
    comments: Array,
  },
  data() {
    return {
      nickname: "박싸피",
      confirmReportDialog: false,
      alert: false,
    };
  },
  watch: {},
  methods: {
    editComment() {
      // 댓글 수정 API 호출
      // emit event => 현재 comment에 대한 content 값 수정
    },
    deleteComment() {
      // 댓글 삭제 API 호출
      // emit event => 현재 comment 삭제?
    },
    likeComment() {
      // 댓글 좋아요 API 호출
      // emit event => 현재 comment에 대한 isLiked 값 수정
    },
    showReportedAlert() {
      this.alert = true;
      setTimeout(() => {
        this.alert = false;
      }, 3000);
      // 댓글 신고 API 호출
    },
  },
};
</script>

<style scoped>
.profile {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}
.comment-info {
  font-size: 11px;
}
.content-and-btns {
  display: flex;
  justify-content: space-between;
}
.profile-img-and-comment {
  display: flex;
  align-items: center;
}
.active-like-btn {
  color: red !important;
}
.report-btn {
  cursor: pointer;
}
</style>