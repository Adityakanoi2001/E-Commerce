<template>
  <div>
    <h3 style="text-align: center">product upload page</h3>
    <form @submit.prevent="submitForm" v-if="!formSubmitted">
      <span>Product Name</span><br />
      <input
        v-model="name"
        type="text"
        placeholder="Enter product name"
      /><br />
      <span>Price</span><br />
      <input v-model="price" type="int" /><br />
      <span>Product Description</span><br />
      <input
        v-model="description"
        type="text"
        placeholder="product description"
      /><br />
      <span>Product stock</span><br />
      <input
        v-model="stock"
        type="int"
        placeholder="Enter product stock"
      /><br />
      <p>Category Id</p>
      <select v-model="categoryid">
        <option value="1">Laptop</option>
        <option value="2">Mobile</option>
        <option value="3">T.V</option>
        <option value="4">Watches</option>
        <option value="5">Music Items</option></select
      ><br />
      <p>Brand Id</p>
      <select v-model="BrandId">
        <option value="1">Samsung</option>
        <option value="2">Apple</option>
        <option value="3">Sony</option>
        <option value="4">Realme</option>
        <option value="5">BliBli</option></select
      ><br />
      <span>Upload Image</span><br />
      <input type="text" id="myImg" name="image" />
      <button type="submit">Upload Product</button>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      name: "",
      email: "",
      gender: "",
      formSubmitted: false,
    };
  },
  methods: {
    submitForm: function () {
      this.formSubmitted = true;
    },
  },
};
</script>
<style>
form {
  padding: 30px;
  border: 2px solid black;
  border-radius: 5px;
}

input {
  padding: 10px 50px;
  margin: 4px;
}

span {
  font-size: 18px;
  margin: 4px;
  font-weight: 500;
}

.submit {
  font-size: 15px;
  color: #fff;
  background: #222;
  padding: 6px 12px;
  border: none;
  margin-top: 8px;
  cursor: pointer;
  border-radius: 5px;
}
</style>
