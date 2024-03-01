<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="my-first-view">
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@1,300&display=swap"
      rel="stylesheet"
    />
    <Header></Header>
    <h1>Proucts Description Page</h1>
    <div id="row">
      <div id="row1">
        <img
          :src="products[0].imageURL"
          style="height: 50vh; width: 40vh"
        /><br />
      </div>
      <div id="row2">
        <div class="details">
          <!-- {{ products[index].merchantId }}<br /> -->
          Title: {{ products[0].productName }} <br />
          Description: {{ products[0].productDescription }}<br />
          <!-- {{ products[index - 1].productId }}<br /> -->
          <b>Price:{{ products[0].price }}<br /></b>
          Sold By:{{ products[0].merchantName }}<br />
          <!-- <h3>Other Merchants</h3>
        <div v-for="merchant in merchantsList" :key="merchant.id">
          <p>{{ merchant.merchantName }}</p>
          <p>{{ merchant.price }}</p>
        </div> -->
        </div>
        <div class="buttncontainer">
          <button id="button" @click="addToCart(products[0].productId)">
            Add To Cart
          </button>
          <button id="button" @click="buyNow(products[0].productId)">
            Buy Now
          </button>
        </div>
        <!-- <button
          class="merchant"
          @click="Merchant(products[0].merchantId, products[0].productName)"
        >
          Show More Merchants
        </button> -->
        <!-- <div
          class="otherMerchants"
          v-for="merchant in merchantsList"
          :key="merchant.id"
        >
          {{ this.merchantsList }}
          {{ merchant[0].merchantName }}
          {{ merchant[0].price }}
          {{ merchant[0].stock }}
        </div> -->
        <!-- {{ this.merchantsList.length }}
        {{ this.merchantsList }} -->
        <!-- <div v-for="merchant in this.merchantsList" :key="merchant.id"> -->
        Select Other Merchants Here
        <select
          v-model="selectedValue"
          @click="Merchant(products[0].merchantId, products[0].productName)"
          @change="values"
          class="form-control"
        >
          <option value="" disabled selected>select an option</option>
          <option
            v-for="merchant in this.merchantsList"
            v-bind:value="merchant.productId"
          >
            Marchnat Name : {{ merchant.merchantName }}, Price:
            {{ merchant.price }}, Stock Availabe: {{ merchant.stock }}
          </option>
        </select>
        <!-- </div> -->
      </div>
    </div>
    <FooterVue></FooterVue>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import axios from "axios";
import Header from "@/components/DeleteHeader.vue";
import FooterVue from "@/components/AnotherFooter.vue";
export default {
  name: "productDescription",
  data() {
    return {
      products: [],
      index: 0,
      merchantsList: [],
    };
  },
  computed: {
    ...mapGetters([
      "getToDoList",
      "getCartList",
      "getOrderList",
      "getProductId",
    ]),
    // productId() {
    //   console.log("product id is", this.selectedProduct.productId);
    //   return this.selectedProduct.productId;
    // },
    userId() {
      return localStorage.getItem("userId");
    },
    // selectedProduct() {
    //   return (
    //     this.products.find((product) => product.productId === this.index) || {}
    //   );
    // },
  },
  mounted: function () {
    this.$store.dispatch("getProductIdApi", {
      success: (res) => {
        this.products = res;
        console.log("Product are", res[0]);
      },
      productId: this.$route.params.productId,
    });
  },

  // this.$store.dispatch("getMerchnatsListApi", {
  //   success: (res) => {
  //     this.merchantsList = res.productsEntities;
  //     console.log("List of merchants are", this.merchantsList);
  //   },
  //   MerchantId: this.res[0].merchantId,
  //   productName: this.res[0].productName,
  // });

  // this.$store.dispatch("getProductIdApi", {
  //   success: (res) => {
  //     this.products = res.productsDtoList;
  //     console.log("results", res.productsDtoList);
  //   },
  // });

  // updated: function () {
  //   this.$store.dispatch("getMerchnatsListApi", {
  //     success: (res) => {
  //       this.merchantsList = res.productsEntities;
  //       console.log("List of merchants are", this.merchantsList);
  //     },
  //     MerchantId: this.res[0].merchantId,
  //     productName: this.res[0].productName,
  //   });
  // },

  methods: {
    ...mapActions(
      ["getToDoListApi"],
      ["SET_CART_LIST"],
      ["SET_ORDERS"],
      ["getMerchnatsListApi"],
      ["getProductIdApi"]
    ),
    values(e) {
      console.log(e.target.value);
    },
    Merchant(merchantId, productName) {
      this.$store.dispatch("getMerchnatsListApi", {
        success: (res) => {
          this.merchantsList = res.productsEntities;
          console.log("List of merchants are", this.merchantsList);
        },
        MerchantId: merchantId,
        productName: productName,
      });
      // this.showMerchant = true;
    },
    buyNow(productId) {
      const orderDetails = {
        productId,
        userId: this.userId,
      };
      axios
        .post("/api/orderDetails/addNewOrder", orderDetails)
        .then((response) => {
          console.log(response);
          this.SET_ORDERS(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
      this.$router.push("/checkout");
    },
    addToCart(productId) {
      const requestBody = {
        productId,
        userId: this.userId,
      };
      axios
        .post("/api/cart/add", requestBody)
        .then((response) => {
          console.log(response);
          this.SET_CART_LIST(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  components: {
    Header,
    FooterVue,
  },
  // mounted() {
  //   console.log(this.$route.params); // after the '/' symbol us params
  //   this.index = this.$route.params.productId;
  //   console.log("Index", this.index);
  // },
  // this.$store.dispatch("getMerchnatsListApi", {
  //   success: (res) => {
  //     this.merchantsList = res.productsEntities;
  //     console.log("List of merchants are", this.merchantsList);
  //     // console.log("merchantId", this.selectedProduct.merchantId);
  //     // console.log("productName", this.selectedProduct.productName);
  //   },
  //   // MerchantId: this.selectedProduct.merchantId,
  //   // productName: this.selectedProduct.productName,
  // });
};
</script>
<style scoped>
#row {
  display: flex;
}
#row1 {
  flex: 40%;
  /* justify-content: center;
  align-items: center; */
  margin-top: 100px;
  margin-left: 80px;
  /* border: 2px solid black; */
}
#row2 {
  flex: 40%;
  /* border: 2px solid black; */
  justify-content: center;
  align-items: center;
  margin-top: 200px;
  margin-right: 100px;
  /* border-inline: 10vh; */
  font-size: 20px;
}
#button {
  background-color: green;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border-radius: 1.5em;
}
/* #button:hover {
  border: 1px solid black;
} */
.details {
  font-family: "Open Sans", sans-serif;
  /* border: 2px solid red; */
}
.buttncontainer {
  /* border: 2px solid red; */
  margin-top: 2em;
  display: flex;
  justify-content: space-around;
}
</style>
