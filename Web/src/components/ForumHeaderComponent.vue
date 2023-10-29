<script lang="ts">
import { type ForumUser } from '@/types';

export default {
    props: {
        user: {
            type: Object as () => ForumUser,
            required: true
        },
        date: {
            type: Date,
            required: true
        },
        postId: {
            type: Number,
            required: false
        },
        commentId: {
            type: Number,
            required: false
        }
    },
    methods: {
        deletePostComment() {
            console.log(this.postId, this.commentId);

            if (this.postId && !this.commentId) {
                this.deletePost();
            } else if (this.commentId && !this.postId) {
                this.deleteComment();
            } else {
                alert("Something went wrong.");
            }
        },
        deletePost() {
            if (!this.$cookies.isKey("auth_token") || !this.$store.state.user.user_id) {
                alert("You must be logged in to delete a post.");
                return;
            }

            if (!confirm("Are you sure you want to delete this post?")) {
                return;
            }

            fetch(`${import.meta.env.VITE_ROOT_API}/posts/${this.postId}`, {
                method: "DELETE",
                headers: {
                    "Authorization": this.$cookies.get("auth_token")
                }
            })
                .then(response => {
                    if (!response.ok && response.status !== 204) {
                        return response.json().then(data => {
                            throw new Error(data.message);
                        });
                    }
                    // return response.json();
                })
                .then(data => {
                    console.log(data);

                    this.$router.go(0);
                })
                .catch(error => {
                    console.error(error);
                });
        },
        deleteComment() {
            if (!this.$cookies.isKey("auth_token") || !this.$store.state.user.user_id) {
                alert("You must be logged in to delete a comment.");
                return;
            }

            if (!confirm("Are you sure you want to delete this comment?")) {
                return;
            }

            fetch(`${import.meta.env.VITE_ROOT_API}/comments/${this.commentId}`, {
                method: "DELETE",
                headers: {
                    "Authorization": this.$cookies.get("auth_token")
                }
            })
                .then(response => {
                    if (!response.ok && response.status !== 204) {
                        return response.json().then(data => {
                            throw new Error(data.message);
                        });
                    }
                    // return response.json();
                })
                .then(data => {
                    console.log(data);

                    this.$router.go(0);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    },
    computed: {
        timeDiff() {
            const diff = new Date().getTime() - new Date(this.date).getTime();;

            if (diff < 1000) {
                return "Just now";
            } else if (diff < 60000) {
                return `${Math.floor(diff / 1000)} seconds ago`;
            } else if (diff < 3600000) {
                return `${Math.floor(diff / 60000)} minutes ago`;
            } else if (diff < 86400000) {
                return `${Math.floor(diff / 3600000)} hours ago`;
            } else if (diff < 604800000) {
                return `${Math.floor(diff / 86400000)} days ago`;
            } else if (diff < 2629800000) {
                return `${Math.floor(diff / 604800000)} weeks ago`;
            } else if (diff < 31557600000) {
                return `${Math.floor(diff / 2629800000)} months ago`;
            } else {
                return `${Math.floor(diff / 31557600000)} years ago`;
            }
        }
    },
}
</script>

<template>
    <div class="header">
        <img class="profile-image" :src="user.profileImageURL" alt="Profile Image" width="50" height="50">
        <div class="user-info">
            <h3 class="username">{{ user.username }}</h3>
            <p class="date">{{ timeDiff }}</p>
        </div>
        <div class="delete-section">
            <button @click="deletePostComment" v-if="user.id == $store.state.user.user_id">X</button>
        </div>
    </div>
</template>

<style scoped>
.header {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    gap: 0.5rem;
}

.profile-image {
    border-radius: 50%;
}

.user-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
}

.user-info * {
    margin: 0;
}

.date {
    font-size: 0.8rem;
    color: rgba(0, 0, 0, 0.8);
}

.delete-section {
    flex: 1;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-end;
}
</style>