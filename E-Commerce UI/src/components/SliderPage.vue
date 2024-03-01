<template>
  <div id="slider">
    <!-- <transition-group name="fade" tag="div"> -->
    <div id="images" v-for="i in [currentIndex]" :key="i">
      <img :src="currentImg" />
    </div>
    <!-- </transition-group> -->
    <h1>Our categories</h1>
    <div id="productcards">
      <img
        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1hwys00V6n6lLgm4m_f_wfIz8Af-fGwMi3w&usqp=CAU"
      />
      <img
        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRTjl3GJEI1B9TC4qK1iJnZmi7R-VyOUh3VA&usqp=CAU"
      />
      <img
        src="https://images.unsplash.com/photo-1615986200762-a1ed9610d3b1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YW5kcm9pZCUyMHR2fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"
      />
      <img
        src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTT5QZBTDJPhr3YlfN1xagwFt0v3uNykyVVfA&usqp=CAU"
      />
      <img
        src="https://d2d22nphq0yz8t.cloudfront.net/88e6cc4b-eaa1-4053-af65-563d88ba8b26/https://media.croma.com/image/upload/v1669124415/Croma%20Assets/Entertainment/Headphones%20and%20Earphones/Images/262015_4_xoyays.png/mxw_2256,f_auto"
      />
    </div>
    <h1>Top picks</h1>
    <div id="productcards">
      <img src="https://m.media-amazon.com/images/I/616FcX9aiEL._SL1200_.jpg" />
      <img
        src="https://images.pexels.com/photos/51383/photo-camera-subject-photographer-51383.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
      />
      <img src="https://m.media-amazon.com/images/I/61L5M+cAwDL._SL1000_.jpg" />
      <img src="https://m.media-amazon.com/images/I/71--IQUHVwL._SL1500_.jpg" />
      <img src="https://m.media-amazon.com/images/I/61YvHa6o94L._SL1500_.jpg" />
    </div>
    <h1>Our Partners</h1>
    <div id="partners">
      <img
        src="https://i.pinimg.com/originals/71/71/0f/71710f762b6af383a73f9760fda3a3ae.png"
      />
      <img
        src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw0NDQ0NDQ0ODQ0NDQ0NDQ0NDRANDg0NIRIYGBYSFBkdHTYiGhwmHBYUIzkiJyssPDw8GCE1OkEtPzYuNCwBCgoKDg0OFg8PFi4eFiAuLDk5OS8sLC4wMjAsLiwsLDksLiwuLCwsLCwvLCwuLC4vMCwsLi4sLi4uLiwsLC4sLv/AABEIAOAA4AMBIgACEQEDEQH/xAAbAAACAgMBAAAAAAAAAAAAAAAAAQIFAwQHBv/EAEcQAAIBAgEFCwkECQMFAAAAAAABAgMEEQUSExUhBjFBUVJjcpKjseIHMjNDYXGBg8IiI5OyFEJic5GhorPRNYLSFhckJVP/xAAZAQADAQEBAAAAAAAAAAAAAAAAAQIDBAX/xAA2EQACAQIDBAkCBQQDAAAAAAAAAQIDEQSh0RQhMVIFEhMVQVFxgbEzYSKRssHwIzI04SRy8f/aAAwDAQACEQMRAD8A5zcekqdOf5mYzJcekn05/mZjPUPQAAAYERgIAGRGIdxWAYgC4AAgC4gAAEMBgAgEAAIYAAEgMBDEUSAiMQxgRJEsYAACAy3PpKnTn+ZmMy1/SVOnPvZiNkyRiAYxWIiJCGIQhgwAQAACEAwAAABAAwAQgAAAljAAARSGMiMljGIAFcYDEMm40MQwE2FjPcekqdOfezGZLj0k+nPvZjNhARJANCsRESBjTAgNgIZIgGIYhgIQgGIAAAAAJuAAACGAABLKAYASMAACWxjGIYrlAAhkjNi49JPpz72YjNcekn0597MZsmIiMBDTFYQiQMdySIiQhiIiJERpkgIYh3AAACQAAAQ7AAASMBgAhgADJbGIYgJuUBIAJuMAEBNxo27jz59OfezEZ7jz59OfezCa3GIQxMaYrCEyQirkNCIkmBVySJEmIdxWIgMAAQiREVwABgK4CAYiRjARIljsAgAkoYAMkYAIZNykhAMRNxm7cekn0597MZluPPn0597MZpcqxDA2rO00ud9vNzc3gxxxx/wa5Y5G36vy/qMsRUlClKUeO75SN8JSjOtGMlud/hhqnnf6PELVHO9n4i0B+48rba/PktD2+7sNyZy1KrU/O9n4han53s/EWwD23Ec+S0F3dhuTOWpU6m55/h+INS88/wAPxFsAbdiOfJaB3bheTOWpU6l55/h+IWpee7LxFuAbdiOfJaC7twvJnLUqdS88/wAPxBqXnn+H4i2ANuxHPktB924XkzlqVGpeef4fiDUvPdn4i3ANtr8+S0Du3C8mctSo1Lz3ZeINSc92fiLcA2yvz5LQO7sNyZy1KfUnPdn4h6l57s/EW4C2yvzZLQO7sNyZy1KjUvPdn4h6l57s/EWwBtdbmyWgd3YbkzlqVOpee7PxBqXnuz8RbALa63NktB934bkzlqeev7LQ5n287Oz/ANXDDDN/yaZb5e9T8z6CoPQoTcqak+O/5PFxlONOtKMVZbvPyXmADA0uc6N249JPpz72QMlx58+nPvZjKuaWIsscj+t+X9RXsscj79X5f1GOKf8ARl7fKOnAr/kQ9/0ssjo/krs6NWjdurSp1XGrTSdSEamCwlvYo5wdP8kHoLv97S/KzzsN9RHd0w2sHNp+Xyjxm7ilGGVLuEIxhCLgoxilGK+7i9iXxKMv933+r3nSh/agef32lxmdT++Xq/k7cLvoUv8ArH9KJ0qcpSUYxk3J4RjGLlKT4kltZYVNzuUIxz5WVwo7+Lt6mxe1YYr4nUMj5NtsiWEq9VLSqmpXFRJaSVR4fcw9mOCS+LK/I3lBnd3KoKwqqnJpZ1LOrShFvDPaUfN4+L2m/YQjZVJWkzzH0lWq9eWGpdanHi3K17cbb1r9ldHLXxYbTerZGvKcHUna14U0sXUlQq00lxuTWCPf+U/IFN0Xf04qNSEoqvgsFUg3gpv9pNxWPE/Yj2F/KhG0lK5ipUIUlUqRazk0sHhhw7UtnCOOFfWlGTtYip00uzpVKcL9ZtNeKa6u5eDunu/Y4jb5Dvq0NJTtK9Sm1ipwo1JRkv2Xht+BpVKcotxlGUXF4SjJOMoviae1HULDyk2866pVLedGk5qCquopNbcE3HDe9zZm8p2SKdWzleJJVqDgnNLbOk55ua+PByTXx42S6MXByhK9jSHSNeNeFHEUep13u3347l9nvsnwt4nKFFtpJNtvBJbW3xIsv+ncoZud+hXWbv46Crjhx4YYnStx2Q6GTrKN5XS00qKr1Ksli6FLNxzI8Wzfw338DQXlPoabM/Raqo52Gl0qdTDjzMMPhnDVCEUnUlZsH0lWqTnHC0evGPFt2v6cNX5HNIUakpKChJ1HLNVOMXJuXJUVtx9hsXWS7qhHPrW1xShiln1KFSEMeBY7x1XdfkendW6yjauKuaEYXVKrFemhFZyx49ixXuw4WWVZQytkt4Yf+VQxhtxUK62rqzj/ACKWFd2r7+K+5k+m11IVOp+Fu0rvfF/vu4cODRw1G9VyLe04uc7S5hCKbnOdCtCMFwttrBIs9w2S3c5SownFqNCUq9SMltioSWEX/uzVh7WdC3c1ZVlbZMpPCpf1kqj5FuvtTk/ils4cGjKnS60HO/p6/wDp14rH9jiIUEk7q7d+C33fsk36HIrS0rV5ZtGlOtLkU4TnLDjwwNi7yLe28c+tbXFOG+6jozjFe94YI63f3tlkK0hGMGk3mwhHDS1qmG2U33v3JcCNHc7u8o39VW1ShK3nUxjBSmqsJyw817Fg2seDA02eCajKf4vQ5V0riKkXWpYe9JeN7Pdx3aXt58TkgHtPKPudp2k6d1QiqdGu5RqQjsjCrhjsXAsMXhwYM8Wc84OEnFnq4bEQxFKNWHB/z+aFTl71PzPoKgt8u+p+Z9JVHo4b6Uff5Z4mP/yJ+36UAAM1OY3bjz59OfezGzNcefPpz72YWFzSwiwyPv1Pl/UaBv5I9Z8v6jPEv+lL2+UdOC+vD3+GWR0/yQ+gu/3tL8rOYHsNwm6m3ybCvCtTrTdaUJxdJRaUUmtuMlxnBQko1E5OyO/pSlOrhZwpq8nb5Rh3a5KuquVLudO2rThKUMJxoVZRl91FbGtm+mUtPJN5Tkqjs6+FOSqSzqFSKSTxeLa2LYdH/wC51j/8Lrq0v+ZrZS8ollVoV6UaNzGVWhWpxco0sE3FpN4T3tprOFFuUuuclDEY6EYU3htySXHwSS/Y9dlCztr+2iqqdS3no6yUXJZ8d+Pm7cNvAeQvN29lZJ2+TbPOmpZmCp6Cln8bWGdJ+9L3nntyu7etYxVGrHT26bzVnYTpLhUXvNex/wAUesflHybjnqhcOphyKKfuzs827eM1dSUZeO485dHVaE3TnSdalfdaVl6tJN34eXujxG6zKWVqmar5VaNKqs+nRzZUqbjv4OPC1+021s3jpm7H/R7n9xD80TnG6zdjVyjHQxpxpW8ZKai3nznLbg3LDZ7l/Fl5l7d1aXVhWtqdKuqlSEYKU1TzE009uEseDiM4VILtPxXuvH3Oythq81hWqSj1ZNtRtZK8bfD8X93vOerfXSXedr3fr/1F10aH96BxTHan7UzoO6fdza3ljXtaVG4jOrGEVOUaeYvtpvHCWP6r4DKhOKjNN2utTsx9CpOvhZwjdRld/ZXjoz1t1T1hkhxoNY17SDprHBZ+Cag+Las1nGtW3Om0GhrabOzdHo552OPFh/Mu9ye7Ctk7Gk46a3lLOdNyzXCXDKD4Mfbsfs2t+1flMyfh6K6xw83Mp9+eaTlTrJOUuqzjoU8VgHOnTpdpTbut9reuV/zTLWMP0DI+bWaxoWTjPbitJm4Zi4/tNRR5vyT5UxhWsp78G7iljyXgpxXuea/9zPL7qt1tfKLUMFRtoyxjTjLOcpcEpvhfs3l7d8rdz+U5WV3RuUm1Tn95FPBzg1hKP8G/jgEsQu0i4/2rcOl0XN4SrGr9Sbv6Nb1+e+/hZnZMj5ChbXV9cLfuqqlFciGGdJfGcpv4I8NbZejX3SU6zadGMp21GWOKzc2UItdKTb+JYZX8o1tUt6tO3p141pwlCEpKmlFtYZ2yTexY8HEczWx7N9PHEdetFdVU+Cd8yejsBVl2s8SrSlHqrhe1rXyS9mdT8qeSa1xRt61GLqKg6ukhBOTSkl9rBbWk4YP3+88duJyVXr39tOFOejo1qdWpNxkowjFp4N8bw3vb7y+3P+UeVOEaV7TqVc1JKvTcXUkv2k2k37cUWl95TrSMHoLevUnhsVRQpxXvabf8gn2NSfXcrfYVHb8PReFVHrcUpX3Wd+P53V7W8U7B5WbuCtKFHH7ypcKpFcUVGSb/AIzSOWG5ljKta9rOvXljKWCSXmwhwQS4EsWaZz1qnaTcj1ej8K8NQjSbu/H1f8RU5d9V8z6CqLXLnqvmfSVZ3Yf6a9/lnlY7/In7fCAAGaHMb1x58+nLvZiM1x58+nLvMIkzbwIssMket+X9RoMz2l1os/7Odn4cOGG//kmsnKDS4/7NcNJQqxlJ7t/wy6ArNa83/X4Q1rzfaeE4tnqeWa1PV26hzZPQswKzW3N9p4Q1tzfaeENmq8ua1Ft2H5snoWYFVrfmu08Itcc12nhDZqvLmtQ2/D8+T0LYCp1xzPaeENc8z2nhHstXlzWotvw/PlLQtgKnXPM9p4Q1zzPaeENlrcua1DvDDc+UtC2Aqddcz2nhFrrme08IbLV5c1qHeGG58paFuBT665ntPCPXXM9p4RbNV5c1qHeGH58paFuBU655rtPCGueZ7Twhs1XlzWobfh+fKWhbAVOuea7Twi1zzXaeENnq8ua1Ht+H58noW4FTrjmu08Ia45rtPCLZ6nlmtQ27D82T0Hlz1XzPpKo3L6802b9jMzM/hxxxw9nsNQ7aMXGCT4/7PIxU41K0pxe52+EAwAsxN248+fTl3sxMyXHnz6cu9mIlGoMiSIsohiIskRZZLEIbEUiBMRIiO4hMQwKJEAMQCGAAAAAAIAAQCY7DJCAkoBoBokYDACSxjABDNq49JPpz72YyVx58+nPvZjEiwIsbENEsCIMCiSLAGBRJEAAZIERsRVxAIYguSMAAVwEMABsYAAxFAhiGIYIaBDJGAwRIRQABJIRSMlx6SfTn3sxk7j0k+nPvZiEAyLHiRZQgEwEwEAAIsgGAhMBDEADEIGMAABAAAgGAgACQhiGMEAITGNDBDRJY0NAhiGMYiQiwuH95Ppz72Yydx6Sp05/mZhxGQSIjxEMQCYCYyQAAGIQmMBiIjEACGAhDAYCGAAMQCAESQhgUNAAIgCQ0JEkBY0NCRJCKsMYIBMsx3D+8qdOf5mQMtxTlpKn2ZefP9V8bMWjlyZdVlGCYgxJaOXJl1WGjlyZdVjC5AB6OXJl1WLRy5Muqx2EIB6OXJl1WPRS5MuqwEQET0cuTLqsWilyZdVjEIRLRS5MuqwzJcmXVYBcgBPRS5MuqxaKXJl1WAXIkg0U+TLqseinyZdVgBEY9FLky6rHopcmXVYDIjRLRS5MuqxqlLky6rExkSSBUpcmXVY9FLky6rJGJE0CpS5MuqxqnLky6rApCRIkqcuTLqsejlyZdViKRFEkNU5cmXVZNU5cmXVZJZ//Z"
      />
      <img
        src="https://thumbs.dreamstime.com/b/samsung-korean-electronics-company-logo-48015862.jpg"
      />
      <img
        src="data:image/png;base64,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"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "SliderPage",
  data() {
    return {
      images: [
        "https://images.pexels.com/photos/6934240/pexels-photo-6934240.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://images.unsplash.com/photo-1603791239531-1dda55e194a6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8YXBwbGUlMjBpcGhvbmV8ZW58MHx8MHx8&w=1000&q=80",
        "https://www.apple.com/v/apple-watch-nike/ac/images/site/meta/gps-lte/og__d5tgtywxrcom.png",
        "https://images.pexels.com/photos/205421/pexels-photo-205421.jpeg?auto=compress&cs=tinysrgb&w=800",
        "https://telecomtalk.info/wp-content/uploads/2022/09/apple-airpods-pro-2-arrive-in-india.jpg",
      ],
      timer: null,
      currentIndex: 0,
    };
  },
  mounted: function () {
    this.startSlide();
  },

  methods: {
    startSlide: function () {
      this.timer = setInterval(this.next, 2000);
    },

    next: function () {
      this.currentIndex += 1;
    },
    prev: function () {
      this.currentIndex -= 1;
    },
  },

  computed: {
    currentImg: function () {
      return this.images[Math.abs(this.currentIndex) % this.images.length];
    },
  },
};
</script>
<style scoped>
#slider {
  /* margin-top: 4.1em; */
}
#productcards img {
  border-radius: 1.2em;
}
img {
  height: 80vh;
  width: 100vw;
}
#images img {
  width: 100vw;
  height: 90vh;
}
#productcards {
  display: flex;
  justify-content: space-around;
}
#productcards img {
  height: 35vh;
  width: 15vw;
}
#partners {
  display: flex;
  justify-content: space-around;
}
#partners img {
  height: 35vh;
  width: 15vw;
}
</style>
