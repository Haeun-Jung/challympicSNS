<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="challenges"
      item-key="challenge_no"
      class="elevation-1"
      :search="search"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
    >
      <template v-slot:top>
        <v-text-field
          v-model="search"
          label="챌린지 번호, 챌린지명, 작성자로 챌린지 검색"
          class="mx-8"
          append-icon="mdi-clipboard-search"
        ></v-text-field>
      </template>
      <!--search ends here-->
      <template #item.challenge_no="{ value }">
        <div class="mr-8">{{ value }}</div>
      </template>
      <template #item.challenge_report="{ value }">
        <div class="ml-2 justify-center">{{ value }}</div>
      </template>
      <template #item.challenge_participants="{ value }">
        <!--	<div v-bind="sumField(value)">{{ value }}</div>-->
        {{ value }}
      </template>
      <template #item.challenge_title="{ item }">
        <router-link
          :to="{ path: `/challenge/${item.challenge_no}` }"
          style="text-decoration: none; color: inherit; mr-2"
        >
          {{ item.challenge_title }}
        </router-link>
      </template>
      <template #item.user_email="{ value }">
        <a :href="`mailto:${value}`"> {{ value }} </a>
      </template>
      <template #item.duration="{ item }">
        {{ item.challenge_start.substring(0, 10) }} ~ <br />
        {{ item.challenge_end.substring(0, 10) }}
      </template>
      <template #item.actions="props">
        <div class="ml-3">
          <v-checkbox
            v-if="props.item == true"
            v-model="props.item.challenge_official"
            @click="makeOfficial(props.item)"
            color="success"
          ></v-checkbox>
          <v-checkbox
            v-else
            v-model="props.item.challenge_official"
            color="success"
            @click="makeOfficial(props.item)"
          ></v-checkbox>
        </div>
      </template>

      <template v-slot:item.delete="props">
        <v-icon small @click="goodbye(props.item)"> mdi-trash-can</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import { challengeList, deleteChallenge } from "@/api/admin";
export default {
  name: "UserList",
  data() {
    return {
      search: "",
      sum: 0,
      page: "",
      sortBy: "status",
      sortDesc: false,
      index: 0,
      checkbox: true,
      //api 통신전 dummy data
      headers: [
        {
          text: "번호",
          align: "center",
          value: "challenge_no",
        },
        {
          text: "챌린지명",
          sortable: false,
          value: "challenge_title",
        },
        {
          text: "작성자",
          sortable: false,
          align: "center",
          value: "user_email",
        },
        {
          text: "참여수",
          sortable: true /*디비 이름 확인하기 */,
          value: "post_cnt",
        },
        {
          text: "구독수" /*db 이름 확인하기 */,
          value: "subscription_cnt",
        },

        {
          text: "신고수",
          value: "challenge_report",
        },
        {
          text: "기간",
          //	sortable: false,
          value: "duration",
        },
        {
          text: "공식 설정",
          value: "actions",
          align: "center",
          sortable: false,
        },
        {
          text: "삭제",
          value: "delete",
          align: "center",
          sortable: false,
        },
      ],
      challenges: [],
    };
  },

  methods: {
    goodbye(item) {
      let obj1 = { challenge_no: item.user_no };

      var message = "정말 " + item.challenge_title + "을 삭제하시겠습니까?";
      if (confirm(message)) {
        alert(item.challenge_title + "를 삭제하였습니다.");
        deleteChallenge(obj1);
        location.reload();
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
        console.log(this.challenges);
      },
      (error) => {
        if (error) console.log("er");
      }
    );
  },
};
</script>

<style></style>
