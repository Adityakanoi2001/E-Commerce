<template>
  <div>
    <input type="text" :value="value" @input="updateValue" />
  </div>
</template>

<script>
export default {
  name: "MySearch",
  props: ["value"],
  methods: {
    updateValue(event) {
      this.$emit("input", event.target.value);
    },
  },
};
</script>
<style scoped>
input[type="text"] {
  width: 75vw;
  margin-top: 1em;
  height: 5vh;
  border-radius: 1.5em;
}
</style>
