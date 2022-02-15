<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="users"
      item-key="user_no"
      class="elevation-1"
      :search="search"
      :sort-by.sync="sortBy"
      :sort-desc.sync="sortDesc"
      @load="load"
    >
      <template v-slot:top>
        <v-text-field
          v-model="search"
          label="닉네임, 이메일로 회원 검색"
          class="mx-8"
          append-icon="mdi-account-search"
        ></v-text-field>
      </template>
      <template #item.user_no="{ value }">
        <div class="mr-4">{{ value }}</div>
      </template>
      <template #item.user_nickname="{ item }">
        <nick-name-module :item="item.user_nickname" :itemno="item.user_no" />
      </template>
      <template #item.user_email="{ value }">
        <a :href="`mailto:${value}`"> {{ value }} </a>
      </template>

      <template #item.user_active="{ value }">
        <v-row>
          <v-chip
            v-if="value === `ACTIVE`"
            color="blue lighten-1"
            text-color="white"
            text-align="center"
          >
            ✓ active
          </v-chip>
          <v-chip v-else color="grey" text-color="white">inactive </v-chip>
        </v-row>
      </template>
      <template v-slot:item.actions="props">
        <v-icon small @click="goodbye(props.item)"> mdi-close-octagon</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import NickNameModule from "./NickNameModule.vue";
import { userList, inactiveUser } from "@/api/admin.js";

export default {
  name: "UserList",
  components: {
    NickNameModule,
  },
  data() {
    return {
      search: "",
      page: "",
      sortBy: "status",
      sortDesc: false,
      headers: [
        {
          text: "번호",
          align: "center",
          value: "user_no",
          sortable: false,
        },
        {
          text: "닉네임",
          sortable: false,
          value: "user_nickname",
        },
        {
          text: "이메일",
          sortable: false,
          value: "user_email",
        },
        {
          text: "신고수",
          sortable: true,
          value: "report",
        },
        {
          text: "정지 여부",
          value: "user_active",
        },
        {
          text: "회원 정지",
          value: "actions",
          align: "center",
          sortable: false,
        },
      ],
      users: [],
    };
  },
  methods: {
    goodbye(item) {
      if (item.user_active != `ACTIVE`) alert("이미 비활성화된 회원입니다.");
      else {
        let obj1 = { user_no: item.user_no };
        var message =
          "정말 " + item.user_nickname + "님을 비활성화 시키겠습니까?";
        if (confirm(message)) {
          alert("회원을 비활성화 시켰습니다.");
          inactiveUser(obj1);
          location.reload();
        } else {
          alert("취소하였습니다.");
        }
      }
    },
  },
  created() {
    userList(
      (response) => {
        this.users = response.data.data;
        delete this.users[0];
      },
      (error) => {
        if (error) console.log("er");
      }
    );
  },
};
</script>

<style></style>
