<template>
  <div class="box">
    <!-- <img class="box-bg" src="@/assets/weichat_bg.jpeg" alt=""> -->
    <div class="login-container">
      <div class="login-left">
        <img src="@/assets/logo.jpg" alt="" class="img">
        <p class="title">品讯运营管理后台</p>
        <p>v 1.0.0</p>
      </div>
      <div class="login-right">
        <div class="title-container">
          <h3 class="title">
            品讯运营登录
            <!-- {{ $t('login.title') }} -->
          </h3>
        </div>
        <div>
          <el-form
            ref="loginForm"
            :model="loginForm"
            :rules="loginRules"
            class="login-form"
            label-position="left"
          >
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                :placeholder="$t('login.username')"
                name="username"
                type="text"
                autocomplete="off"
              >
                <i slot="prefix" class="">
                  <svg-icon icon-class="user1" />
                </i>
              </el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                :placeholder="$t('login.password')"
                name="password"
                type="password"
              >
                <i slot="prefix" class="">
                  <svg-icon icon-class="密码" />
                </i>
              </el-input>
            </el-form-item>
            <!-- <el-form-item>
              <el-col :span="12" style="overflow:hidden">
                <el-form-item prop="captcha">
                  <el-input
                    v-model="loginForm.code"
                    type="test"
                    auto-complete="off"
                    placeholder="验证码, 单击图片刷新"
                    style="width: 100%;"
                    @keyup.enter.native="handleLogin"
                  />
                </el-form-item>
              </el-col>
              <el-col class="line" :span="1">&nbsp;</el-col>
              <el-col :span="11">
                <el-form-item>
                  <img
                    style="width: 85%;height: 35px;float: right;"
                    class="pointer"
                    :src="src"
                    @click="refreshCaptcha"
                  >
                </el-form-item>
              </el-col>
            </el-form-item> -->

            <el-button
              :loading="loading"
              type="primary"
              style="width:100%;"
              @click.native.prevent="handleLogin"
            >
              {{ $t('login.logIn') }}
            </el-button>
          </el-form>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('密码至少6位'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'super',
        password: '',
        code: '',
        token: '',
        t: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur' }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      passwordType: 'password',
      loading: false,
      showDialog: false,
      redirect: undefined,
      token: '',
      activeName: 'loginForm',
      codeLoading: false,
      time: 60,
      currentPath: '',
      active: '',
      src: ''
    }
  },
  // watch: {
  //   $route: {
  //     handler: function(route) {
  //       this.redirect = route.query && route.query.redirect
  //     },
  //     immediate: true,
  //
  //   }
  // },
  created() {
    // this.refreshCaptcha()
  },
  mounted() {
    // 自动加载indexs方法

  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    // 用户名 密码登录
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('LoginByUsername', this.loginForm).then(() => {
            this.loading = false
            this.$router.replace({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
            // this.refreshCaptcha()
          })
        } else {
          console.log('登录未能成功!!')
          return false
        }
      })
    },
    // refreshCaptcha: function() {
    //   this.loginForm.code = ''
    //   this.loginForm.t = new Date().getTime()

    //   // TODO 地址配置
    //  this.src = 'http://**********/zly/captcha?t=' + this.loginForm.t
    //   //this.src = process.env.BASE_API + '/captcha?t=' + this.loginForm.t
    // }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .box{
    position: relative;
    width: 100%;
    height: 100%;
  }
  .box-bg{
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
  }
  .login-container {
    z-index: 999;
    width: 1000px;
    display: flex;
    border-radius: 5px;
    overflow: hidden;
    background: #fff;
    box-shadow: 0 0 25px #cac6c6;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 40px 0;

    .login-left {
      width: 50%;
      /*background: #1F79D6;*/
      text-align: center;
      padding-top: 90px;
      border-right: 1px solid #00000055;

      img {
        width: 140px;
      }
    }

    .login-right {
      width: 50%;
      padding: 35px;

      .title {
        margin: 0 auto 30px auto;
        text-align: center;
        color: #505458;
      }

      .remember {
        margin: 0 0 35px 0;
      }

      .el-form-item {
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 5px;
        color: #454545;
      }
    }

  }
</style>
