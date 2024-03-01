<template>
  <div>
    <h3>payment details</h3>
    <h6>Address</h6>
    <textarea rows="4" cols="50" name="comment" form="usrform"></textarea>
    <h6>payment Details</h6>
    <input type="radio" id="online payment" name="fav_language" value="HTML" />
    <label for="html">Online payment</label><br />

    <input
      type="radio"
      id="cash on delivery"
      name="fav_language"
      value="HTML"
    />
    <label for="html">Cash On Delivery</label><br />
    <button @click="confirm(product.productId)">Confirm your order</button>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {};
  },
  computed: {
    userId() {
      return localStorage.getItem("userId");
    },
  },
  methods: {
    confirm(productId) {
      this.$router.push("/ordersuccess");
      axios.delete(`/api/cart/deleteCartProduct/${this.userId}/${productId}`);
    },
  },
};
</script>
