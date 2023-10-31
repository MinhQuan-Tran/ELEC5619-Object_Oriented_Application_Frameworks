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
        },
        supportedLanguages: {
            type: Array as () => {
                display_name: string;
                language_code: string;
            }[],
            required: true
        }
    },
    data() {
        return {
            translatedContent: "",
        };
    },
    methods: {
        translate(event: Event) {
            let data = JSON.stringify({
                "texts": [this.content],
                "to": [(event.target as HTMLOptionElement).value || "en"]
            });

            fetch("https://lecto-translation.p.rapidapi.com/v1/translate/text", {
                method: "POST",
                headers: {
                    'content-type': 'application/json',
                    'X-RapidAPI-Key': 'bdb5168ac2msh71ec36747bb9e8fp18dfc8jsnf259043f5ed3',
                    'X-RapidAPI-Host': 'lecto-translation.p.rapidapi.com'
                },
                body: data,
            })
                .then((response) => response.json())
                .then((data) => {
                    console.log(data.translations[0]);
                    this.translatedContent = data.translations[0].translated[0];
                })
                .catch((error) => {
                    console.error(error);
                    alert("Error translating post")
                });
        }
    },
    components: {
        ForumHeaderComponent
    }
};
</script>

<template>
    <div class="post">
        <ForumHeaderComponent :user="user" :date="date" :post-id="postId"></ForumHeaderComponent>
        <p class="content">
            {{ content }}
        </p>
        <span>
            Translate to:
            <select ref="languageOptions" @change="translate">
                <option value="" selected disabled>Language</option>
                <option v-for="language in supportedLanguages" :key="language.language_code"
                    :value="language.language_code">{{
                        language.display_name }}</option>
            </select>
        </span>
        <p class="translated-content">
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
    background-color: rgb(237, 237, 237);
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
    background-color: rgba(79, 79, 79, 0.5);
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