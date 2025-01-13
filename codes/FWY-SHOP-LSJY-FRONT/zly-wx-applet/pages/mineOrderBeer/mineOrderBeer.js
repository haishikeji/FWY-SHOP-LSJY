// pages/mineOrderBeer/mineOrderBeer.js
const request = require("../../utils/request.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tabStatus: 0,
    orderList: [],
    noDataStatus: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.geOrderList()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    let that = this;
    let lastBeerBoxOrderId = Number(that.data.orderList[that.data.orderList.length-1].beerBoxOrderId);
    request.getData(
      'app/box/order/list',{type: that.data.tabStatus, lastBeerBoxOrderId: lastBeerBoxOrderId},
      res => {
        if(res.data.data.length > 0) {
          let arr = res.data.data;
          that.setData({
            orderList: that.data.orderList.concat(arr)
          })
        }
      },
      res => {
        console.log(res)
      }
    )
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  geOrderList() {
    let that = this;
    request.getData(
      'app/box/order/list',{type: that.data.tabStatus},
      res => {
        that.setData({
          orderList: res.data.data,
          noDataStatus: res.data.data.length==0 ? true : false
        })
      },
      res => {
        console.log(res)
      }
    )
  },
  swicthTab(event) {
    let tab = event.currentTarget.dataset.tab;
    this.setData({
      tabStatus: tab
    })
    this.geOrderList();
  },
  toBeerBox() {
    wx.navigateTo({
      url: '../beerBox/beerBox'
    })
  },
  toProgress(event) {
    let infolist = event.currentTarget.dataset.infolist;
    wx.navigateTo({
      url: '../mineOrderProgress/mineOrderProgress',
      success: function(res) {
        res.eventChannel.emit('getInfoList', { infolist })
      }
    })
  },
})