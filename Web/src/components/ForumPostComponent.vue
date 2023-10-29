<script lang="ts">
import ForumHeaderComponent from "@/components/ForumHeaderComponent.vue";
import { type ForumUser } from "@/types";

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
        id: {
            type: String,
            required: true
        },
        postId: {
            type: Number,
            required: true
        },
        content: {
            type: String,
            required: true
        }
    },
    data() {
        return {
            translatedContent: this.content,
        };
    },
    methods: {
        translate() {
            fetch(`https://665.uncovernet.workers.dev/translate?text=${this.content}&target_lang=en`, {
                // mode: "no-cors",
            })
                .then((response) => response.json())
                .then((data) => {
                    this.translatedContent = data.response.translated_text;
                })
                .catch((error) => {
                    console.error(error);
                    alert("Error translating post")
                });
        }
    },
    // mounted() {
    //     this.translatedContent = this.content;
    // },
    components: {
        ForumHeaderComponent
    }
};
</script>

<template>
    <div class="post">
        <ForumHeaderComponent :user="user" :date="date" :post-id="postId"></ForumHeaderComponent>
        <button @click="translate">Translate to English</button>
        <p class="content">
            {{ translatedContent }}
        </p>
        <router-link :to="`/forum/posts/${id}`" class="view-post">
            View post
        </router-link>
    </div>
</template>

<style scoped>
.post {
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

.view-post {
    text-align: center;
}
</style>