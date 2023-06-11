<script lang="ts">
    import Router from "svelte-spa-router";

    import ExamList from "./routes/ExamList.svelte";
    import ExamTaking from "./routes/ExamTaking.svelte";
    // import svelteLogo from "./assets/svelte.svg";
    // import viteLogo from "/vite.svg";
    // import Counter from "./lib/Counter.svelte";

    // const test = async () => {
    //     const res = await fetch('list/test')
    //     const body = await res.json()

    //     if (res.ok) {
    //         console.log('ok')
    //         console.log(body)
    //     } else {
    //         console.log(body.message)
    //     }
    // }
</script>

<Router
    routes={{
        "/": ExamList, // TODO
        "/formative-exams": ExamList,
        "/take": ExamTaking,
    }}
/>
