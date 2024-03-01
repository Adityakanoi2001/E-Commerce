<!-- eslint-disable vue/multi-word-component-names -->
// eslint-disable-next-line vue/multi-word-component-names
<template>
  <div class="container">
    <div
      v-for="product in productsList"
      :key="product.productId"
      :class="['default-class']"
      @click="selectProduct(product)"
    >
      <img :src="product.imageURL" />
      <hr />
      Brand: <b>{{ product.productName }}</b>
      <br />
      Model: <b>{{ product.categoryId }} </b>
      <br />
      Price: <b>{{ product.price }} </b>
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  emits: ["product-selected"],
  props: {
    productsList: {
      type: Array,
      default: () => [],
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    selectProduct(product) {
      // console.log(product);
      this.$emit("product-selected", product, "data");
    },
  },
};
</script>

<style scoped>
.container {
  /* border: 2px solid red; */
  display: flex;
  flex-wrap: wrap;
  overflow: auto;
}
.default-class {
  border: 2px solid black;
  margin: 2em;
  border-radius: 30px;
  padding: 10px;
  box-shadow: 5px 10px black;
  height: 35vh;
  margin: 2em;
  width: 20vw;
}
.default-class:hover {
  background: aliceblue;
  transition: all;
  transition-duration: 1s;
  transform: scale(1.1, 1.2);
  cursor: pointer;
}
.default-class img {
  width: 20vw;
  width: 10vw;
  height: 20vh;
}
</style>
