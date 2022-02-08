<template>
  <v-container fluid class="footer">
		<v-row class="content-wrapper">
      <span class="footer-content content-pointer" @click="openNotionPage">소개</span>
      <span class="footer-content content-pointer" @click="openPostmanPage">API</span>
      <span class="footer-content content-pointer" @click="openTermsDialog">약관</span>
      <footer-modal v-if="terms" type="terms" @close-dialog="openTermsDialog"></footer-modal>
      <span class="footer-content content-pointer" @click="openPrivacyDialog">개인정보처리방침</span>
      <footer-modal v-if="privacy" type="privacy" @close-dialog="openPrivacyDialog"></footer-modal>
      <span class="content-pointer" @click="openLocationDialog">위치</span>
      <footer-modal v-if="location" type="location" @close-dialog="openLocationDialog"></footer-modal>
    </v-row>
    <v-row>
      Copyright © Daejeon 1 team All Rights Reserved.
    </v-row>
	</v-container>
</template>

<script>
import FooterModal from './FooterModal.vue'
export default {
  name: "SideFooter",
  components: { FooterModal },
  props: {
    type: String,
  },
  data: () => ({
    terms: false, //true : TermsDialog열림, false : TermsDialog닫힘
    privacy: false, //true : PrivacyDialog열림, false : PrivacyDialog닫힘
    location: false, //true : LocationDialog열림, false : LocationDialog닫힘
	}),
  methods: {
    openNotionPage() {
      window.open("https://www.notion.so/ssafy6commonpjt/_-6-b449c8f84a6843a282e8a9158fa207f0");
    },
    openPostmanPage() {
      window.open("https://restless-eclipse-28650.postman.co/workspace/%25EC%25B1%258C%25EB%25A6%25BC%25ED%2594%25BDFE~f53efbe6-c83b-436b-8535-be3f90e3b155/collection/18058395-9787387e-383d-4cce-ab46-27e4ce421d6f?ctx=documentation");
    },
    openTermsDialog() {
      this.terms = !this.terms;
    },
    openPrivacyDialog() {
      this.privacy = !this.privacy;
    },
    openLocationDialog() {
      this.location = !this.location;
    }
  }
}
</script>

<style scoped>
.footer {
  color: 	#C0C0C0;
  margin: 1%;
	padding-left: 10%;
	padding-right: 7%;
}
.content-wrapper {
  letter-spacing: 1px;
  margin-bottom: 6px;
}
.footer-content {
  margin-right: 7px;
}
.content-pointer {
  cursor: pointer;
}
</style>