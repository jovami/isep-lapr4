<script lang="ts" type="module">
    import type { Exam } from "../../types/exam";
    import type { ExamResult } from "../../types/exam-result";
    import type { RegularResolution } from "../../types/regular-resolution";

    const examResult = async (): Promise<ExamResult> => {
        console.log(resolution);

        const res = await fetch("api/examtaking/regular/grade", {
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

    export let resolution: RegularResolution;
    export let exam: Exam;
</script>

<!-- TODO: better styling -->

{#await examResult()}
    <p>Loading Grade...</p>
{:then result}
    <p>
        Grade:
        {#if result.grade == null}
            <strong>Not available</strong>
        {:else}
            <strong>{result.grade}</strong>
            / <strong>{result.maxGrade}</strong>
        {/if}
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
                {#if answer.points == null}
                    <p>Not available</p>
                {:else}
                    {#if answer.points === answer.maxPoints}
                        <strong>{answer.points}</strong>
                    {:else}
                        {answer.points}
                    {/if}
                    / <strong>{answer.maxPoints}</strong>
                {/if}
            </p>
            <p>
                {#if answer.feedback == null}
                    <p>Feedback: Not available</p>
                {:else}
                    Feedback: {answer.feedback}
                {/if}
            </p>
            <br />
        {/each}
        <br />
    {/each}
{:catch error}
    <p>Grade: {error.message ?? error.error ?? error.status}</p>
{/await}
