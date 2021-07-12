import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import Base64 from './assets/js/base64.js'
const app = createApp(App)
installElementPlus(app)
app.config.globalProperties.$Base64 = Base64;
app
    .use(store)
    .use(router)
    .mount('#app')