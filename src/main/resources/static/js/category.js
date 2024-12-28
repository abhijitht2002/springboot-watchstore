var authorSwiper = new Swiper(".category-content", {
      slidesPerView: 5,
      spaceBetween: 0,
      // slidesPerGroup: 2,
      loop: true,
      // loop: false,
      // loopFillGroupWithBlank: true,
      centerSlide: 'true',
      fade: 'true',
      grabCursor: 'true',
      // pagination: {
      //   el: ".swiper-pagination",
      //   clickable: true,
      // },
      navigation: {
        nextEl: ".author-next",
        prevEl: ".author-prev",
      },
      autoplay: {
                    delay: 1500,
                    disableOnInteraction: false,
                    pauseOnMouseEnter: true,
                },

      breakpoints:{
        0:{
          slidesPerView: 1,
        },
        480:{
          slidesPerView: 1,
        },
        768:{
          slidesPerView: 2,
        },
        1024:{
          slidesPerView: 4,
        },
        1280:{
          slidesPerView: 5,
        },
      },

    });

