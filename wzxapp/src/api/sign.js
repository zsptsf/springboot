import request from '@/request'

export function saveSign(usernumber, remark) {
    const data = {
        usernumber,
        remark
    }
    return request({
        url: 'sign/create',
        method: 'post',
        data
    })
}

export function findSign(id) {
    return request({
        url: `sign/find/${id}`,
        method: 'get'
    })
}

export function signpage(page,size) {
    return request({
        url: `sign/findAll/${page}/${size}`,
        method: 'get'
    })
}

export function getAllSign() {
    return request({
        url: '/sign',
        method: 'get',
    })
}