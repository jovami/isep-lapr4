<script lang="ts">
    import Router from "svelte-spa-router";

    import ExamList from "./routes/ExamList.svelte";
    import ExamTaking from "./routes/ExamTaking.svelte";
</script>

<Router
    routes={{
        "/": ExamList, // TODO
        "/formative-exams": ExamList,
        "/take": ExamTaking,
    }}
/>
