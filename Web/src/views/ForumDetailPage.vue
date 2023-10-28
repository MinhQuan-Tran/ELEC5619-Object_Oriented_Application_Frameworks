<script lang="ts">
import ForumCommentComponent from "@/components/ForumCommentComponent.vue";
import ForumHeaderComponent from "@/components/ForumHeaderComponent.vue";
import { type Comment, type ForumUser } from "@/types";

export default {
    data() {
        return {
            id: Number(this.$route.params.postId),
            user: {} as ForumUser,
            comments: [] as Comment[],
            content: String,
            date: {} as Date
        }
    },
    methods: {
        getDetailPost() {
            fetch(`${import.meta.env.VITE_ROOT_API}/posts/${this.$route.params.postId}`)
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(data => {
                            throw new Error(data.message);
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data);

                    this.id = data.id;
                    this.user = data.user;
                    this.comments = data.comments.map((comment: Comment) => {
                        return {
                            id: comment.id,
                            date: new Date(comment.date),
                            user: comment.user,
                            content: comment.content,
                        };
                    })
                    this.content = data.content;
                    this.date = new Date(data.date);
                })
                .catch(error => {
                    console.error(error);

                    alert("No post found.");

                    this.$router.push({ name: "Forum" });
                });
        },
        submitComment(event: Event) {
            event.preventDefault();

            fetch(`${import.meta.env.VITE_ROOT_API}/posts/${this.$route.params.postId}/comments`, {
                method: "POST",
                headers: {
                    "Authorization": this.$cookies.get("auth_token")
                },
                body: new FormData(this.$refs.commentForm as HTMLFormElement)
            })
                .then(response => {
                    if (!response.ok && response.status !== 201) {
                        return response.json().then(data => {
                            throw new Error(data.message);
                        });
                    }
                    console.log("here")
                    return response.json();
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
    created() {
        this.getDetailPost();
    },
    components: {
        ForumCommentComponent,
        ForumHeaderComponent
    }
};
</script>

<template>
    <main>
        <div class="post">
            <ForumHeaderComponent :user="user" :date="date" :post-id="id"></ForumHeaderComponent>
            <p class="content">
                {{ content }}
            </p>
            <hr>
            <form class="write-comment" ref="commentForm" @submit="submitComment">
                <input type="hidden" name="pid" :value="id">
                <input type="hidden" name="uid" :value="$store.state.user.user_id">
                <textarea name="context" id="comment" rows="1" placeholder="Write a comment..."></textarea>
                <button type="submit">Comment</button>
            </form>
            <div class="comments">
                <ForumCommentComponent v-for="comment in comments" :key="comment.id" :comment="comment" :post-id="id">
                    {{ comment.content }}
                </ForumCommentComponent>
            </div>
        </div>
    </main>
</template>

<style scoped>
main {
    overflow: auto;
}

.post {
    margin: auto;
    margin-top: 1rem;
    width: 80%;
    min-height: 500px;
    height: auto;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
    text-align: left;
    background-color: rgb(52, 52, 52);
    box-sizing: border-box;
    padding: 0.5rem;
    border-radius: 8px;
    gap: 1rem;
    overflow: visible;
}

.content {
    margin: 0;
}

hr {
    width: 100%;
    height: 1px;
    border: 0;
    margin: 0;
    background-color: rgba(255, 255, 255, 0.5);
}

.write-comment {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: 0.5rem;
}

.write-comment textarea {
    flex: 1;
    padding: 0.2rem;
    border-radius: 4px;
    resize: vertical;
}

.comments {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
    gap: 1rem;
}
</style>