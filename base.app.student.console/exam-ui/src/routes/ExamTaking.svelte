<script lang="ts">
    import {examStore, resolutionStore} from "../store";
    import {push} from "svelte-spa-router";

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

    let selectedExam = null;
    examStore.subscribe((value) => {
        if (value !== null) selectedExam = value;
    });

    const chooseExam = async (): Promise<Exam> => {
        if (selectedExam === null) {
            throw new Error("No exam selected!");
        }

        console.log(selectedExam);

        const res = await fetch("http://localhost:8090/api/examtaking/take", {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(selectedExam),
        });

        const body = await res.json();

        if (res.ok) {
            console.log(body);
            return body;
        } else {
            throw new Error(body);
        }
    }

    function handleSubmit() {
        const form = document.getElementById("exam") as HTMLFormElement;

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

        let resolution: Resolution = null;

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());
        console.log(data);

        const sectionAnswers: SectionAnswers[] = [];
        for (const [key, value] of Object.entries(data)) {
            const sectionId = key.split("_")[0];
            const questionIdx = key.split("_")[1];
            const id = key.split("_")[2];
            const answer = value;

            console.log(id);

            if (sectionAnswers[sectionId] === undefined) {
                sectionAnswers[sectionId] = {answers: []};
            }

            if (sectionAnswers[sectionId].answers[questionIdx] === undefined) {
                sectionAnswers[sectionId].answers[questionIdx] = {answer: "", questionID: null};
            }

            if (sectionAnswers[sectionId].answers[questionIdx].answer.length > 0) {
                sectionAnswers[sectionId].answers[questionIdx].answer += "\n" + answer;
            } else {
                sectionAnswers[sectionId].answers[questionIdx].questionID = parseInt(id);
                sectionAnswers[sectionId].answers[questionIdx].answer = answer;
            }
        }

        resolution = {sectionAnswers: sectionAnswers};
        resolutionStore.set(resolution);
        push("/grade");
    }
</script>

{#await chooseExam()}
    <p>Loading...</p>
{:then exam}
    <h1>
        <strong>{exam.title}</strong>
    </h1>
    <hr/>
    <br/>
    <form on:submit|preventDefault={handleSubmit} id="exam">
        {#each exam.sections as section, i}
            <h2>
                Section {i + 1} &mdash; {section.description}
            </h2>
            <hr/>
            <br/>
            {#each section.questions as question, j}
                <h3>
                    Question {j + 1} &mdash; {question.description}
                </h3>
                <!-- FIXME: id's in <input> should probably be unique -->
                {#if question.type === "MULTIPLE_CHOICE"}
                    {#if question.singleAnswer}
                        <div>
                            {#each question.options as choice, k}
                                <div
                                        class="mb-[0.125rem] block min-h-[1.5rem] pl-[1.5rem]
                                hover:cursor-pointer"
                                >
                                    <input
                                            class="align-middle hover:cursor-pointer"
                                            type="radio"
                                            id={choice + 1}
                                            name={i + "_" + j + "_" + question.id}
                                            value={k+1}
                                            required
                                    />
                                    <label
                                            class="mt-px inline-block pl-[0.15rem] hover:cursor-pointer"
                                            for={choice + 1}>{choice}</label
                                    >
                                </div>
                            {/each}
                        </div>
                    {:else}
                        <div>
                            {#each question.options as choice, k}
                                <div
                                        class="mb-[0.125rem] block min-h-[1.5rem] pl-[1.5rem]
                                hover:cursor-pointer"
                                >
                                    <input
                                            class="align-middle hover:cursor-pointer"
                                            type="checkbox"
                                            id={choice}
                                            name={i + "_" + j + "_" + question.id + "_" + k}
                                            value={k+1}
                                    />
                                    <label
                                            class="mt-px inline-block pl-[0.15rem] hover:cursor-pointer"
                                            for={choice}>{choice}</label
                                    >
                                </div>
                            {/each}
                        </div>
                    {/if}
                {:else if question.type === "TRUE_FALSE"}
                    <div>
                        <div class="flex justify-center">
                            <div
                                    class="mb-[0.125rem] mr-4 inline-block min-h-[1.5rem] pl-[1.5rem]"
                            >
                                <input
                                        class="hover:cursor-pointer"
                                        type="radio"
                                        id={"true" + i + "-" + j}
                                        name={i + "_" + j + "_" + question.id}
                                        value="true"
                                        required
                                />
                                <label
                                        class="mt-px inline-block pl-[0.15rem] hover:cursor-pointer"
                                        for={"true" + i + "-" + j}
                                >
                                    True
                                </label>
                            </div>
                            <div
                                    class="mb-[0.125rem] mr-4 inline-block min-h-[1.5rem] pl-[1.5rem]"
                            >
                                <input
                                        type="radio"
                                        id={"false" + i + "-" + j}
                                        name={i + "_" + j + "_" + question.id}
                                        value="false"
                                        required
                                />
                                <label
                                        class="mt-px inline-block pl-[0.15rem] hover:cursor-pointer"
                                        for={"false" + i + "-" + j}
                                >
                                    False
                                </label>
                            </div>
                        </div>
                    </div>
                {:else if question.type === "MISSING_WORDS"}
                    <div>
                        {#each question.choices as from_group, k}
                            <p>
                                [{k + 1}] &#8594;
                                <select
                                        name={i + "_" + j + "_" + question.id + "_" + k}
                                        class="hover:cursor-pointer"
                                        required
                                >
                                    {#each question.groups[from_group] as group}
                                        <option value={group}>{group}</option>
                                    {/each}
                                </select>
                            </p>
                        {/each}
                    </div>
                {:else if question.type === "SHORT_ANSWER"}
                    <div>
                        <input
                                type="text"
                                id="short_answer"
                                name={i + "_" + j + "_" + question.id}
                                value=""
                                required
                        />
                    </div>
                {:else if question.type === "NUMERICAL"}
                    <div>
                        <input
                                type="number"
                                id="numerical_answer"
                                name={i + "_" + j + "_" + question.id}
                                value=""
                                required
                        />
                    </div>
                {:else if question.type === "MATCHING"}
                    <!-- https://www.inmotionhosting.com/support/wp-content/uploads/2012/11/edu_moodle_104_matching-question_matching-question-5-final.gif -->
                    <div>
                        {#each question.phrase1 as phrase1, k}
                            <!-- TODO: right align phrase1, left align select -->
                            <!-- Like so:
                                Portugal -> Lisbon
                                   Spain -> Madrid
                                  France -> Paris
                                -->
                            <div
                                    class="flex mt-2 justify-center justify-items-center"
                            >
                                {phrase1}
                                &#8594;

                                <select
                                        name={i + "_" + j + "_" + question.id + "_" + phrase1}
                                        class="hover:cursor-pointer"
                                        required>
                                    {#each question.phrase2 as phrase2, l}
                                        <option value={(k+1) + "-" + (l+1)}>{phrase2}</option>
                                    {/each}
                                </select>
                            </div>
                        {/each}
                    </div>
                {/if}
                <br/>
            {/each}
            <br/>
        {/each}
        <div class="inline-flex">
            <button type="submit"
                    class="flex rounded-lg mt-16 bg-indigo-500 py-2 px-8 font-sans font-bold
                    uppercase text-white shadow-md shadow-indigo-500/20 transition-all
                    hover:shadow-lg hover:shadow-indigo-500/40 focus:opacity-[0.85]
                    focus:shadow-none active:opacity-[0.85] active:shadow-none
                    disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                    data-ripple-light="true"
            >
                Submit
            </button>
        </div>
    </form>
{:catch error}
    <p>
        Error: {error.message}
    </p>
{/await}

