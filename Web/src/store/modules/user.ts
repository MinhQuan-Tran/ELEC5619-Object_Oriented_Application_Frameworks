// initial state
const state = () => ({
  user_id: null,
  username: null,
  email: null,
  phone_number: null,
  user_type: null
})

// getters
const getters = {}

// actions
const actions = {}

// mutations
const mutations = {
  setUser(state: any, user: any) {
    state.user_id = user.uid
    state.username = user.username
    state.email = user.email
    state.phone_number = user.phone_number
    state.user_type = user.user_type
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
