import request from '@/request'

export function userupdate(id, clbum, number, password, avatar, name, role) {
    const data = {
        id,
        clbum,
        number,
        password,
        avatar,
        name,
        role
    }
    return request({
        url: 'user/update',
        method: 'post',
        data
    })
}

export function about(id) {
    return request({
        url: `user/${id}`,
        method: 'get'
    })
}

export function findpage(page,size) {
    return request({
        url: `user/findAll/${page}/${size}`,
        method: 'get'
    })
}

export function getAllUser() {
    return request({
        url: '/user',
        method: 'get',
    })
}

export function findByUserNameLike(name) {
    const data = {
        name
    }
    return request({
        url: 'user/findByNameLike',
        method: 'post',
        data
    })
}

export function saveUser(clbum, number, password, avatar, name, role) {
    const data = {
        clbum,
        number,
        password,
        avatar,
        name,
        role
    }
    return request({
        url: 'user/create',
        method: 'post',
        data
    })
}

export function deleteuser(id) {
    return request({
        url: `user/delete/${id}`,
        method: 'get'
    })
}