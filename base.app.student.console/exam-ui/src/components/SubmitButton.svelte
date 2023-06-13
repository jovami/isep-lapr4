<script lang="ts">
    export let onclick: () => void = () => {};
    export let disable: boolean = false;
    export let type: "button" | "submit" | "reset" = "button";
</script>

<div class="inline-flex">
    <button
        {type}
        class="flex rounded-lg mt-16 bg-indigo-500 py-2 px-8 font-sans font-bold
           uppercase text-white shadow-md shadow-indigo-500/20 transition-all
           hover:shadow-lg hover:shadow-indigo-500/40 focus:opacity-[0.85]
           focus:shadow-none active:opacity-[0.85] active:shadow-none
           disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
        data-ripple-light="true"
        disabled={disable}
        on:click={onclick}
    >
        <slot />
    </button>
</div>
