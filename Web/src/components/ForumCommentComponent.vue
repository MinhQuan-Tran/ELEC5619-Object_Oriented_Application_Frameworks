<script lang="ts">
import ForumHeaderComponent from "@/components/ForumHeaderComponent.vue";
import type { Comment } from "@/types";
import type { PropType } from "vue";

export default {
    name: "ForumCommentComponent",
    props: {
        comment: {
            type: Object as PropType<Comment>,
            required: true
        },
        postId: {
            type: Number,
            required: true
        }
    },
    methods: {
        submitReply(event: Event) {
            event.preventDefault();

            if (!this.$store.state.user.user_id || !this.$cookies.get("auth_token")) {
                alert("You must be logged in to reply to a comment.");
                return;
            }

            fetch(`${import.meta.env.VITE_ROOT_API}/posts/${this.$route.params.postId}/comments`, {
                method: "POST",
                headers: {
                    "Authorization": this.$cookies.get("auth_token")
                },
                body: new FormData(this.$refs.replyForm as HTMLFormElement)
            })
                .then(response => {
                    if (!response.ok && response.status !== 201) {
                        return response.json().then(data => {
                            throw new Error(data.message);
                        });
                    }
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
    mounted() {
        console.log(this.comment);
    },
    components: {
        ForumHeaderComponent
    }
}
</script>

<template>
    <div class="comment">
        <ForumHeaderComponent :user="comment.user" :date="comment.date" :comment-id="comment.id"></ForumHeaderComponent>
        <p class="content">
            <slot></slot>
        </p>
        <details>
            <summary>Reply</summary>
            <form class="write-reply" ref="replyForm" @submit="submitReply">
                <input type="hidden" name="pid" :value="postId">
                <input type="hidden" name="uid" :value="$store.state.user.user_id">
                <input type="hidden" name="parentCommentId" :value="comment.id">
                <textarea name="context" id="comment" rows="1" placeholder="Write a reply..."></textarea>
                <button type="submit">Reply</button>
            </form>
        </details>
        <ForumCommentComponent class="replies" v-for="reply in comment.replies" :key="reply.id.toString()" :comment="reply"
            :post-id="postId">
            {{ reply.content }}
        </ForumCommentComponent>
    </div>
</template>

<style scoped>
.comment {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: stretch;
}

.content {
    margin: 0;
}

details summary {
    background-color: rgba(var(--primary-color), 1);
    width: fit-content;
    padding: 0 0.4rem;
    border-radius: 4px;
    font-size: 0.9rem;
    cursor: pointer;
    color: white;
}

.write-reply {
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    gap: 0.5rem;
}

.write-reply textarea {
    flex: 1;
    padding: 0.2rem;
    border-radius: 4px;
    resize: vertical;
}

.replies {
    margin-left: 2rem;
    margin-top: 0.5rem;
}
</style>