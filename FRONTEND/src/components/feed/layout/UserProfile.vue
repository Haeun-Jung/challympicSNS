<template>
    <v-container>
        <!-- PC -->
        <v-row v-if="!isMobile()" class="profile-wrapper">
            <v-avatar size="150">
                <img :src="profileUrl" alt="John" />
            </v-avatar>
            <v-col align-self="center">
                <v-container>
                    <!-- 이름 -->
                    <v-row>
                        <v-col align-self="center" class="name-wrapper">
                            <v-list-item-title class="title-wrapper">
                                <!-- 타이틀이 있을 때만 -->
                                <img class="medal-icon" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/>
                                <div class="header-title font-weight">스쿼트 왕</div>
                                <div class="user-name font-weight">박싸피</div>
                            </v-list-item-title>
                        </v-col>
                        <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
                        <v-col md="2" class="follow-wrapper" align-self="center">
                            <div class="font-weight">팔로워</div>
                            <div class="show-folllow-modal" @click="openFollowerDialog">3</div>
                            <follow-like-modal v-if="follower" @close-modal="follower=false" type="follower" :users="follows"></follow-like-modal>
                        </v-col>
                        <v-col md="2" class="follow-wrapper" align-self="center">
                            <div class="font-weight">팔로잉</div>
                            <div class="show-folllow-modal" @click="openFollowingDialog">3</div>
                            <follow-like-modal v-if="following" @close-modal="following=false" type="following" :users="follows"></follow-like-modal>
                        </v-col>
                    </v-row>
                </v-container>
            </v-col>
        </v-row>
        <!-- Mobile -->
        <v-row v-else>
            <!-- 이름 -->
            <v-row>
				<v-col md="6" class="profile-setting-avatar-container">
					<v-avatar size="150" class="avatar-wrapper">
						<img :src="profileUrl" alt="John" />
					</v-avatar>
				</v-col>
            </v-row>
            <v-row>
				<v-col>
					<v-container>
						<!-- 타이틀과 이름 -->
						<v-row class="name-content">
							<v-col>
                                <v-list-item-title class="title-wrapper">
                                    <img class="medal-icon" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/>
                                    <div class="header-title font-weight">스쿼트 왕</div>
                                </v-list-item-title>
                                <div class="user-name bottom-blank font-weight">박싸피</div>
                                <!-- 상대 프로필일 때
                                <v-btn v-if="user.isFollowing" @click="follow(user.userNo)" color="#3396F4"  class="white--text rounded-xl" small>
                                    팔로우
                                </v-btn>
                                <v-btn v-else @click="follow(user.userNo)" color="#3396F4" class="rounded-xl" small outlined>
                                    팔로잉
                                </v-btn>
                                -->
							</v-col>
						</v-row>
                        <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
                        <v-row>
                            <v-col md="2" class="follow-wrapper" align-self="center">
                            <div class="font-weight">팔로워</div>
                            <div class="show-folllow-modal" @click="openFollowerDialog">3</div>
                            <follow-like-modal v-if="follower" @close-modal="follower=false" type="follower" :users="follows"></follow-like-modal>
                        </v-col>
                        <v-col md="2" class="follow-wrapper" align-self="center">
                            <div class="font-weight">팔로잉</div>
                            <div class="show-folllow-modal" @click="openFollowingDialog">3</div>
                            <follow-like-modal v-if="following" @close-modal="following=false" type="following" :users="follows"></follow-like-modal>
                        </v-col>
                        </v-row>
					</v-container>
				</v-col>
			</v-row>
        </v-row>
    </v-container>
</template>

<script>
import FollowLikeModal from "@/components/common/FollowLikeModal.vue";
export default {
     name: "UserProfile",
    components: {
        FollowLikeModal,
    },
    props: {
        type: String,
    },
    data() {
        return {
            profileUrl: "https://cdn.vuetifyjs.com/images/john.jpg",
            follower: false,
            following: false,
            follows: [
                {
                follow: false,
                avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
                name: 'Jason Oner',
                title: '밥 잘먹는'
                },
                {
                follow: false,
                avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
                name: 'Mike Carlson',
                title: '스쿼트 장인'
                },
                {
                follow: false,
                avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
                name: 'Cindy Baker',
                },
            ],
        }
    },
    methods: {
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
        moveToProfile() {
            this.$router.push("/user/account/:userNo/");
        },
        openFollowerDialog() {
            this.follower = !this.follower;
        },
        openFollowingDialog() {
            this.following = !this.following;
        }
    }
}
</script>

<style scoped>
.profile-wrapper {
    margin-bottom: 30px;
}
.v-avatar {
    width: 130px;
}
.title-wrapper {
    display: inline-flex;
    margin-bottom: 4px;
}
.medal-icon {
    width: 30px;
    margin-right: 6px;
}
.header-title {
    font-size: 18px;
    color: rgb(138, 138, 138);
    margin-top: 3px;
}
.name-wrapper {
    padding-left: 30px;
}
.user-name {
    font-size: 20px;
    margin-left: 8px;
}
.follow-wrapper {
    text-align: center;
    font-size: 17px;
}
.font-weight {
    font-weight: bold;
}
.show-folllow-modal {
    cursor: pointer;
}

/* 모바일 */
.profile-setting-avatar-container {
    display: table;
}

.avatar-wrapper {
    display: table-cell;
}

.name-content {
    text-align: center;
    margin-bottom: 2px;
}

.bottom-blank {
    margin-bottom: 6px;
}
</style>