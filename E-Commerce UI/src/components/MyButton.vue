<template>
  <button class="btn" @click="myButtonHandler">{{ buttonText }}</button>
</template>

<script>
export default {
  name: "MyButtton",
  data() {
    return {};
  },
  emits: ["button-clicked"],
  props: {
    buttonText: {
      type: String,
      default: "Click Me",
      required: false,
    },
  },
  methods: {
    myButtonHandler() {
      this.$emit("button-clicked");
    },
  },
};
</script>

<style scoped>
.btn {
  width: 15vw;
  height: 5vh;
  border: 2px solid whitesmoke;
  /* margin-top: auto; */
  margin-top: 1.6em;
  /* margin-right: auto; */
  border-radius: 1.6em;
  cursor: pointer;
  color: white;
  background: black;
  /* background: white; */
}
</style>
