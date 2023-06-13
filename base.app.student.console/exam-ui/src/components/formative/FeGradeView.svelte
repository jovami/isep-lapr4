<script lang="ts" type="module">
    type Answer = {
        points: number;
        maxPoints: number;
        feedback: string;
    };

    type Section = {
        answers: Answer[];
    };

    type ExamResult = {
        sections: Section[];
        grade: number;
        maxGrade: number;
    };

    const examResult = async (): Promise<ExamResult> => {
        console.log(resolution);

        const res = await fetch(
            "http://localhost:8090/api/examtaking/formative/grade",
            {
                method: "POST",
                headers: { "Content-type": "application/json" },
                body: JSON.stringify(resolution),
            }
        );

        const body = await res.json();

        if (res.ok) {
            console.log(body);
            return body;
        } else {
            throw new Error(body);
        }
    };

    export let resolution: {};
</script>

<!-- TODO: better styling -->

{#await examResult()}
    <p>Loading Grade...</p>
{:then result}
    <p>Grade: {result.grade}/{result.maxGrade}</p>
    <hr />
    <br />
    {#each result.sections as section, i}
        <h2>Section {i + 1}</h2>
        <hr />
        <br />
        {#each section.answers as answer, j}
            <h3>Question {j + 1} &mdash; enunciado</h3>
            <p>
                Points:
                {#if answer.points == answer.maxPoints}
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
    <p>Grade: {error.message}</p>
{/await}
