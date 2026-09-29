import axios from "axios"

const base_url = "http://localhost:8091/api/records"

export function addRecord(record) {
    return axios.post(base_url, record)
}
