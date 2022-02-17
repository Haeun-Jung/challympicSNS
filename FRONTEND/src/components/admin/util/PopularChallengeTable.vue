<template>
  <v-container class="official-table">
    <v-data-table
      :headers="headers"
      :items="challenges"
      item-key="challenge_no"
      class="elevation-1 official-table"
      :search="search"
      :sort-by.sync="sortBy"
      items-per-page="10"
      :sort-desc.sync="sortDesc"
      :hide-default-footer="true"
    >
      <template #item.challenge_title="{ item }">
        <router-link
          :to="{ path: `/challenge/${item.challenge_no}` }"
          style="text-decoration: none; color: inherit; mr-2"
        >
          <div
            style="
              text-overflow: ellipsis;
              overflow-x: hidden;
              overflow: hidden;
              white-space: nowrap;
            "
          >
            <div v-if="item.challenge_official == true">
              {{ item.challenge_title }}✅
            </div>
            <div v-else>
              {{ item.challenge_title }}
            </div>
          </div>
        </router-link>
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
      sortBy: "challenge_participants",
      sortDesc: true,
      spp: 5,
      itemsPerPage: 5,
      checkbox: true,
      //api 통신전 dummy data
      headers: [
        {
          text: "챌린지명",

          sortable: false,
          value: "challenge_title",
        },
        {
          text: "참여",
          width: "30%",
          sortable: true,
          value: "post_cnt",
        },
        {
          text: "구독",
          width: "30%",
          sortable: true,
          value: "subscription_cnt",
        },
      ],
      challenges: [],
    };
  },

  methods: {
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
      },
      (error) => {
        if (error) console.log("er");
      }
    );
  },
};
</script>
