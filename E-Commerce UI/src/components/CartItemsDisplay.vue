<template>
  <div>
    <div v-if="!isEmpty" class="page">
      <h1>
        Total Items- <b>{{ cartProducts.length }}</b>
      </h1>
      <h2>Total Cart Value {{ totalCost }}</h2>
      <button class="btn checkout" @click="CheckOut()">Check Out</button>
      <div class="container">
        <div id="cart" v-for="product in cartProducts" :key="product.id">
          <img
            :src="product.imageURL"
            style="height: 35vh; width: 20vw"
          /><br />
          <div class="details">
            Title: {{ product.productName }}, <br />
            Price: {{ product.price }}
          </div>
          <div class="buttons">
            <button
              @click="deleteFromCart(product.productId)"
              class="btn btn-danger"
            >
              Delete from Cart
            </button>
            <span>Teja Srini</span>
            <button id="button" class="btn btn-primary" @click="buyNow()">
              Buy Now
            </button>
          </div>
          <!-- {{ product.category }}, {{ product.description }}, {{ product.price }}, -->
        </div>
      </div>
    </div>
    <div v-else>
      <h1>Cart Is Empty</h1>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
// import { response } from "express";
export default {
  computed: {
    ...mapGetters(["getItemsCartList"]),
    userId() {
      return localStorage.getItem("userId");
    },
  },
  data() {
    return {
      cartProducts: [],
      isCheckOut: true,
      isEmpty: false,
      totalCost: 0,
    };
  },
  created: function () {
    this.$store.dispatch("getItemsCartListApi", {
      success: (res) => {
        this.cartProducts = res.productsEntities;
        this.totalCost = res.totalCost;
        console.log("CARTPRODUCTS", res);
      },
      userID: this.userId,
    });
  },
  methods: {
    ...mapActions(["getItemsCartListApi"], ["SET_ORDERS"]),
    CheckOut() {
      this.$router.push("/checkoutfinal");
    },
    buyNow() {
      // const orderDetails = {
      //   productId,
      //   userId: this.userId,
      // };
      // axios
      //   .post("/api/orderDetails/addNewOrder", orderDetails)
      //   .then((response) => {
      //     console.log(response);
      //     this.SET_ORDERS(response.data);
      //   })
      //   .catch((error) => {
      //     console.log(error);
      //   });
      // // this.cartProducts = this.cartProducts.filter(
      // //   (i) => i.productId !== productId,
      // // this.getItemsCartListApi(this.cartProducts)
      // // );
      // axios.delete(`/api/cart/deleteCartProduct/${this.userId}/${productId}`),
      //   this.$router.push("/checkoutfinal");
      this.$router.push("/checkoutfinal");
    },
    deleteFromCart(productId) {
      axios.delete(`/api/cart/deleteCartProduct/${this.userId}/${productId}`);
      // .then((response) => {
      //   (this.isDelete = false), location.reload();
      //   console.log(response);
      // });
      this.$router.push("/loading");
    },
  },
  updated: function isCartEmpty() {
    if (this.cartProducts.length === 0) {
      this.isEmpty = true;
      console.log(this.isEmpty);
    }
  },
};
</script>
<style scoped>
#cart {
  /* display: flex; */
  /* flex-direction: column; */
  /* border: 2px solid black; */
  margin: 2em;
  padding: 3em;
}
.checkout {
  /* background-image: linear-gradient(#00bc40, #4c3ae3); */
  background-color: #12b0e8;
}
span {
  visibility: hidden;
}
.details {
  font-size: 1.6em;
}
.buttons {
  margin-top: 1em;
  /* padding: 2em; */
  /* border: 2px solid red; */
}
.container {
  /* border: 2px solid red; */
  overflow: auto;
}
</style>
