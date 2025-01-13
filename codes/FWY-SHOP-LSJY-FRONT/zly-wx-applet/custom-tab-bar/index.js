Component({
  data: {
    selected: 0,
    unreadMsgNum: 0,
    color: "rgba(0, 0, 0, 0.4)",
    selectedColor: "#FF5700",
    list: [
      {
        pagePath: "/pages/index/index",
        text: "首页",
        iconPath: "/static/tab/home_n.svg",
        selectedIconPath: "/static/tab/home_s.svg"
      },
      {
        pagePath: "/pages/beerWall/beerWall",
        text: "精酿",
        iconPath: "/static/tab/beer_n.svg",
        selectedIconPath: "/static/tab/beer_s.svg"
      },
      {
        pagePath: "/pages/circles/circles",
        text: "广场",
        iconPath: "/static/tab/qz_n.svg",
        selectedIconPath: "/static/tab/qz_s.svg"
      },
      {
        pagePath: "/pages/mine/mine",
        text: "我的",
        iconPath: "/static/tab/my_n.svg",
        selectedIconPath: "/static/tab/my_s.svg"
      },
    ]
  },
  attached() {
  },
  methods: {
    switchTab(e) {
      const data = e.currentTarget.dataset
      const url = data.path
      wx.switchTab({ url })
      this.setData({
        selected: data.index
      })
    }
  }
})