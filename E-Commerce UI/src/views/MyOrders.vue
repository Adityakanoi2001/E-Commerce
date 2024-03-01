<template>
  <div class="Orders">
    <h1>Order History</h1>
    <div class="container">
      <div v-for="order in OrdersList" :key="order.id" class="item">
        <img :src="order.imageURL" />
        <div class="details">
          <p>Name: {{ order.productName }}</p>
          <p>Product Description: {{ order.productDescription }}</p>
          <p>
            Price: <b>{{ order.price }} </b>
          </p>
        </div>
      </div>
    </div>
    <!-- <Footer></Footer> -->
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
// import Footer from "@/components/AnotherFooter.vue";
export default {
  components: {
    // Footer,
  },
  data() {
    return {
      OrdersList: [],
      totalCost: "",
    };
  },
  computed: {
    // ...mapGetters(["getOrdersList"]),
    ...mapGetters(["getOrdersList"]),
  },
  methods: {
    ...mapActions(["getOrdersListApi"]),
  },
  created: function () {
    // eslint-disable-next-line no-debugger
    this.$store.dispatch("getOrdersListApi", {
      success: (res) => {
        this.OrdersList = res.productsEntities;
        this.totalCost = res.totalCost;
        console.log("Orders are", this.OrdersList);
        console.log("Orders are", res);
        console.log(this.totalCost);
      },
      value: localStorage.getItem("userId"),
    });
  },
};
</script>
<style scoped>
template {
  background: azure;
}
.container {
  /* border: 2px solid red; */
}
.details {
  /* text-align: start; */
  border: 2px solid rgb(15, 14, 14);
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
  margin-top: 1em;
}
.item {
  margin: 2em;
  /* border: 2px solid blueviolet; */
}
.item img {
  width: 30vw;
  height: 40vh;
}
</style>
