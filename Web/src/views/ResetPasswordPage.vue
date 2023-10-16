<script lang="ts">
export default {
    data() {
        return {
            formData: {
                password: '',
                confirmPassword: '',
                resetPasswordToken: this.$route.params.resetPasswordToken
            }
        };
    },
    methods: {
        submitResetPassword(event: Event) {
            event.preventDefault();

            (this.$refs.submitBtn as HTMLButtonElement).setAttribute("data-status", "processing");
            (this.$refs.submitBtn as HTMLButtonElement).disabled = true;
            (this.$refs.submitBtn as HTMLButtonElement).innerText = "Processing...";

            fetch(`${import.meta.env.VITE_ROOT_API}/reset-password`, {
                method: "POST",
                body: new FormData(this.$refs.resetPasswordForm as HTMLFormElement)
            })
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

                    (this.$refs.submitBtn as HTMLButtonElement).setAttribute("data-status", "success");
                    (this.$refs.submitBtn as HTMLButtonElement).innerText = data.message;

                    setTimeout(() => this.$router.push({ name: "Login" }), 2000);
                })
                .catch(error => {
                    console.error(error);

                    (this.$refs.submitBtn as HTMLButtonElement).setAttribute("data-status", "error");
                    (this.$refs.submitBtn as HTMLButtonElement).innerText = error + "\n\nClick here to retry";
                    (this.$refs.submitBtn as HTMLButtonElement).disabled = false;
                });
        }
    },
    mounted() {
        const formData = new FormData();
        formData.append('resetPasswordToken', (this.$route.params.resetPasswordToken as string));

        fetch(`${import.meta.env.VITE_ROOT_API}/validate-reset-password-token`, {
            method: "POST",
            body: formData
        })
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

                (this.$refs.username as HTMLSpanElement).innerText = data.data.name;
                (this.$refs.email as HTMLInputElement).value = data.data.email;
                (this.$refs.loadingText as HTMLParagraphElement).classList.add("hidden");
                (this.$refs.resetPasswordForm as HTMLFormElement).classList.remove("hidden");
            })
            .catch(error => {
                console.error(error);

                alert(error + "\n\nClick OK to go back to login page");

                this.$router.push({ name: "Login" });
            });
    },
};
</script>

<template>
    <main>
        <h1>Reset Password</h1>
        <p ref="loadingText">Loading...</p>
        <form ref="resetPasswordForm" class="reset-password-form hidden" @submit.prevent="submitResetPassword">
            <p>
                Hello, <span ref="username"></span>
            </p>
            <input ref="email" type="hidden" name="email" />
            <input type="hidden" name="resetPasswordToken" :value="formData.resetPasswordToken" />
            <div class="input-box">
                <input type="password" name="password" id="password" v-model="formData.password" placeholder="" required />
                <label for="password">New Password</label>
            </div>
            <div class="input-box">
                <input type="password" name="confirmPassword" id="confirmPassword" v-model="formData.confirmPassword"
                    placeholder="" required />
                <label for="confirmPassword">Confirm Password</label>
            </div>
            <button class="submit-button" ref="submitBtn" type="submit" data-status="normal">Submit</button>
        </form>
    </main>
</template>

<style scoped>
main {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
}

.reset-password-form {
    width: 300px;
}

.hidden {
    display: none;
}
</style>