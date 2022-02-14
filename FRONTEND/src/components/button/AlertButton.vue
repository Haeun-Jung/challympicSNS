<template>
  <v-menu bottom left offset-y max-height="260" :width="menuWidth" display="block">
    <template v-slot:activator="{ on, attrs }">
      <v-btn icon v-bind="attrs" v-on="on">
        <div v-if="activeAlert" id="alert-badge"></div>
        <v-icon> mdi-bell-outline </v-icon>
      </v-btn>
    </template>

    <v-card v-if="alertList" width="300">
      <v-list class="overflow-y-auto">
        <v-list-item
          v-for="(item, i) in alertList"
          :key="i"
          :to="item.link1"
          class="px-3; mx-1;,my-2"
        >
          <v-list-item-title>
            {{ item.alert_content.split('"')[3] }}
            <span class="date-text">{{ dayjsRegDate(item.alert_regDate) }}</span>
          </v-list-item-title>
        </v-list-item>
      </v-list>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text @click="deleteAlert"> 모두 읽음 </v-btn>
      </v-card-actions>
    </v-card>
    <v-card v-else>
      <v-list>
        <v-list-item>
          <v-list-item-title>새로운 알림이 없습니다.</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-card>
  </v-menu>
</template>

<script>
import fromNow from "@/plugins/dayjs.js";
export default {
  name: "AlertButton",
  data() {
    return {
      alertMenu: [],
    }
  },
  computed: {
    menuWidth() {
      if (this.isMobile) {
        return 210
      }
      return 300
    },
    activeAlert() {
      if (this.alertMenu.length > 1) {
        return true;
      }
      return false;
    },
    alertList() {
      return this.$store.state.userStore.alertList;
    }
  },
  methods: {
    dayjsRegDate(regDate) {
      const parsedRegDate = regDate.split("T")[0].replace(/-/g, ".");
      return fromNow(parsedRegDate);
		},
    deleteAlert() {
      this.menu = false;
      setTimeout(() => {
        this.$store.commit('userStore/DELETE_ALERT');
      }, 500);
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
  }
}
</script>

<style scoped>
::v-deep .v-list-item {
  padding: 2px 16px;
}
::v-deep .v-list-item__title {
  white-space: normal;
}
</style>