<script lang="ts">
    import SubmitButton from "./SubmitButton.svelte";

    import type { Exam } from "../types/exam";

    export let exam: Exam;
    export let submit: () => void;
</script>

<h1>
    <strong>{exam.title}</strong>
</h1>
<hr class="w-100 h-1 mx-auto my-4 bg-gray-100 border-0 rounded md:my-3 dark:bg-gray-500">
<br />

<form on:submit|preventDefault={submit} id="exam">
    {#each exam.sections as section, i}
        <h2>
            Section {i + 1} &mdash; {section.description}
        </h2>
        <hr class="w-100 h-1 mx-auto my-4 bg-gray-100 border-0 rounded md:my-3 dark:bg-gray-500">
        <br />
        {#each section.questions as question, j}
            <h3>
                Question {j + 1} &mdash; {question.description}
            </h3>
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
                                    id={(choice + 1).toString()}
                                    name={i + "_" + j + "_" + question.id}
                                    value={k + 1}
                                    required
                                />
                                <label
                                    class="mt-px inline-block pl-[0.15rem] hover:cursor-pointer"
                                    for={(choice + 1).toString()}
                                    >{choice}</label
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
                                    name={i +
                                        "_" +
                                        j +
                                        "_" +
                                        question.id +
                                        "_" +
                                        k}
                                    value={k + 1}
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
                                name={i +
                                    "_" +
                                    j +
                                    "_" +
                                    question.id +
                                    "_" +
                                    phrase1}
                                class="hover:cursor-pointer"
                                required
                            >
                                {#each question.phrase2 as phrase2, l}
                                    <option value={k + 1 + "-" + (l + 1)}
                                        >{phrase2}</option
                                    >
                                {/each}
                            </select>
                        </div>
                    {/each}
                </div>
            {/if}
            <br />
        {/each}
        <br />
    {/each}
    <SubmitButton type="submit">Submit</SubmitButton>
</form>
