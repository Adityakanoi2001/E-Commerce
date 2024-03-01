<template>
  <div>
    <Header></Header>
    <SliderPage></SliderPage>
    <Footer></Footer>
  </div>
</template>
<script>
import SliderPage from "@/components/SliderPage.vue";
import Header from "@/components/DeleteHeader.vue";
import Footer from "@/components/AnotherFooter.vue";
export default {
  name: "HeaderView",
  components: {
    SliderPage,
    Header,
    Footer,
  },
};
</script>
<style scoped></style>
