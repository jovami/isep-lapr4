<script lang="ts">
    import ExamForm from "../components/ExamForm.svelte";
    import SubmitButton from "../components/SubmitButton.svelte";
    import ReGradeView from "../components/regular/ReGradeView.svelte";

    import type { Exam } from "../types/exam";
    import type {
        RegularResolution as Resolution,
        RegularSectionAnswers as SectionAnswers,
    } from "../types/regular-resolution";

    import { examStore } from "../store";
    import { pop, push } from "svelte-spa-router";

    let title: string = null;
    let selectedExam = null;
    examStore.subscribe((value) => {
        if (value !== null) selectedExam = value;
    });

let _exam: Exam | null = null;

    const chooseExam = async (): Promise<Exam> => {
        if (selectedExam === null) {
            throw new Error("No exam selected!");
        }

        console.log(selectedExam);

        const res = await fetch("api/examtaking/regular/take", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(selectedExam),
        });

        const body = await res.json();
        console.log(body);

        if (res.ok) {
            _exam = body;
            title = selectedExam.title;
            return body;
        } else {
            throw body as Error;
        }
    };

    let resolution: Resolution = null;

    const handleSubmit = () => {
        const form = document.getElementById("exam") as HTMLFormElement;

        const data = Object.fromEntries(new FormData(form).entries());
        console.log(`form data: ${data}`);

        const sections: SectionAnswers[] = [];
        for (const [key, value] of Object.entries(data)) {
            const sectionId = key.split("_")[0];
            const questionIdx = key.split("_")[1];
            const answer = value;

            if (sections[sectionId] === undefined) {
                sections[sectionId] = { answers: [] };
            }

            if (sections[sectionId].answers[questionIdx] === undefined) {
                sections[sectionId].answers[questionIdx] = "";
            }

            if (sections[sectionId].answers[questionIdx].length > 0) {
                sections[sectionId].answers[questionIdx] += "\n" + answer;
            } else {
                sections[sectionId].answers[questionIdx] = answer;
            }
        }

        resolution = {
            sections: sections,
            submissionTime: new Date(),
            title: title,
        };
    };
</script>

{#if !resolution}
    {#await chooseExam()}
        <p>Loading...</p>
    {:then exam}
        <ExamForm {exam} submit={handleSubmit} />
    {:catch error}
        <p>
            Error: {error.message ?? error.error ?? error.status}
        </p>
        <SubmitButton onclick={pop}>Back to Exam selection</SubmitButton>
    {/await}
{:else}
    <ReGradeView {resolution} exam={_exam}/>
    <SubmitButton onclick={() => push("/regular")}>
        Back to Exam selection
    </SubmitButton>
{/if}
