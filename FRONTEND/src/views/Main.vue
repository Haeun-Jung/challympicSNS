<template>
<v-main>
  <tool-bar/>
  <!--
    <toolbar/>
    <tagbar/>
    <side/>
    -->
    <tag-bar/>
    <side/>
    <fab-button/>
  <!-- 리스트 공간
  -->

</v-main>
</template>

<script>
import ToolBar from '../components/layout/ToolBar.vue';
import TagBar from '../components/layout/TagBar.vue';
import Side from '../components/layout/Side.vue';
import FabButton from '../components/button/FabButton.vue';

//import Toolbar from '../components/layout/ToolBar.vue';
//import tagbar from '../components/layout/TagBar.vue';
//import side from '../components/layout/Side.vue';
//import fabBtn from '../components/fab/FabButton.vue';
export default {
  components : {ToolBar, TagBar, Side, FabButton},
 // components: { toolbar , tagbar, side ,fabBtn},
}
</script>

<style>

</style>