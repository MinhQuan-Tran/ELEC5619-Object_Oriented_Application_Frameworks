<script lang="ts">
import ForumCreatePostComponent from "@/components/ForumCreatePostComponent.vue";
import ForumPostComponent from "@/components/ForumPostComponent.vue";
import { type ForumUser } from "@/types";

export default {
    data() {
        return {
            posts: [] as {
                id: Number;
                date: Date,
                user: ForumUser,
                content: string,
            }[]
        };
    },
    methods: {
        getPosts() {
            fetch(`${import.meta.env.VITE_ROOT_API}/posts`)
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

                    this.posts = data.map((post: {
                        id: Number;
                        date: string,
                        user: ForumUser,
                        content: string,
                    }) => {
                        return {
                            id: post.id,
                            date: new Date(post.date),
                            user: post.user,
                            content: post.content,
                        };
                    });
                })
                .catch(error => {
                    console.error(error);
                });
        },
    },
    created() {
        this.getPosts();
    },
    components: {
        ForumPostComponent,
        ForumCreatePostComponent
    },
};
</script>

<template>
    <div class="forum">
        <div class="create-post">
            <ForumCreatePostComponent></ForumCreatePostComponent>
        </div>
        <div class="posts">
            <ForumPostComponent v-for="post in posts" :key="post.id.toString()" :id="post.id.toString()" :user="post.user"
                :date="post.date" :post-id="post.id.valueOf()">
                {{ post.content }}
            </ForumPostComponent>
        </div>
    </div>
</template>

<style scoped>
.forum {
    padding: 1em;
    overflow-x: auto;
}

.posts {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
    gap: 1rem;
    width: 600px;
    margin: auto;
}
</style>
