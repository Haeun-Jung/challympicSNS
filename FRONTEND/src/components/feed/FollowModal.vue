<template>
    <v-dialog v-model="dialog" scrollable max-width="400px">
        <v-card>
            <v-card-title v-if="this.type === 'follower'" class="title-align">
                팔로워
                <v-btn class="cancel-btn" icon absolute top right @click="dialog = false">
                    <v-icon>mdi-close</v-icon>
                </v-btn>
            </v-card-title>
            <v-card-title v-if="this.type === 'following'" class="title-align">
                팔로잉
                <v-btn class="cancel-btn" icon absolute top right @click="dialog = false">
                    <v-icon>mdi-close</v-icon>
                </v-btn>
            </v-card-title>
        
            <v-divider></v-divider>

            <v-list class="overflow-y-auto" >
                <v-list-item
                    v-for="user in follows"
                    :key="user.name">
                    <v-list-item-avatar>
                    <v-img
                        :alt="`${user.name} avatar`"
                        :src="user.avatar"
                    ></v-img>
                    </v-list-item-avatar>

                    <v-list-item-content>
                        <!-- 배지.. -->
                        <!-- <v-img v-if="user.title" max-width="20" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/> -->
                        <v-list-item-subtitle v-text="user.title" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"></v-list-item-subtitle>
                        <v-list-item-title v-text="user.name" class="font-weight-bold"></v-list-item-title>
                    </v-list-item-content>

                    <v-list-item-icon>
                    <v-btn
                        v-if="user.isFollowing"
                        @click="follow(user.userNo)"
                        color="#3396F4"
                        class="white--text rounded-xl"
                        small
                        >
                        팔로우
                        </v-btn>
                        <v-btn
                        v-else
                        @click="follow(user.userNo)"
                        color="#3396F4"
                        class="rounded-xl"
                        small
                        outlined
                        >
                        팔로잉
                        </v-btn>
                    </v-list-item-icon>
                </v-list-item>
                </v-list>
            
        </v-card>
    </v-dialog>
</template>

<script>
export default {
  name: "FollowModal",   
  props: {
    type: { type: String },
  }, 
  data: ()=> ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘   
    follows: [
        {
          follow: true,
          avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
          name: 'Jason Oner',
          title: '밥 잘먹는'
        },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
          name: 'Mike Carlson',
          title: '스쿼트 장인'
        },
        {
          avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
          name: 'Cindy Baker',
        },
      ],
  }),
  methods: {
      follow(userNo) {
      console.log(userNo);
      // 팔로우 API 요청 보내기
      // 해당 유저에 대한 isFollowing 값 변경
    },
  },
  watch: {
    dialog() {
      return this.likeDialog || this.followDialog;
    },
  },
}
</script>

<style scoped>
.title-align {
  justify-content: center;
}
.cancel-btn {
    margin-top: -2px;
    margin-right: -4px;
}
.follow-btn {
    color: #fff;
}
.medal-icon {
    display: inline;
}

</style>