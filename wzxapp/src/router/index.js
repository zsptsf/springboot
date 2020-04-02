import Vue from 'vue'
import Router from 'vue-router'
import {Message} from 'element-ui'
import Home from '@/Home'
import store from '@/store'

import {getToken} from '@/request/token'

Vue.use(Router)

const router = new Router({
    routes: [
        {
            path: '',
            name: 'Home',
            component: Home,
            children: [
                {
                    path: '/',
                    component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
                },
                {
                    path: '/sign',
                    component: r => require.ensure([], () => r(require('@/views/Sign')), 'sign'),
                    children: [
                        {
                            path: '/sign',
                            component: r => require.ensure([], () => r(require('@/views/SignChart')), 'signchart')
                        },
                        {
                            path: '/signtable',
                            component: r => require.ensure([], () => r(require('@/views/SignTable')), 'signtable')
                        }
                    ]
                }
            ]
        },
        {
            path: '/login',
            component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
        },
        {
            path: '/register',
            component: r => require.ensure([], () => r(require('@/views/Register')), 'register')
        },
        {
            path: '/userupdate',
            component: r => require.ensure([], () => r(require('@/views/UserUpdata')), 'userupdate')
        },
        {
            path: '/admin',
            name: 'Admin',
            component: r => require.ensure([], () => r(require('@/views/Admin')), 'admin'),
            children: [
                {
                    path: '/usermanage',
                    component: r => require.ensure([], () => r(require('@/views/User/UserManage')), 'usermanage')
                },
                {
                    path: '/clbummanage',
                    component: r => require.ensure([], () => r(require('@/views/Clbum/ClbumManage')), 'clbummanage')
                },
                {
                    path: '/signmanage',
                    component: r => require.ensure([], () => r(require('@/views/Sign/SignManage')), 'signmanage')
                },
                {
                    path: '/rolemanage',
                    component: r => require.ensure([], () => r(require('@/views/Role/RoleManage')), 'rolemanage')
                }
            ]
        }
    ]
})


router.beforeEach((to, from, next) => {

    if (getToken()) {

        if (to.path === '/login') {
            next({path: '/'})
        } else {
            if (store.state.number.length === 0) {
                store.dispatch('getUserInfo').then(data => { //获取用户信息
                    next()
                }).catch(() => {
                    next({path: '/'})
                })
            } else {
                next()
            }
        }
    } else {
        if (to.matched.some(r => r.meta.requireLogin)) {
            Message({
                type: 'warning',
                showClose: true,
                message: '请先登录哦'
            })

        }
        else {
            next();
        }
    }
})

export default router
