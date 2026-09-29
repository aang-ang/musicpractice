import axios from "axios"

const base_url = "http://localhost:8091/api/records"

export function addRecord(record) {
    return axios.post(base_url, record)
}

export function getRecordsBySongId(songId) {
    return axios.get(`${base_url}/song/${songId}`)
}

export function deleteRecord(id) {
    return axios.delete(`${base_url}/${id}`)
}
