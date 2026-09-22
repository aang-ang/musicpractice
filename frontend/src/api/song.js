import axios from 'axios'

const base_url = 'http://localhost:8091/api/songs'

export function getSongs() {
    return axios.get(base_url)
}
export function addSong(song) {
    return axios.post(base_url, song)
}
export function updateSong(id, song) {
    return axios.put(`${base_url}/${id}`, song)
}
export function deleteSong(id) {
    return axios.delete(`${base_url}/${id}`)
}