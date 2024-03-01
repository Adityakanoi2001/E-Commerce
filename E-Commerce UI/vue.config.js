const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "^/api/products": {
        target: "http://10.20.2.120:8099", // http://192.168.156.50:8085/api/products/allProducts
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
      "^/api/cart": {
        target: "http://10.20.2.120:8112",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
      "^/api/orderDetails": {
        target: "http://10.20.2.120:8112",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
      "^/api/user": {
        target: "http://10.20.3.153:8780",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
      "^/api/merchant": {
        target: "http://10.20.3.153:8001",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
    },
  },
});
