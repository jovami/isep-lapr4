<script lang="ts">
    import Router from "svelte-spa-router";

    // Regular exams

    // Formative exams
    import FexamList from "./routes/FexamList.svelte";
    import TakeFexam from "./routes/TakeFexam.svelte";
</script>

<Router
    routes={{
        "/": FexamList, // TODO
        "/formative": FexamList,
        "/formative/take": TakeFexam,
        // "/*": NotFound.svelte,
    }}
/>
