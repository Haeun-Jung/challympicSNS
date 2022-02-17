<template>
  <v-card
    class="mt-4"
    :color="$vuetify.theme.dark ? '#2a2a2a' : 'grey lighten-4'"
    elevation="0"
    max-width="600"
  >
    <v-data-table
      :headers="headers"
      :items="challenges"
      item-key="challenge_no"
      class="elevation-1"
      :hide-default-footer="true"
    >
      <template v-slot:item="{ item }">
        <div v-bind="sumField(item.post_cnt)"></div>
      </template>
    </v-data-table>

    <v-card-title>
      <v-row> </v-row>
      <v-row>
        <v-icon
          :color="checking ? 'red lighten-2' : 'indigo'"
          class="mr-5; ml-5"
          size="64"
          @click="takePulse"
        >
          mdi-account-group
        </v-icon>
        <div>
          <div class="text-caption grey--text">전체 챌린지 참여 횟수</div>
          <span class="text-h3 font-weight-black">
            {{ setsum }}
          </span>
          <strong> 회</strong>
        </div>
      </v-row>
      <v-spacer></v-spacer>
    </v-card-title>

    <v-sheet color="transparent">
      <v-sparkline
        :key="String(avg)"
        :smooth="16"
        :gradient="['#f72047', '#ffd200', '#1feaea']"
        :line-width="3"
        :value="heartbeats"
        auto-draw
        stroke-linecap="round"
      ></v-sparkline>
    </v-sheet>
  </v-card>
</template>

<script>
import { challengeList } from "@/api/admin";
const exhale = (ms) => new Promise((resolve) => setTimeout(resolve, ms));
export default {
  data() {
    return {
      checking: false,
      setsum: 0,
      index: 0,
      heartbeats: [],
      headers: [],
      challenges: [],
    };
  },
  computed: {
    avg() {
      const sum = this.heartbeats.reduce((acc, cur) => acc + cur, 0);
      const length = this.heartbeats.length;
      if (!sum && !length) return 0;
      return Math.ceil(sum / length);
    },
  },

  methods: {
    sumField(value) {
      if (this.index < Object.keys(this.challenges).length) {
        this.index++;
        this.setsum += value;

        //alert("done!" + this.setsum);
      }
    },
    heartbeat() {
      return Math.ceil(Math.random() * (120 - 80) + 80);
    },
    async takePulse(inhale = true) {
      this.checking = true;
      inhale && (await exhale(1000));
      this.heartbeats = Array.from({ length: 20 }, this.heartbeat);
      this.checking = false;
    },
  },

  created() {
    this.takePulse(false);
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
</script>
