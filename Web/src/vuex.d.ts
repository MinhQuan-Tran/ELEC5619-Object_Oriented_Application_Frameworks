import { Store } from 'vuex'

declare module '@vue/runtime-core' {
  // declare your own store states
  interface State {
    user: {
      user_id: string | null
      username: string | null
      email: string | null
      phone_number: string | null
      user_type: string | null
    }
  }

  // provide typings for `this.$store`
  interface ComponentCustomProperties {
    $store: Store<State>
  }
}

declare global {
  interface Window {
    $cookies: VueCookies
  }
}
