<template>
  <div class="container">
    <img src="../assets/OrderConfirm.jpeg" alt="Order Confirm" />
    <div class="buttons">
      <button class="btn offers" @click="products">Explore More Offers</button>
      <button class="btn btn-primary" @click="Orders">Check Your Orders</button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    Orders() {
      this.$router.push("/userdetails/myorders");
    },
    products() {
      this.$router.push("/products");
    },
  },
};
</script>
<style scoped>
template {
  background: azure;
}
.container img {
  height: 60vh;
  width: 50vw;
}
.buttons {
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* align-items: center; */
  /* align-items: space-around; */
  /* margin: 1em; */
  /* width: 10vw; */
  /* border: 2px solid red; */
}
.offers {
  font-size: 3em;
  font-style: oblique;
}
.btn {
  margin: 1em;
}
.btn-primary {
  width: 20vw;
  /* position: relative;
  right: 0; */
  margin-left: 30em;
}
.btn-primary:hover {
  opacity: 0.8;
}
</style>
