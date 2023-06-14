<script lang="ts">
    import Router from "svelte-spa-router";

    // Regular exams
    import RexamList from "./routes/RexamList.svelte";
    import TakeRexam from "./routes/TakeRexam.svelte";
    // Formative exams
    import FexamList from "./routes/FexamList.svelte";
    import TakeFexam from "./routes/TakeFexam.svelte";
</script>

<Router
    routes={{
        "/": FexamList, // TODO
        "/formative": FexamList,
        "/formative/take": TakeFexam,
        "/regular": RexamList,
        "/regular/take": TakeRexam,
        // "/*": NotFound.svelte,
    }}
/>
