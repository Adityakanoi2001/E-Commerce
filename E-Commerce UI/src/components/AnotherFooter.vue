<template>
  <div>
    <footer class="Footer">
      <div class="container">
        <div class="item-1">
          <p class="heading">Get To Know Us</p>
          <ul class="know">
            <li>
              <a href="https://www.quinbay.com/about" target="_blank">
                <pre>AboutUs </pre>
              </a>
            </li>
            <br />
            <li>
              <a href="https://www.quinbay.com/privacy-policy" target="_blank">
                Policy</a
              >
            </li>
          </ul>
        </div>
        <div class="connect">
          <p class="heading">Connect With Us</p>
          <ul>
            <li>
              <a
                href="https://www.instagram.com/bliblidotcom/?hl=en"
                target="_blank"
                ><i class="fa fa-instagram fa-lg"></i>
              </a>
            </li>
            <li>
              <a href="https://www.facebook.com/bliblidotcom/" target="_blank"
                ><i class="fa fa-facebook fa-lg"></i
              ></a>
            </li>
            <li>
              <a
                href="https://twitter.com/bliblidotcom?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
                target="_blank"
                ><i class="fa fa-twitter fa-lg"></i
              ></a>
            </li>
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
<style scoped>
footer {
  /* position: fixed;
  bottom: 0; */
  /* height: 13vh; */
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
  /* border: 2px solid red; */
  display: flex;
  justify-content: space-around;
  /* width: 5vw; */
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
