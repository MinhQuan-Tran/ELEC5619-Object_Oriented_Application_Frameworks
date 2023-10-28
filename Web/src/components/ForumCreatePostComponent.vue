<script lang="ts">
export default {
    methods: {
        submitPost(event: Event) {
            event.preventDefault();

            if (!this.$store.state.user.user_id || !this.$cookies.get("auth_token")) {
                alert("You must be logged in to create a post.");
                return;
            }

            fetch(`${import.meta.env.VITE_ROOT_API}/posts`, {
                method: "POST",
                headers: {
                    "Authorization": this.$cookies.get("auth_token")
                },
                body: new FormData(this.$refs.createPostForm as HTMLFormElement)
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
    }
}
</script>

<template>
    <div class="create-post">
        <form ref="createPostForm" @submit="submitPost">
            <input type="hidden" name="uid" :value="$store.state.user.user_id">
            <textarea name="content" rows="4" placeholder="What's on your mind?"></textarea>
            <button type="submit">Post</button>
        </form>
    </div>
</template>

<style scoped>
.create-post {
    margin: 0 auto;
    width: 100%;
    max-width: 600px;
    padding: 1rem;
}

.create-post form {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    justify-content: flex-start;
}
</style>