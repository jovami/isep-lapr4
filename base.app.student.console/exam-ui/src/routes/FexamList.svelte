<script lang="ts">
    import ExamButton from "../components/ExamButton.svelte";
    import SubmitButton from "../components/SubmitButton.svelte";
    import ListPanel from "../components/ListPanel.svelte";

    import { examStore } from "../store";
    import { push } from "svelte-spa-router";

    type ExamInfo = { examName: string; courseName: string };

    const prefix: string = "/formative";

    let selected: ExamInfo | null = null;

    const listExams = async (): Promise<ExamInfo[]> => {
        const res = await fetch("api/examtaking/formative/exam-list");
        const body = await res.json();

        if (res.ok) {
            return body;
        } else {
            throw body as Error;
        }
    };
</script>

<ListPanel>
    {#await listExams()}
        <p>waiting</p>
    {:then list}
        {#if list.length === 0}
            <!-- TODO: better message -->
            <p>No exams available</p>
        {:else}
            <div class="flex flex-wrap -m-4">
                {#each list as exam}
                    <ExamButton onclick={() => (selected = exam)}>
                        <h2
                            class="text-lg text-ctp-text font-medium title-font mb-2 capitalize"
                        >
                            {exam.examName}
                        </h2>
                        <p class="leading-relaxed text-base uppercase">
                            {exam.courseName}
                        </p>
                    </ExamButton>
                {/each}
            </div>
        {/if}
    {:catch error}
        <p>Error: {error.message ?? error.error}</p>
    {/await}

    <SubmitButton
        disable={selected === null}
        onclick={() => {
            examStore.set(selected);
            push(`${prefix}/take`);
        }}
    >
        Take Formative Exam
    </SubmitButton>
</ListPanel>
