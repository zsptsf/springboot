import request from '@/request'

export function getAllClbum() {
    return request({
        url: '/clbum',
        method: 'get',
    })
}

export function clbumpage(page,size) {
    return request({
        url: `clbum/findAll/${page}/${size}`,
        method: 'get'
    })
}

export function findByClbumNameLike(name) {
    const data = {
        name
    }
    return request({
        url: 'clbum/findByNameLike',
        method: 'post',
        data
    })
}

export function saveClbum(name) {
    const data = {
        name
    }
    return request({
        url: 'clbum/create',
        method: 'post',
        data
    })
}

export function update(id,name) {
    const data = {
        id,
        name
    }
    return request({
        url: 'clbum/update',
        method: 'Put',
        data
    })
}

export function deleteclbum(id) {
    return request({
        url: `clbum/delete/${id}`,
        method: 'get'
    })
}