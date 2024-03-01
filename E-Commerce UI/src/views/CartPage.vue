<template>
  <div class="cartview">
    <Header></Header>
    <CartItemsDisplay></CartItemsDisplay>
    <!-- <Footer></Footer> -->
  </div>
</template>
<script>
import Header from "@/components/DeleteHeader.vue";
// import Footer from "@/components/AnotherFooter.vue";
import CartItemsDisplay from "@/components/CartItemsDisplay.vue";
export default {
  name: "CartPage",
  components: {
    Header,
    // Footer,
    CartItemsDisplay,
  },
  data() {
    return {};
  },
};
</script>
