<script lang="ts">
    import { push } from "svelte-spa-router";
    import { examStore } from "../store";

    type ExamInfo = { examId: number; examName: string; courseName: string };

    let selected: ExamInfo | null = null;

    const examList = async (): Promise<ExamInfo[]> => {
        const res = await fetch("api/examtaking/formative-exams");
        const body = await res.json();

        if (res.ok) return body;
        else throw new Error(body);
    };
</script>

<div
    class="text-gray-400 body-font bg-gray-900 border border-gray-700 rounded-lg
    border-indigo-500 border-opacity-75"
>
    <div class="container px-5 py-24 mx-auto">
        <!-- <div -->
        <!--     class="flex flex-wrap w-full mb-20 flex-col items-center text-center" -->
        <!-- > -->
        <!--     <h1 -->
        <!--         class="sm:text-3xl text-2xl font-medium title-font mb-2 text-white" -->
        <!--     > -->
        <!--         Pitchfork Kickstarter Taxidermy -->
        <!--     </h1> -->
        <!--     <p class="lg:w-1/2 w-full leading-relaxed text-opacity-80"> -->
        <!--         Whatever cardigan tote bag tumblr hexagon brooklyn asymmetrical -->
        <!--         gentrify, subway tile poke farm-to-table. -->
        <!--     </p> -->
        <!-- </div> -->
        <div class="flex flex-wrap -m-4">
            {#await examList()}
                <p>waiting</p>
            {:then resp}
                {#if resp.length === 0}
                    <!-- TODO: better message -->
                    <p>No exams available</p>
                {:else}
                    {#each resp as exam}
                        <div class="xl:w-1/3 md:w-1/2 p-4">
                            <button on:click={() => (selected = exam)}>
                                <div
                                    class="border border-gray-700 border-opacity-75 p-6
                                        rounded-lg"
                                >
                                    <div
                                        class="w-10 h-10 inline-flex items-center
                                            justify-center rounded-full bg-gray-800
                                            text-indigo-400 mb-4"
                                    >
                                        <svg
                                            fill="none"
                                            stroke="currentColor"
                                            stroke-linecap="round"
                                            stroke-linejoin="round"
                                            stroke-width="2"
                                            class="w-6 h-6"
                                            viewBox="0 0 24 24"
                                        >
                                            <path
                                                d="M22 12h-4l-3 9L9 3l-3 9H2"
                                            />
                                        </svg>
                                    </div>
                                    <h2
                                        class="text-lg text-white font-medium title-font mb-2"
                                    >
                                        '{exam.examName}'
                                    </h2>
                                    <p class="leading-relaxed text-base">
                                        {exam.courseName}
                                    </p>
                                </div>
                            </button>
                        </div>
                    {/each}
                {/if}
            {:catch error}
                <p>Error: {error.message}</p>
            {/await}
        </div>

        <div class="inline-flex">
            <!-- class="flex mx-auto mt-16 text-white bg-indigo-500 border-0 py-2 px-8 focus:outline-none hover:bg-indigo-600 rounded text-lg" -->
            <button
                type="button"
                class="flex rounded-lg mt-16 bg-indigo-500 py-2 px-8 font-sans font-bold
                    uppercase text-white shadow-md shadow-indigo-500/20 transition-all
                    hover:shadow-lg hover:shadow-indigo-500/40 focus:opacity-[0.85]
                    focus:shadow-none active:opacity-[0.85] active:shadow-none
                    disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                data-ripple-light="true"
                disabled={selected === null}
                on:click={() => {
                    examStore.set(selected);
                    push("/take");
                }}
            >
                Take Exam
            </button>
        </div>
    </div>
</div>
