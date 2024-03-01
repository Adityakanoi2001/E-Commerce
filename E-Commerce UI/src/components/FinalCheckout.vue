<template>
  <div>
    <!-- <h3>Checkout page</h3> -->
    <!-- {{ this.cartProducts }} -->
    <Header></Header>
    <h6>
      Total Items- <b>{{ cartProducts.length }}</b>
    </h6>
    <div class="container">
      <div id="cart" v-for="product in cartProducts" :key="product.id">
        <div class="details">
          product: {{ product.productName }}, <br />
          Price: {{ product.price }}
        </div>
      </div>
    </div>
    <h6>Total Cart Value {{ totalCost }}</h6>
    <button id="button" class="btn btn-primary" @click="AddDetails">
      Add Shipment Address
    </button>
    <div v-if="this.isClicked">
      <h3>payment details</h3>
      <h6>Address</h6>
      <textarea rows="4" cols="50" name="comment" form="usrform"></textarea>
      <h6>payment Details</h6>
      <input
        type="radio"
        id="online payment"
        name="fav_language"
        value="HTML"
      />
      <label for="html">Online payment</label><br />

      <input
        type="radio"
        id="cash on delivery"
        name="fav_language"
        value="HTML"
      />
      <label for="html">Cash On Delivery</label><br />
      <button @click="confirm">Confirm your order</button>
    </div>
  </div>
</template>
<script>
import Header from "./DeleteHeader.vue";
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
export default {
  name: "FinalCheckout",
  components: {
    // MyButton,
    // MySearch,
    Header,
  },
  computed: {
    ...mapGetters(["getItemsCartList"], ["getOrderList"]),
    userId() {
      return localStorage.getItem("userId");
    },
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
  data() {
    return {
      cartProducts: [],
      isClicked: false,
    };
  },
  methods: {
    ...mapActions(["getItemsCartListApi"], ["SET_ORDERS"]),
    AddDetails() {
      this.isClicked = !this.isClicked;
      console.log(this.isClicked);
    },

    confirm() {
      //   const orderDetails = {
      //     productId,
      //     userId: this.userId,
      //   };
      //   axios
      //     .post("/api/orderDetails/addNewOrder", orderDetails)
      //     .then((response) => {
      //       console.log(response);
      //       this.SET_ORDERS(response.data);
      //     })
      //     .catch((error) => {
      //       console.log(error);
      //     });
      //   axios.delete(`/api/cart/deleteCartProduct/${this.userId}/${productId}`),
      //     this.$router.push("/checkoutfinal")
      axios
        .post(`/api/cart/addAllToOrder/${this.userId}`)
        .then((response) => {
          location.reload();
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
      this.$router.push("/ordersuccess");
    },
  },
};
</script>
