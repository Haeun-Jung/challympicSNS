<template>
  <div>
    <v-btn
      color="primary"
      class="text-none"
      depressed
      :loading="isSelecting"
      @click="onButtonClick"
    >
      이미지 선택
    </v-btn>
    <input
      ref="uploader"
      class="d-none"
      type="file"
      accept="image/*"
      @change="onFileChanged"
    />
  </div>
</template>

<script>
export default {
  name: "ProfileUploadButton",
  props: {
    selectedFile: Object,
  },
  data() {
    return {
      defaultButtonText: "이미지선택",
      //selectedFile: null,
      isSelecting: false,

      //	fileInfo: [{ fileName: "" }, { filePath: "" }],
    };
  },
  computed: {
    /*	buttonText() {
				return this.selectedFile
					? this.selectedFile.name
					: this.defaultButtonText;
			},*/
  },
  methods: {
    onButtonClick() {
      this.isSelecting = true;
      window.addEventListener(
        "focus",
        () => {
          this.isSelecting = false;
        },
        { once: true }
      );

      this.$refs.uploader.click();
    },
    onFileChanged(e) {
      this.selectedFile = e.target.files[0];
      this.fn = this.selectedFile.name;
      alert(this.fn);
      this.fp = (window.URL || window.webkitURL).createObjectURL(
        this.selectedFile
      );
      alert(this.fp);
      /*var path = (window.URL || window.webkitURL).createObjectURL(
					this.selectedFile
				);
				console.log("path", path);*/
      // do something
    },
  },
};
</script>

<style>
.v-icon--left {
  margin-right: 8px;
}
</style>
