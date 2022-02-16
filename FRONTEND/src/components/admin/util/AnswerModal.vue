<template>
  <v-dialog
    v-model="dialogNote[item.qna_no]"
    scrollable
    lazy
    max-width="500"
    persistent
    :key="item.qna_no"
  >
    <v-card>
      <v-card-title class="lighten-2 card-title-align">
        답변
        <v-btn
          class="cancel-btn"
          absolute
          top
          right
          icon
          @click.stop="$set(dialogNote, item.qna_no, false)"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-divider />
      <div class="mx-6">
        <v-list class="overflow-y-auto">
          <v-list-item-title class="ma-2 ml-2 pr-2">
            [문의 제목] {{ item.qna_title }}
          </v-list-item-title>
          <v-list-item-subtitle class="ma-2 ml-2 grey--text">
            <v-icon>mdi-calendar-month</v-icon>
            {{ item.qna_question_regdate }}
            <!--	<v-icon>mdi-clipboard-text-outline</v-icon>
						{{ item.user_nickname }}-->
          </v-list-item-subtitle>
          <v-sheet
            height="150px"
            max-height="150px"
            outlined
            class="overflow-y-auto ma-5"
            shaped
            rounded
          >
            <div v-html="item.qna_question"></div>
          </v-sheet>
          <v-list-item-title class="ma-2 ml-2 pr-2">
            [답변 작성]
          </v-list-item-title>
          <v-list-item-subtitle class="ma-2 ml-2 grey--text">
            <v-icon>mdi-calendar-month</v-icon>
            현재 날짜 :{{ currentDate() }}
          </v-list-item-subtitle>
          <v-textarea
            outlined
            solo
            dense
            flat
            class="ma-5"
            label=""
            v-model="answer_content"
            ><v-html></v-html
          ></v-textarea>
          <v-card-actions>
            <v-spacer />
            <v-btn color="primary" flat @click="answer(item.qna_no)"
              >답변 등록</v-btn
            >
            <!--	@click.stop="$set(dialogNote, item.qna_no, false)"-->
          </v-card-actions>
        </v-list>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import { answer } from "@/api/admin";
export default {
  name: "AnswerModal",
  props: {
    item: Object,
    dialogNote: Object,
  },
  data() {
    return {
      answer_content: ``,
    };
  },
  methods: {
    answer(no) {
      let obj1 = { qna_no: no, qna_answer: this.answer_content };
      //	console.log(obj1);
      answer(obj1);
      alert("답변을 등록하였습니다.");
      this.$set(this.dialogNote, no, false);
      location.reload();
    },
    currentDate() {
      const current = new Date();
      const date = current.toISOString().split("T")[0];
      return date;
    },
  },
};
</script>

<style scoped>
.card-title-align {
  display: flex;
  justify-content: center;
}
.cancel-btn {
  margin-top: -4px;
  margin-right: -4px;
}
.cancel-icon {
  width: 16px;
}
.follow-btn {
  color: #fff;
}
.user-image {
  margin-right: 16px;
}
.medal-icon {
  position: absolute;
  top: 0.5rem;
  left: 2.3rem;
  width: 20px;
  height: 20px;
  z-index: 1;
}
</style>
