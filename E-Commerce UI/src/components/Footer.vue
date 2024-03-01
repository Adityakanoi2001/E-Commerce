<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
    />
    <footer class="Footer">
      <div class="container">
        <div class="item-1">
          <p class="heading">Get To Know Us</p>
          <ul>
            <li>
              <a href="https://www.quinbay.com/about" target="_blank"
                >- About Us</a
              >
            </li>
            <li><a href="">- Policy</a></li>
          </ul>
        </div>
        <div class="connect">
          <p class="heading">Connect With Us</p>
          <ul>
            <li>
              <a
                href="https://www.instagram.com/bliblidotcom/?hl=en"
                target="_blank"
                ><i class="fa fa-instagram"></i
              ></a>
            </li>
            <li>
              <a href="https://www.facebook.com/bliblidotcom/" target="_blank"
                ><i class="fa fa-facebook"></i
              ></a>
            </li>
            <li>
              <a
                href="https://twitter.com/bliblidotcom?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
                target="_blank"
                ><i class="fa fa-twitter"></i
              ></a>
            </li>
            <!-- <li>
              <a href="#" class="fa fa-facebook"></a>
              <a href="#" class="fa fa-twitter"></a>
            </li> -->
          </ul>
        </div>
        <div class="banner">
          <img
            src="https://constant.myntassets.com/web/assets/img/6c3306ca-1efa-4a27-8769-3b69d16948741574602902452-original.png"
            alt=""
          />
          <p><b>100% ORIGINAL</b></p>
          <p>Products</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Footer",
  data() {
    return {};
  },
};
</script>

<style scoped>
footer {
  position: absolute;
  bottom: 0;
  background-color: black;
  color: white;
  width: 100vw;
}
.container {
  display: flex;
  justify-content: space-around;
  margin-right: 25vw;
  margin-left: 25vw;
  width: 50vw;
}
.heading {
  text-decoration-line: underline;
}
img {
  margin-top: 0.5em;
  width: 48px;
  height: 40px;
}
ul {
  display: flex;
  justify-content: space-around;
}
ul li {
  font-weight: bold;
  list-style: none;
}
ul li a {
  text-decoration: none;
  color: white;
}
</style>
