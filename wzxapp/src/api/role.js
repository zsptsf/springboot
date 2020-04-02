import request from '@/request'

export function rolepage(page,size) {
    return request({
        url: `role/findAll/${page}/${size}`,
        method: 'get'
    })
}

export function saveRole(name) {
    const data = {
        name
    }
    return request({
        url: 'role/create',
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
        url: 'role/update',
        method: 'Put',
        data
    })
}

export function deleterole(id) {
    return request({
        url: `role/delete/${id}`,
        method: 'get'
    })
}