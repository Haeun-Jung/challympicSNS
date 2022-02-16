<template>
    <span class="post-inline-tag">
        {{keyword}}
    </span>
</template>

<script>
// 현재 사용하지 않습니다!

export default {
    name: 'PostContentTag',
    props: {
        keyword: String,
    },
    methods : {
        onClickPostInlineTag(){
            console.log(this.keyword);
        }
    }
}
</script>

<style scoped>
  .post-inline-tag{
  	color: #3396f4 !important;
  }

  .post-inline-tag:hover{
    font-weight: bold !important;
    text-decoration: underline !important;
    cursor: pointer;
  }
</style>