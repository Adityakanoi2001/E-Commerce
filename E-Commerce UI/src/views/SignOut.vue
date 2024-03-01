<template>
  <div>
    <Header></Header>
    <button class="btn btn-primary" @click="signOut">Confirm Signout</button>
  </div>
</template>
<script>
import Header from "@/components/DeleteHeader.vue";
export default {
  components: {
    Header,
  },
  data() {
    return {};
  },
  methods: {
    signOut() {
      // this.$store.commit("CLEAR_USER_DATA");
      // this.$globalData.notlogin = true;
      localStorage.removeItem("loginStatus");
      localStorage.removeItem("userId");
      this.$router.push("/userlogin");
      this.$toasted.show("Logged Out Successfully", {
        position: "top-center",
        duration: 3000,
      });
    },
  },
};
</script>
<style scoped>
.btn {
  margin-top: 3em;
}
</style>
