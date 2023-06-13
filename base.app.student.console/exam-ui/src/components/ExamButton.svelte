<script lang="ts">
    export let onclick: () => void;
</script>

<div class="xl:w-1/3 md:w-1/2 p-4">
    <button on:click={onclick}>
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
                    <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
                </svg>
            </div>
            <slot />
        </div>
    </button>
</div>
