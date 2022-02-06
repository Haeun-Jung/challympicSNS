<template>
  <v-card class="my-5">
    <a :href="`#${post.postInfo.postNo}`" :id="`${post.postInfo.postNo}`"></a>
    <v-card-title class="d-flex justify-space-between">
      <div class="d-flex align-center">
        <img
          class="profile mr-3"
          src="../../assets/profile.png"
          alt="profile img"
        />
        <a
          href="#"
          :class="[
            'text-decoration-none',
            'font-weight-bold',
            'text-h6',
            [$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
          ]"
        >
          <span>{{ post.postInfo.nickname }}</span>
        </a>
      </div>
      <!-- 'nickname'을 현재 로그인한 유저의 닉네임으로 수정 -->
      <div v-if="post.postInfo.nickname == 'nickname'">
        <v-btn @click="editPost" icon>
          <v-icon>mdi-pencil-outline</v-icon>
        </v-btn>
        <v-btn @click="deletePost" icon>
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </div>
    </v-card-title>
    <!-- 이미지/동영상 props 넘겨주기 -->

    <v-card-text class="px-0">
      <span v-if="type == 'video'">
        <player />
      </span>
      <span v-else>
        <post-image :fileName="post.postInfo.fileName" />
      </span>
    </v-card-text>
    <v-card-text class="d-flex justify-space-between pt-0 pl-0 pb-0">
      <span>
        <v-btn @click="like(post)" class="icon-margin" icon>
          <v-icon :class="{ 'active-like-btn': post.postInfo.isLiked }" large>
            mdi-heart-outline
          </v-icon>
        </v-btn>
        <v-btn @click="toggleCommentShow" class="icon-margin" icon>
          <v-icon :class="{ 'active-comment-btn': showComment }" large>
            mdi-comment-outline
          </v-icon>
        </v-btn>
      </span>
      <span>
        <share-button :post-no="post.postInfo.postNo" />
      </span>
    </v-card-text>
    <v-card-text class="py-2">
      <span
        :class="[
          'like-cnt',
          [$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
        ]"
        @click="toggleLikeDialog"
      >
        좋아요 {{ post.postInfo.likeCnt }}개
      </span>
    </v-card-text>
    <v-card-text class="py-1">
      <span class="mr-3">
        <a
          href="#"
          :class="[
            'text-decoration-none',
            'font-weight-bold',
            [$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
          ]"
        >
          {{ post.postInfo.nickname }}
        </a>
      </span>
      <span
        :class="[$vuetify.theme.dark ? 'dark-mode-text' : 'black-text']"
        v-html="$options.filters.hashAnchor(post.postInfo.content)"
      ></span>
      <p>{{ post.postInfo.regDate }}</p>
    </v-card-text>
    <v-divider />
    <v-expand-transition>
      <div v-show="showComment">
        <comment-list :comments="post.comments" />
        <v-divider />
      </div>
    </v-expand-transition>
    <v-card-text class="profile-img-and-comment-input">
      <img
        class="comment-profile mr-2"
        src="../../assets/profile.png"
        alt="profile img"
      />
      <v-text-field
        v-model="commentInput"
        class="rounded-pill"
        placeholder="댓글을 달아보세요."
        append-outer-icon="mdi-arrow-up"
        outlined
        hide-details
        @click:append-outer="addComment"
      ></v-text-field>
    </v-card-text>
    <follow-like-modal
      v-if="likeDialog"
      :users="post.likedUsers"
      :type="dialogType"
      @close-dialog="toggleLikeDialog"
    />
  </v-card>
</template>

<script>
import PostImage from "./PostImage.vue";
import Player from "./Player.vue";
import FollowLikeModal from "./FollowLikeModal.vue";
import CommentList from "./CommentList.vue";
import ShareButton from "../button/ShareButton.vue";

export default {
  name: "PostItem",
  components: { PostImage, Player, FollowLikeModal, CommentList, ShareButton },
  props: {
    type: String,
    post: Object,
  },
  data() {
    return {
      commentInput: "",
      showComment: false,
      likeDialog: false,
      dialogType: "like",
    };
  },
  methods: {
    like(post) {
      // 좋아요 API 요청
      post.postInfo.isLiked = !post.postInfo.isLiked;
      if (post.postInfo.isLiked) {
        post.postInfo.likeCnt += 1;
      } else {
        post.postInfo.likeCnt -= 1;
      }
    },
    toggleCommentShow() {
      this.showComment = !this.showComment;
    },
    toggleLikeDialog() {
      this.likeDialog = !this.likeDialog;
    },
    editPost() {
      // 포스트 수정 API 요청
    },
    deletePost() {
      // 포스트 삭제 API 요청
    },
    exportPost() {
      // 공유하기 dialog 열기
      return;
    },
    addComment() {
      // 댓글 작성 API 호출
      this.commentInput = "";
    },
  },
  filters: {
    hashAnchor(str) {
      // TODO: anchor 태그에서 href 주소 검색창 url로 변경
      str = str.replace(
        /@[^\s]+/g,
        '<a class="text-decoration-none" href="$&">$&</a>'
      );
      return str.replace(
        /#[^\s]+/g,
        '<a class="text-decoration-none" href="$&">$&</a>'
      );
    },
  },
  created() {
      console.log(this.type);
  }
};
</script>

<style scoped>
.dark-mode-text {
  color: #424242;
}
.black-text {
  color: black;
}
.profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.icon-margin {
  margin: 0 0.5rem;
}
.comment-profile {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}
.active-like-btn {
  color: red !important;
}
.active-comment-btn {
  color: #3396f4 !important;
}
.like-cnt {
  cursor: pointer;
}
.profile-img-and-comment-input {
  display: flex;
  align-items: center;
}
</style>