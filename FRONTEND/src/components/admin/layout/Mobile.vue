<template>
	<v-main>
		<v-row class="justify-center pt-16">
			<v-icon size="86">mdi-alert-octagon</v-icon>
		</v-row>
		<v-row class="justify-center">
			<v-col col="1"></v-col>
			<v-col cols="9">
				<v-card color="transparent" elevation="0">
					<v-card-title class="text-h6">
						<div>
							관리자 페이지는 모바일 접속이 제한됩니다. PC 환경으로
							이용바랍니다.
							<v-icon>mdi-cellphone-remove</v-icon>
						</div>
					</v-card-title>
				</v-card>
			</v-col>
			<v-col col="1"></v-col>
		</v-row>
	</v-main>
</template>

<script>
	export default {
		name: "AdminMobile",
	};
</script>

<style></style>
