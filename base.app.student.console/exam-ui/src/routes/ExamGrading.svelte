<script lang="ts">
    import {resolutionStore} from "../store";

    type GivenAnswer = {
        answer: string;
        questionID: number;
    };

    type SectionAnswers = {
        answers: GivenAnswer[];
    };

    type Resolution = {
        sectionAnswers: SectionAnswers[];
    }

    type Answer = {
        points: number;
        feedback: string;
    }

    type Section = {
        answers: Answer[];
    }

    type ExamResult = {
        sections: Section[];
        grade: number;
        maxGrade: number;
    }

    let resolution = null;
    resolutionStore.subscribe((value) => {
        if (value !== null) resolution = value;
    });

    const examResult = async (): Promise<ExamResult> => {
        console.log(resolution);

        const res = await fetch("http://localhost:8090/api/examtaking/grade", {
            method: "POST",
            headers: {"Content-type": "application/json"},
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
    <button on:click={() => window.location.href = "/"}>Back to Home</button>
{:catch error}
    <p>Grade: {error.message}</p>
    <button on:click={() => window.location.href = "/"}>Back to Home</button>
{/await}
