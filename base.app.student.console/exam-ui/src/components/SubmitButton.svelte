<script lang="ts">
    export let onclick: () => void = () => {};
    export let disable: boolean = false;
    export let type: "button" | "submit" | "reset" = "button";
</script>

<div class="inline-flex">
    <button
        {type}
        class="flex mt-16 py-2 px-8
               font-sans font-bold uppercase
               rounded-lg text-ctp-mantle

               transition ease-in-out delay-100 duration-300
               hover:-translate-y-1 hover:scale-110

               bg-ctp-lavender hover:bg-ctp-mauve active:bg-ctp-mauve/75

               shadow-md shadow-ctp-mauve/20 transition-all
               focus:shadow-none active:opacity-[0.85] active:shadow-none
               hover:shadow-lg hover:shadow-ctp-mauve/40 focus:opacity-[0.85]

               disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
        data-ripple-light="true"
        disabled={disable}
        on:click={onclick}
    >
        <slot />
    </button>
</div>
