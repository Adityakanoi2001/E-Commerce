<template>
  <div>
    <DeleteHeader></DeleteHeader>
    <div v-if="!isChildRoute">
      <h1>User Details Page</h1>
      <router-link to="/userdetails/personaldetails">
        <button class="btn btn-secondarynn">Personal Details</button>
      </router-link>
    </div>
    <div v-if="!isChildRoute">
      <router-link to="/userdetails/myorders">
        <button class="btn btn-secondarybb">Orders</button>
      </router-link>
    </div>
    <Footer v-if="!isChildRoute"></Footer>
    <router-view />
  </div>
</template>
<script>
import DeleteHeader from "@/components/DeleteHeader.vue";
import Footer from "@/components/Footer.vue";
export default {
  data() {
    return {};
  },
  components: {
    DeleteHeader,
    Footer,
  },
  computed: {
    isChildRoute() {
      return (
        this.$route.path.includes("personaldetails") ||
        this.$route.path.includes("myorders")
      );
    },
  },
};
</script>
<style scoped>
.btn btn-secondary {
  cursor: pointer;
}
.btn btn-secondarybb {
  margin-top: 5px;
}
</style>
