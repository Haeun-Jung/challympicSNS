<template>
<<<<<<< HEAD
	<v-main>
		<tag-bar />
		<fab-button />
		<v-container fluid>
			<v-layout row>
				<!-- 패딩pr-16 16준게 최선..사이드바 어디에놔도이상함..-->
				<!--데스크탑-->
				<v-flex xs3 class="hidden-md-and-down">
					<v-container fluid>
						<v-layout row>
							<v-flex xs2>
								<v-layout column>
									<v-flex> </v-flex>
								</v-layout>
							</v-flex>
							<v-flex xs8>
								<v-layout column>
									<v-flex>
										<side />
									</v-flex>
								</v-layout>
							</v-flex>
						</v-layout>
					</v-container>
				</v-flex>
				<!-- 반응형 처리 -->
				<v-flex
					:class="{
						'pa-4': $vuetify.breakpoint.smAndDown,
						'ma-3': $vuetify.breakpoint.mdAndUp,
					}"
					xs12
					sm6
					md7
				>
					<v-layout column>
						<v-flex>
							<router-view />
						</v-flex>
					</v-layout>
				</v-flex>
			</v-layout>
		</v-container>
	</v-main>
=======
  <v-main>
    <tool-bar />
    <tag-bar />
    <side />
    <fab-button />
    <!-- 리스트 공간
  -->
  </v-main>
>>>>>>> 59b10cbf8e03668b5e7e2f3b3eda90cb0aab7a68
</template>

<script>
import ToolBar from "../components/layout/ToolBar.vue";
import TagBar from "../components/layout/TagBar.vue";
import Side from "../components/layout/Side.vue";
import FabButton from "../components/button/FabButton.vue";
export default {
  components: { ToolBar, TagBar, Side, FabButton },
};
</script>
