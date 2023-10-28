export interface ForumUser {
  id: number
  username: string
  profileImageURL: string
}

export interface Comment {
  id: number
  replies: Comment[]
  date: Date
  user: ForumUser
  content: string
}
