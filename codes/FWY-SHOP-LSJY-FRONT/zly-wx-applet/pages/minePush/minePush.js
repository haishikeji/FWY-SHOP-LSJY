// pages/minePush/minePush.js
const request = require("../../utils/request.js");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    handleStatus: true,
    contentList: [],
    contentId: null,
  },
  deleteConFun() {
    let that = this;
    request.postData(
      'app/cont/content/del',
      {contentId: that.data.contentId},
      res => {
        that.getContentList();
        that.setData({
          contentId: null,
          handleStatus: true
        })        
        wx.showToast({
          title: '删除成功',
          icon: 'none',
          duration: 2000
        })
      }
    )
  },
  getContentList() {
    let that = this;
    request.getData(
      'app/cont/content/mine',
      {},
      res => {
        let arr = res.data.data;
        arr.forEach((item, index, array) => {
          let scale = request.getUrlKey(item.imgUrlList[0], 'scale');
          item.scale = scale ? scale : 1;
        })
        that.setData({
          contentList: arr
        })
      },
      res => {
        console.log(res)
      }
    )
  },
  handleShow(event) {
    // this.data.contentId = event.currentTarget.dataset.contentid;
    this.setData({
      contentId: event.currentTarget.dataset.cid,
      handleStatus: !this.data.handleStatus
    })
  },
  toCirclesDetail(event) {
    let contentId = event.currentTarget.dataset.cid;
    wx.navigateTo({
      url: '../circlesDetail/circlesDetail?contentId=' + contentId
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getContentList()
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
    let lastContentId = Number(that.data.contentList[that.data.contentList.length-1].contentId);
    request.getData(
      'app/cont/content/mine',
      {lastContentId: lastContentId},
      res => {
        if(res.data.data.length > 0) {
          let arr = res.data.data;
          arr.forEach((item, index, array) => {
            let scale = request.getUrlKey(item.imgUrlList[0], 'scale');
            item.scale = scale ? scale : 1;
          })
          that.setData({
            contentList: that.data.contentList.concat(arr)
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
    if(Object.from == 'button') {
      let title = Object.target.dataset.item.content
      let contentId = Object.target.dataset.item.contentId
      let imageUrl = Object.target.dataset.item.imgUrlList[0]
      return {
        title: title,
        imageUrl: imageUrl,
        path: '/pages/circlesDetail/circlesDetail?contentId=' + contentId
      }
    }
  }
})