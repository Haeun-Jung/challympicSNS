<template>
<v-col
      cols="12"
      sm="2"
      md="4"
      class="menu-size"
    >
      <v-dialog
        ref="dialog"
        v-model="modal"
        :return-value.sync="date"
        persistent
        width="290px"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="date"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="date"
          scrollable
        >
          <v-spacer></v-spacer>
          <v-btn
            text
            color="primary"
            @click="modal = false"
          >
            Cancel
          </v-btn>
          <v-btn
            text
            color="primary"
            @click="saveDate"
          >
            OK
          </v-btn>
        </v-date-picker>
      </v-dialog>
    </v-col>
</template>

<script>

export default {
  name: "EndDatePicker",
  data: () => ({
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      modal: false,
  }),
  methods: {
    saveDate() {
      this.$refs.dialog.save(this.date);
      this.$emit("endDate", this.date);
    }
  }
}
</script>

<style scoped>

.menu-size {
  height: 20px;
  padding: 0px;
  margin-top: 0px;
}

.v-text-field {
    padding: 0px;
    margin-top: -4px;
}

</style>