<script lang="ts">
    import ExamForm from "../components/ExamForm.svelte";
    import SubmitButton from "../components/SubmitButton.svelte";
    import FeGradeView from "../components/formative/FeGradeView.svelte";

    import { examStore } from "../store";
    import { pop, push } from "svelte-spa-router";

    type Question = {
        id: number;
        description: string;

        groups?: { [key: string]: string[] };
        choices?: string[];

        phrase1?: string[];
        phrase2?: string[];

        singleAnswer?: boolean;
        options?: [];
        type:
            | "MATCHING"
            | "MULTIPLE_CHOICE"
            | "SHORT_ANSWER"
            | "NUMERICAL"
            | "MISSING_WORDS"
            | "TRUE_FALSE";
    };

    type Section = {
        id: number;
        description: string;
        questions: Question[];
    };

    type Exam = {
        title: string;
        description: string;
        sections: Section[];
    };

    type GivenAnswer = {
        answer: string;
        questionID: number;
    };

    type SectionAnswers = {
        answers: GivenAnswer[];
    };

    type Resolution = {
        sectionAnswers: SectionAnswers[];
    };

    let selectedExam = null;
    examStore.subscribe((value) => {
        if (value !== null) selectedExam = value;
    });

    const chooseExam = async (): Promise<Exam> => {
        if (selectedExam === null) {
            throw new Error("No exam selected!");
        }

        console.log(selectedExam);

        const res = await fetch("api/examtaking/formative/take", {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(selectedExam),
        });

        const body = await res.json();
        console.log(body);

        if (res.ok) {
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
    <FeGradeView {resolution} />
    <SubmitButton onclick={() => push("/formative")}>
        Back to Exam selection
    </SubmitButton>
{/if}
