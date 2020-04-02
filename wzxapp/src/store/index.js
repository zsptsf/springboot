import Vue from 'vue'
import Vuex from 'vuex'
import {getToken, setToken, removeToken} from '@/request/token'
import {login, getUserInfo, logout, register} from '@/api/login'
import {userupdate} from '@/api/user'
import {saveSign} from '@/api/sign'

Vue.use(Vuex);//在创建Vue实例之前

export default new Vuex.Store({
    state: {
        //存放组件之间共享的数据
        id: '',
        number: '',
        name: '',
        avatar: '',
        clbum:'',
        password:'',
        role:'',
        token: getToken(),
    },
    mutations: {
        //显式的更改state里的数据
        //保存信息
        SET_TOKEN: (state, token) => {
            state.token = token;
        },
        SET_NUMBER: (state, number) => {
            state.number = number
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_CLBUM: (state, clbum) => {
            state.clbum = clbum
        },
        SET_PASSWORD: (state, password) => {
            state.password = password
        },
        SET_ROLE: (state, role) => {
            state.role = role
        },
        SET_ID: (state, id) => {
            state.id = id
        }
    },
    actions: {
        login({commit}, user) {
            return new Promise((resolve, reject) => {
                login(user.number, user.password).then(data => {
                    commit('SET_TOKEN', data.data['Oauth-Token'])
                    setToken(data.data['Oauth-Token'])
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取用户信息
        getUserInfo({commit, state}) {
            let that = this
            return new Promise((resolve, reject) => {
                getUserInfo().then(data => {
                    if (data.data) {
                        commit('SET_NUMBER', data.data.number)
                        commit('SET_NAME', data.data.name)
                        commit('SET_AVATAR', data.data.avatar)
                        commit('SET_ID', data.data.id)
                        commit('SET_CLBUM', data.data.clbum)
                        commit('SET_PASSWORD', data.data.password)
                        commit('SET_ROLE', data.data.role)
                    } else {
                        commit('SET_NUMBER', '')
                        commit('SET_NAME', '')
                        commit('SET_AVATAR', '')
                        commit('SET_ID', '')
                        commit('SET_CLBUM', '')
                        commit('SET_PASSWORD', '')
                        commit('SET_ROLE', '')
                        removeToken()
                    }
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 修改用户信息
        userupdate({commit}, user) {
            let that = this
            return new Promise((resolve, reject) => {
                userupdate(user.id, user.clbum,user.number, user.password, user.avatar, user.name, user.role).then(data => {
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 退出
        logout({commit, state}) {
            return new Promise((resolve, reject) => {
                logout().then(data => {

                    commit('SET_TOKEN', '')
                    commit('SET_NUMBER', '')
                    commit('SET_NAME', '')
                    commit('SET_AVATAR', '')
                    commit('SET_ID', '')
                    commit('SET_CLBUM', '')
                    commit('SET_PASSWORD', '')
                    commit('SET_ROLE', '')
                    removeToken()
                    resolve()

                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 前端 登出
        fedLogOut({commit}) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                commit('SET_NUMBER', '')
                commit('SET_NAME', '')
                commit('SET_AVATAR', '')
                commit('SET_ID', '')
                commit('SET_CLBUM', '')
                commit('SET_PASSWORD', '')
                commit('SET_ROLE', '')
                removeToken()
                resolve()
            }).catch(error => {
                reject(error)
            })
        },
        //注册
        register({commit}, user) {
            return new Promise((resolve, reject) => {
                register(user.number, user.name, user.password).then((data) => {
                    commit('SET_TOKEN', data.data['Oauth-Token'])
                    setToken(data.data['Oauth-Token'])
                    resolve()
                }).catch((error) => {
                    reject(error)
                })
            })
        },
        // 签到打卡
        saveSign({commit}, page) {
            let that = this
            return new Promise((resolve, reject) => {
                saveSign(page.usernumber, page.remark).then(data => {
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },
    },
    modules: {
    }
})
