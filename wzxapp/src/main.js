import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import JsonExcel from 'vue-json-excel'

//vue导出Excel表单
Vue.component('downloadExcel', JsonExcel)


//vue修改浏览器的标题title
Vue.directive('title',  function (el, binding) {
    document.title = el.dataset.title
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
