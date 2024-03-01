<template>
  <div>
    <Header></Header>
    <div class="loader-container">
      <span class="loader"></span>
    </div>
    <span class="loading">Loading</span>
  </div>
</template>
<script>
import Header from "@/components/DeleteHeader.vue";
export default {
  components: {
    Header,
  },
  created: function () {
    console.log("From Search results page", this.$globalData.searchTerm);
    setTimeout(() => {
      this.$router.push(`/searchResults/${this.$globalData.searchTerm}`);
    }, 1000);
  },
};
</script>
<style scoped>
.loader-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #000;
}
.loader-container {
  width: 80px;
  height: 4px;
  margin: 0 auto;
  background-color: #aaa;
  border-radius: 20px;
  overflow: hidden;
}
.loader {
  width: 60%;
  height: 100%;
  background-color: #de4b4b;
  display: block;
  animation: animate ease-out 1s infinite;
}
.loading {
  margin: 8px auto;
  width: 80px;
  text-align: center;
  font-size: 16px;
  font-weight: 700;
  color: #aaa;
}
@keyframes animate {
  0% {
    transform: translatex(-50%);
  }
  50% {
    transform: translatex(100%);
  }
  100% {
    transform: translatex(-50%);
  }
}
</style>
