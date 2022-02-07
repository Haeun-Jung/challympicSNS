<template>
	<v-main>
		<v-row class="justify-center pt-16">
			<v-icon size="86">mdi-alert-octagon</v-icon>
		</v-row>
		<v-row class="justify-center">
			<v-col col="2"></v-col>
			<v-col cols="8">
				<v-card color="transparent" elevation="0">
					<v-card-title class="text-h6">
						<span> 관리자 페이지는 모바일 </span>
						<span> 이용이 제한됩니다 </span>
						<span>
							<v-icon>mdi-cellphone-remove</v-icon>
						</span>
					</v-card-title>
				</v-card>
			</v-col>
			<v-col col="2"></v-col>
		</v-row>
	</v-main>
</template>

<script>
	export default {
		name: "AdminMobile",
	};
</script>

<style></style>
