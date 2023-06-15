<script lang="ts">
    import ExamForm from "../components/ExamForm.svelte";
    import SubmitButton from "../components/SubmitButton.svelte";
    import FeGradeView from "../components/formative/FeGradeView.svelte";

    import type { Exam } from "../types/exam";
    import type {
        FormativeResolution as Resolution,
        FormativeSectionAnswers as SectionAnswers,
    } from "../types/formative-resolution";

    import { examStore } from "../store";
    import { pop, push } from "svelte-spa-router";

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

        const res = await fetch("http://localhost:8090/api/examtaking/formative/take", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(selectedExam),
        });

        const body = await res.json();
        console.log(body);

        if (res.ok) {
            _exam = body;
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

        const sectionAnswers: SectionAnswers[] = [];
        for (const [key, value] of Object.entries(data)) {
            const sectionId = key.split("_")[0];
            const questionIdx = key.split("_")[1];
            const id = key.split("_")[2];
            const answer = value;

            console.log(id);

            if (sectionAnswers[sectionId] === undefined) {
                sectionAnswers[sectionId] = { answers: [] };
            }

            if (sectionAnswers[sectionId].answers[questionIdx] === undefined) {
                sectionAnswers[sectionId].answers[questionIdx] = {
                    answer: "",
                    questionID: null,
                };
            }

            if (
                sectionAnswers[sectionId].answers[questionIdx].answer.length > 0
            ) {
                sectionAnswers[sectionId].answers[questionIdx].answer +=
                    "\n" + answer;
            } else {
                sectionAnswers[sectionId].answers[questionIdx].questionID =
                    parseInt(id);
                sectionAnswers[sectionId].answers[questionIdx].answer = answer;
            }
        }

        resolution = { sectionAnswers: sectionAnswers };
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
    <FeGradeView {resolution} exam={_exam}/>
    <SubmitButton onclick={() => push("/formative")}>
        Back to Exam selection
    </SubmitButton>
{/if}
