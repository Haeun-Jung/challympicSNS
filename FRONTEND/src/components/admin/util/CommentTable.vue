<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="comments"
      item-key="comment_no"
      class="elevation-1"
      :search="search"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :expanded.sync="expanded"
      @click:row="clicked"
      show-expand
    >
      <template v-slot:top>
        <v-text-field
          v-model="search"
          label="댓글 번호, 댓글 내용, 작성자로 검색 "
          class="mx-8"
          append-icon="mdi-clipboard-search"
        ></v-text-field>
      </template>
      <template v-slot:expanded-item="{ item }">
        <td :colspan="headers.length + 1">
          <div>
            <v-list-item-subtitle class="ma-2 ml-2 grey--text">
              <v-icon>mdi-calendar-month</v-icon>
              {{ item.comment_regdate.substring(0, 10) }}
              <!--
							<v-icon>mdi-clipboard-text-outline</v-icon>
							{{ item.user_nickname }}
							-->
            </v-list-item-subtitle>
            <div class="admin-comment-expansion">
              {{ item.comment_content }}
            </div>
          </div>
        </td>
      </template>

      <!--search ends here-->
      <template #item.comment_no="{ value }">
        <div class="mr-8">{{ value }}</div>
      </template>
      <template #item.comment_regdate="{ value }">
        {{ value.substring(0, 10) }}
      </template>

      <template #item.challenge_title="{ item }">
        <router-link
          :to="{ path: `/challenge/${item.challenge_no}` }"
          style="text-decoration: none; color: inherit; mr-2"
        >
          {{ item.challenge_title }}
        </router-link>
      </template>
      <template #item.user_nickname="{ item }">
        <nick-name-module :item="item.user_nickname" :itemno="item.user_no" />
      </template>

      <template #item.comment_content="{ item }">
        <div
          style="
            text-overflow: ellipsis;
            overflow: hidden;
            width: 160px;
            height: 1.2em;
            white-space: nowrap;
          "
        >
          {{ item.comment_content }}
        </div>
      </template>

      <template v-slot:item.delete="props">
        <v-icon small @click="goodbye(props.item)"> mdi-trash-can</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import NickNameModule from "./NickNameModule.vue";
import { deleteComment, commentList } from "@/api/admin";
export default {
  name: "UserList",
  components: {
    NickNameModule,
  },
  data() {
    return {
      search: "",
      expanded: [],
      isExpanded: false,
      page: "",
      sortBy: "status",
      sortDesc: false,
      show: false,
      value: "comment_content",
      headers: [
        {
          text: "번호",
          align: "center",
          value: "comment_no",
        },
        {
          text: "내용",
          sortable: false,
          width: "25%",
          value: "comment_content",
        },
        {
          text: "좋아요",
          sortable: true /*디비 이름 확인하기 */,
          value: "like_cnt",
        },
        {
          text: "신고수" /*db 이름 확인하기 */,
          value: "comment_report",
        },

        {
          text: "등록일",
          value: "comment_regdate",
          align: "center",
        },
        {
          text: "삭제",
          value: "delete",
          sortable: false,
        },
      ],
      comments: [],
    };
  },

  methods: {
    goodbye(item) {
      let obj1 = { comment_no: item.comment_no };
      var message = "정말 해당 댓글을 삭제하시겠습니까?";
      if (confirm(message)) {
        alert("해당 댓글을 삭제하였습니다.");
        deleteComment(obj1);
        location.reload();
      } else {
        alert("취소하였습니다.");
      }
    },
    clicked(item, event) {
      if (event.isExpanded) {
        const index = this.expanded.findIndex((i) => i === item);
        this.expanded.splice(index, 1);
      } else {
        this.expanded = []; //single 직접 구현..
        this.expanded.push(item);
      }
    },
  },
  created() {
    commentList(
      (response) => {
        this.comments = response.data.data;
        console.log(this.comments);
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style scopoed>
.admin-comment-expansion {
  margin: 1%;
  padding: 1%;
}
</style>
