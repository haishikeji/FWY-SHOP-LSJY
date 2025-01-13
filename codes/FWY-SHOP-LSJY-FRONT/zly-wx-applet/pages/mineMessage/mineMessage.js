// pages/mineMessage/mineMessage.js
const request = require("../../utils/request.js");

Page({

  /**
   * 页面的初始数据
   */
  data: {
    info: {}
  },
  getMineMessage() {
    let that = this;
    request.getData(
      'app/message/mine',
      {},
      res => {
        that.setData({
          info: res.data.data
        })
      },
      res => {
        console.log(res)
      }
    )
  },
  toDetail() {
    wx.navigateTo({
      url: '../messageDetail/messageDetail'
    })
  },
  toMineLike() {
    wx.navigateTo({
      url: '../mineLike/mineLike'
    })
  },
  toMineMoment() {
    wx.navigateTo({
      url: '../mineComment/mineComment'
    })  
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getMineMessage()
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})