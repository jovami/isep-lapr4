<script lang="ts" type="module">
    import type { Exam } from "../../types/exam";
    import type { ExamResult } from "../../types/exam-result";
    import type { FormativeResolution } from "../../types/formative-resolution";

    const examResult = async (): Promise<ExamResult> => {
        console.log(resolution);

        const res = await fetch("api/examtaking/formative/grade", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(resolution),
        });

        const body = await res.json();

        if (res.ok) {
            console.log(body);
            return body;
        } else {
            throw body as Error;
        }
    };

    export let resolution: FormativeResolution;
    export let exam: Exam;
</script>

<!-- TODO: better styling -->

{#await examResult()}
    <p>Loading Grade...</p>
{:then result}
    <p>
        Grade:
        {#if result.grade === result.maxGrade}
            <strong>{result.grade}</strong>
        {:else}
            {result.grade}
        {/if}
        / <strong>{result.maxGrade}</strong>
    </p>
    <hr
        class="w-100 h-1 mx-auto my-4 bg-gray-100 border-0 rounded md:my-3 dark:bg-gray-500"
    />
    <br />
    {#each result.sections as section, i}
        <h2>Section {i + 1}</h2>
        <hr
            class="w-100 h-1 mx-auto my-4 bg-gray-100 border-0 rounded md:my-3 dark:bg-gray-500"
        />
        <br />
        {#each section.answers as answer, j}
            <h3>
                Question {j + 1} &mdash; {exam.sections[i].questions[j]
                    .description}
            </h3>
            <p>
                Points:
                {#if answer.points === answer.maxPoints}
                    <strong>{answer.points}</strong>
                {:else}
                    {answer.points}
                {/if}
                / <strong>{answer.maxPoints}</strong>
            </p>
            <p>Feedback: {answer.feedback}</p>
            <br />
        {/each}
        <br />
    {/each}
{:catch error}
    <p>Grade: {error.message ?? error.error ?? error.status}</p>
{/await}
