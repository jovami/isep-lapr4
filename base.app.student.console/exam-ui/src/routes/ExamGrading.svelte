<script lang="ts">
    import { push } from "svelte-spa-router";
    import { resolutionStore } from "../store";

    // type GivenAnswer = {
    //     answer: string;
    //     questionID: number;
    // };

    // type SectionAnswers = {
    //     answers: GivenAnswer[];
    // };

    // type Resolution = {
    //     sectionAnswers: SectionAnswers[];
    // }

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

    let resolution = null;
    resolutionStore.subscribe((value) => {
        if (value !== null) resolution = value;
    });

    const examResult = async (): Promise<ExamResult> => {
        console.log(resolution);

        const res = await fetch("http://localhost:8090/api/examtaking/grade", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(resolution),
        });

        const body = await res.json();

        if (res.ok) {
            console.log(body);
            return body;
        } else {
            throw new Error(body);
        }
    };
</script>

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
<div class="inline-flex">
    <button
        on:click={() => push("/")}
        class="flex rounded-lg mt-16 bg-indigo-500 py-2 px-8 font-sans font-bold
                    uppercase text-white shadow-md shadow-indigo-500/20 transition-all
                    hover:shadow-lg hover:shadow-indigo-500/40 focus:opacity-[0.85]
                    focus:shadow-none active:opacity-[0.85] active:shadow-none
                    disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
        data-ripple-light="true"
    >
        Back to Home
    </button>
</div>
