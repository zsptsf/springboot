import request from '@/request'

export function login(number, password) {
  const data = {
    number,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
//获取当前登录用户
export function getUserInfo() {
  return request({
    url: '/user/currentUser',
    method: 'get'
  })
}
//注册
export function register(number,name,password) {
  const data = {
    number,
    name,
    password
  }
  return request({
    url: '/register',
    method: 'post',
    data
  })
}
