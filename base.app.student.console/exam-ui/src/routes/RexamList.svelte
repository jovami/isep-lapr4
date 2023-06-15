<script lang="ts">
    import ExamButton from "../components/ExamButton.svelte";
    import SubmitButton from "../components/SubmitButton.svelte";
    import ListPanel from "../components/ListPanel.svelte";

    import { examStore } from "../store";
    import { push } from "svelte-spa-router";

    type ExamInfo = { title: string; course: string; startTime: Date; endTime: Date };

    const prefix: string = "/regular";

    let selected: ExamInfo | null = null;

    const listExams = async (): Promise<ExamInfo[]> => {
        const res = await fetch(
            "http://localhost:8090/api/examtaking/regular/exam-list"
        );
        const body = await res.json();

        if (res.ok) {
            console.log(body);
            return body;
        }
        else throw new Error(body);
    };
</script>

<ListPanel>
    <div class="flex flex-wrap -m-4">
        {#await listExams()}
            <p>waiting</p>
        {:then list}
            {#if list.length === 0}
                <!-- TODO: better message -->
                <p>No exams available</p>
            {:else}
                {#each list as exam}
                    <ExamButton onclick={() => (selected = exam)}>
                        <h2
                                class="text-lg text-white font-medium title-font mb-2"
                        >
                            {exam.title}
                        </h2>
                        <p class="leading-relaxed text-base">
                            {exam.course}
                        </p>
                    </ExamButton>
                {/each}
            {/if}
        {:catch error}
            <p>Error: {error.message}</p>
        {/await}
    </div>

    <SubmitButton
            disable={selected === null}
            onclick={() => {
            examStore.set(selected);
            push(`${prefix}/take`);
        }}
    >
        Take Regular Exam
    </SubmitButton>
</ListPanel>