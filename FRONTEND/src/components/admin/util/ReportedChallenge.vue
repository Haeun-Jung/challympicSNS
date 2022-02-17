<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="challenges"
      item-key="challenge_no"
      class="elevation-1"
      dense
      :search="search"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      :hide-default-footer="true"
    >
      <!--search ends here-->
      <template #item.post_cnt="{ item }">
        <div v-if="item.challenge_report === 0" class="mr-8"></div>
        <div v-else class="mr-8">{{ item.post_cnt }}</div>
      </template>

      <template #item.challenge_title="{ item }">
        <div v-if="item.challenge_report === 0"></div>
        <div v-else>
          <router-link
            :to="{ path: `/challenge/${item.challenge_no}` }"
            style="text-decoration: none; color: inherit; mr-2"
          >
            {{ item.challenge_title }}
          </router-link>
        </div>
      </template>

      <template #item.challenge_report="{ item }">
        <div v-if="item.challenge_report === 0"></div>
        <div v-else>
          {{ item.challenge_report }}
        </div>
      </template>

      <template v-slot:item.delete="props">
        <v-icon
          v-if="props.item.challenge_report > 0"
          small
          @click="goodbye(props.item)"
        >
          mdi-trash-can</v-icon
        >
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import { challengeList } from "@/api/admin";
export default {
  name: "UserList",
  data() {
    return {
      search: "",
      page: "",
      sortBy: "challenge_report",
      sortDesc: true,
      headers: [
        {
          text: "챌린지",
          sortable: false,
          value: "challenge_title",
        },
        {
          text: "참여",
          sortable: false,
          value: "post_cnt",
        },
        {
          text: "신고",
          sortable: false,
          value: "challenge_report",
        },
        {
          value: "delete",
          align: "center",
          sortable: false,
        },
      ],
      challenges: [],
      created() {
        challengeList(
          (response) => {
            this.challenges = response.data.data;
          },
          (error) => {
            console.log(error);
          }
        );
      },
    };
  },

  methods: {
    goodbye(item) {
      var message = "정말 " + item.challenge_title + "을 삭제하시겠습니까?";
      if (confirm(message)) {
        alert(item.challenge_title + "를 삭제하였습니다.");
        //item.user_no로 회원 정지 api통신
        //deleteUser(this.item);
      } else {
        alert("취소하였습니다.");
      }
      //locaation.reload();
    },
    makeOfficial(item) {
      if (!item.challenge_official) {
        alert("공식 챌린지를 해제했습니다.");
        this.item.challenge_official = false;
      } else {
        alert("공식 챌린지에 등록했습니다.");
        this.item.challenge_official = true;
      }
    },
  },
  created() {
    challengeList(
      (response) => {
        this.challenges = response.data.data;
        this.challenges = this.challenges.filter(function (el) {
          return el.challenge_report != 0;
        });
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style></style>
