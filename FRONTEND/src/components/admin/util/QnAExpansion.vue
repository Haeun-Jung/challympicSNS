<template>
  <div>
    <div>
      <v-list-item-title class="ma-2 ml-2 pr-2">
        [문의 제목] {{ item.qna_title }}
      </v-list-item-title>

      <v-list-item-subtitle class="ma-2 ml-2 grey--text">
        <v-icon>mdi-calendar-month</v-icon>
        {{ item.qna_question_regdate }}
        <!-- <v-icon>mdi-clipboard-text-outline</v-icon>
				{{ item.user_nickname }} -->
      </v-list-item-subtitle>

      <v-list-item-subtitle>
        <v-sheet
          height="4rem"
          max-height="4rem"
          max-width="750px"
          outlined
          class="overflow-y-auto ml-5 mb-1"
          shaped
          rounded
        >
          <div v-html="item.qna_question"></div>
        </v-sheet>
      </v-list-item-subtitle>
    </div>
    <div v-if="item.qna_answer_regdate != item.qna_question_regdate">
      <v-divider />
      <div>
        <v-list-item-subtitle class="ma-2 ml-2">
          <div>
            [문의 답변]
            <span class="expansion-overflow">{{ item.qna_answer }}</span>
          </div>
        </v-list-item-subtitle>
        <v-list-item-subtitle class="ma-2 ml-2 grey--text">
          {{ item.qna_answer_regdate }}
        </v-list-item-subtitle>
      </div>
    </div>
    <div v-else>
      <v-divider />
      <div>
        <v-list-item-subtitle class="ma-2 ml-2 grey--text">
          아직 등록된 답변이 없습니다.
        </v-list-item-subtitle>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "QnAExpansion",
  props: {
    item: Object,
  },
};
</script>

<style>
.expansion-overflow {
  /*text-overflow: scroll;
		overflow-y: scroll;
		*/
  overflow: hidden;
  max-width: 500px;
}
</style>
