import axios from 'axios';

const base_url = 'http://localhost:8091/api/goals'

export function getGoalsSongId(songId) {
    return axios.get(`${base_url}/song/${songId}`);
}
export function addGoal(goal) {
    return axios.post(base_url, goal);
}
export function updateGoal(id, goal) {
    return axios.put(`${base_url}/${id}`, goal);
}
export function deleteGoal(id) {
    return axios.delete(`${base_url}/${id}`);
}
export function getAchieve(goalId) {
    return axios.get(`${base_url}/${goalId}/achieve`);
}