<template>
  <v-main>
      <tool-bar />
    <side/>

    계정 설정 페이지 -> 사이드 메뉴 + 가운데 컴포넌트로 구성
    설정   개인정보 수정
        ㄴ 프로필 수정
        ㄴ 비밀번호 변경
        ㄴ 문의하기
        ㄴ 회원 탈퇴
  </v-main>
</template>

<script>
import ToolBar from "../components/layout/ToolBar.vue";
import Side from "../components/account/layout/desktop/Side.vue";
export default {
components:{
    Side, ToolBar
}
}
</script>

<style>

</style>