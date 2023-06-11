<script lang="ts">
    import {examStore} from "../store";

    type Question = {
        id: number;
        description: string;

        groups?: {
            [key: string]: string[];
        };
        choices?: string[];

        phrase1?: string[];
        phrase2?: string[];

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

    const ola = "/formative-exams";
    let selectedExam = null;
    examStore.subscribe((value) => {
        selectedExam = value;
    });

    const chooseExam = async (): Promise<Exam> => {
        if (selectedExam === null) {
            console.log("ola");
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
    };
</script>

{#await chooseExam()}
    <p>Loading...</p>
{:then exam}
    <h1>
        Title: {exam.title}
    </h1>
    <hr/>
    <br/>
    {#each exam.sections as section, i}
        <h2>
            Section {i + 1}: {section.description}
        </h2>
        <hr/>
        <br/>
        {#each section.questions as question, j}
            <h3>
                Question {j + 1}: {question.description}
            </h3>
            {#if question.type === "MULTIPLE_CHOICE"}
                <form>
                    {#each question.options as choice, k}
                        <input type="radio" id={choice} name={question.id} value={choice}/>
                        <label for={choice}>{choice}</label><br/>
                    {/each}
                </form>
            {:else if question.type === "TRUE_FALSE"}
                <form>
                    <div class="flex justify-center">
                        <div class="mb-[0.125rem] mr-4 inline-block min-h-[1.5rem] pl-[1.5rem]">
                            <input type="radio" id="true" name={question.id} value="true"/>
                            <label for="true">True</label>
                        </div>
                        <div class="mb-[0.125rem] mr-4 inline-block min-h-[1.5rem] pl-[1.5rem]">
                            <input type="radio" id="false" name={question.id} value="false"/>
                            <label for="false">False</label>
                        </div>
                    </div>

                </form>
            {:else if question.type === "MISSING_WORDS"}
                <form>
                    {#each question.choices as from_group, k}
                        <p>
                            [{k + 1}] &#8594;
                            <select name={from_group}>
                                {#each question.groups[from_group] as group, l}
                                    <option value={group}>{group}</option>
                                {/each}
                            </select>
                        </p>
                    {/each}
                </form>
            {:else if question.type === "SHORT_ANSWER"}
                <form>
                    <input type="text" id="short_answer" name={question.id} value=""/>
                </form>
            {:else if question.type === "NUMERICAL"}
                <form>
                    <input type="number" id="numerical_answer" name={question.id} value=""/>
                </form>
            {:else if question.type === "MATCHING"}
                <!-- https://www.inmotionhosting.com/support/wp-content/uploads/2012/11/edu_moodle_104_matching-question_matching-question-5-final.gif -->
                <form>
                    {#each question.phrase1 as phrase1, k}
                        <p>
                            {phrase1} &#8594;
                            <select name={phrase1}>
                                {#each question.phrase2 as phrase2, l}
                                    <option value={phrase2}>{phrase2}</option>
                                {/each}
                            </select>
                        </p>
                    {/each}

                </form>
            {/if}
            <br/>
        {/each}
        <br/>
    {/each}

{:catch error}
    <p>
        Error: {error.message}
    </p>
{/await}
